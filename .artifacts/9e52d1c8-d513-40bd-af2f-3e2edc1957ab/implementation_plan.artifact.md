# Gradle Build Optimization Plan

The goal is to significantly reduce the build time of the DLearn project and address the "double build" issue while reverting to the stable API 36.

## User Review Required

> [!IMPORTANT]
> I am increasing the Gradle JVM heap size to 4GB (`-Xmx4g`). If your machine has limited RAM (e.g., 8GB total), we might want to adjust this to 3GB.

> [!NOTE]
> Reverting to `compileSdk = 36` and `targetSdk = 36` as requested. This matches your requirement for using the current stable API.

## Proposed Changes

### Build Configuration

#### [MODIFY] [gradle.properties](file:///C:/Users/theok/AndroidStudioProjects/DLearn/gradle.properties)
- Increase `org.gradle.jvmargs` to `-Xmx4g`.
- Enable `org.gradle.parallel`.
- Enable `org.gradle.caching`.
- Enable `org.gradle.vfs.watch` to speed up file change detection.
- Add `android.nonFinalResIds=true` for better incremental builds.

#### [MODIFY] [app/build.gradle.kts](file:///C:/Users/theok/AndroidStudioProjects/DLearn/app/build.gradle.kts)
- Revert `compileSdk` and `targetSdk` to 36.
- Remove the non-standard `optimization { enable = false }` block which might be causing overhead or conflicts in recent AGP versions.
- Ensure `isMinifyEnabled = false` for debug (default) and release (as currently set) to avoid unnecessary shrinking during development.

## Verification Plan

### Automated Tests
- Run `./gradlew clean assembleDebug` to measure the initial clean build time.
- Run `./gradlew assembleDebug` again to verify that the build cache and incremental build are working (should be much faster).
- Verify that only one build execution occurs when running the app.

### Manual Verification
- Deploy the app to a real device to ensure API 36 compatibility.
- Check the Gradle "Build" tab in Android Studio to confirm task counts are reduced and no redundant "second build" is triggered.
