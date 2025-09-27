package com.example.handgesturescroll

import android.content.Context
import android.graphics.Bitmap
import android.os.SystemClock
import android.util.Log
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarker
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class GestureRecognizerHelper(
    val context: Context,
    val listener: GestureListener
) {

    private var handLandmarker: HandLandmarker? = null
    private lateinit var backgroundExecutor: ExecutorService

    init {
        setupGestureRecognizer()
    }

    private fun setupGestureRecognizer() {
        backgroundExecutor = Executors.newSingleThreadExecutor()
        backgroundExecutor.execute {
            val baseOptions = BaseOptions.builder()
                .setModelAssetPath(MODEL_PATH)
                .setDelegate(BaseOptions.Delegate.CPU)
                .build()

            val options = HandLandmarker.HandLandmarkerOptions.builder()
                .setBaseOptions(baseOptions)
                .setNumHands(1)
                .setRunningMode(RunningMode.LIVE_STREAM)
                .setResultListener(this::onResults)
                .setErrorListener(this::onError)
                .build()

            try {
                handLandmarker = HandLandmarker.createFromOptions(context, options)
            } catch (e: IllegalStateException) {
                listener.onError("Hand Landmarker failed to initialize. See error logs for details")
                Log.e(TAG, "MediaPipe failed to load the model with error: " + e.message)
            }
        }
    }

    private fun onResults(result: HandLandmarkerResult, timestamp: Long) {
        if (result.landmarks().isNotEmpty()) {
            val landmarks = result.landmarks()[0]
            // Simple swipe detection based on the vertical movement of the wrist landmark
            if (landmarks.size > 8) {
                val wrist = landmarks[0]
                val indexFingerTip = landmarks[8]

                // For this simple implementation, we'll track the y-coordinate of the index finger tip.
                // A more robust implementation would track the average position of all landmarks or use a more complex gesture recognition model.
                detectSwipe(indexFingerTip.y())
            }
        }
    }

    private var lastY: Float? = null
    private fun detectSwipe(y: Float) {
        lastY?.let {
            val deltaY = y - it
            if (deltaY > SWIPE_THRESHOLD) {
                listener.onGesture(Gesture.SWIPE_DOWN)
                Log.d(TAG, "Swipe Down Detected")
            } else if (deltaY < -SWIPE_THRESHOLD) {
                listener.onGesture(Gesture.SWIPE_UP)
                Log.d(TAG, "Swipe Up Detected")
            }
        }
        lastY = y
    }


    private fun onError(error: RuntimeException) {
        listener.onError(error.message ?: "An unknown error occurred")
    }



    fun recognizeLiveStream(bitmap: Bitmap) {
        val frameTime = SystemClock.uptimeMillis()
        val image = BitmapImageBuilder(bitmap).build()
        handLandmarker?.detectAsync(image, frameTime)
    }

    fun interface GestureListener {
        fun onGesture(gesture: Gesture)
        fun onError(error: String) {}
    }

    enum class Gesture {
        SWIPE_UP,
        SWIPE_DOWN
    }

    companion object {
        private const val TAG = "GestureRecognizerHelper"
        private const val MODEL_PATH = "hand_landmarker.task"
        private const val SWIPE_THRESHOLD = 0.04f // Adjust this value for sensitivity
    }
}