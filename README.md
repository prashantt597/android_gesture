# Hand Gesture Scroll

This is an Android application that uses the front-facing camera to detect hand gestures and scroll the content on the screen. It's built with CameraX for camera management and MediaPipe for real-time hand landmark detection.

## Features

*   **Gesture-based Scrolling**: Use hand swipes to scroll up and down.
*   **Real-time Detection**: Uses MediaPipe Hand Landmarker to detect hand gestures in real-time.
*   **CameraX Integration**: Leverages CameraX for efficient and easy-to-manage camera operations.
*   **GitHub Actions CI**: Automatically builds a debug APK on every push to the `main` branch.

## How to Run Locally

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/hand-gesture-scroll.git
    ```
2.  **Open in Android Studio:**
    *   Open Android Studio.
    *   Click on "Open an existing Android Studio project".
    *   Navigate to the cloned repository and select it.
3.  **Build the project:**
    *   Android Studio should automatically sync the Gradle files. If not, click on "Sync Project with Gradle Files".
    *   Once the sync is complete, click on "Build" > "Make Project".
4.  **Run the app:**
    *   Connect an Android device with developer options enabled, or start an emulator.
    *   Click on "Run" > "Run 'app'".

## How to Download the APK from GitHub Actions

1.  **Go to the "Actions" tab** in the GitHub repository.
2.  **Select the latest workflow run** for the `main` branch.
3.  **Scroll down to the "Artifacts" section** and you will find the `hand-gesture-scroll-apk`.
4.  **Click on the artifact to download it.** You will get a zip file containing the `app-debug.apk` file.
5.  **Unzip the file and install the APK** on your Android device. You may need to enable "Install from unknown sources" in your device settings.

## How to Push to Your Own GitHub Repository

1.  **Create a new repository on GitHub.**
2.  **Initialize a git repository in the project folder (if not already done):**
    ```bash
    git init
    ```
3.  **Add the remote repository:**
    ```bash
    git remote add origin https://github.com/your-username/your-new-repo-name.git
    ```
4.  **Stage and commit your changes:**
    ```bash
    git add .
    git commit -m "Initial commit"
    ```
5.  **Push to the remote repository:**
    ```bash
    git push -u origin main
    ```