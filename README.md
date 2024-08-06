# Custom Gallery
An simple Gallery to showcase images and videos available on the device

<div align="center">
  <sub>Built with ❤︎ by
  <a>Muhammad Usama Yasin</a>
</div>
<br/>

## Features
* Home Screen showing list of albums available on device
* Each albums shows thumbnail with respective folder name and file count.
* Detail Screen to show content of album
* Switch Layout from Grid to Linear and vice versa
* View Screen available if you click on Image/Video it will be presented to the user

## Architecture
* Built with Modern Android Development practices following up CLEAN architecture with MVVM
* Utilized Repository pattern for data.
* Includes valid Unit tests for Repository.

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying data changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Dependency Injection](https://developer.android.com/training/dependency-injection)
  - [Hilt](https://dagger.dev/hilt) - Easier way to incorporate Dagger DI into Android apps.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - For writing Gradle build scripts using Kotlin.
- [MockK](https://mockk.io) - For Mocking and Unit Testing.
- [Glide](https://github.com/bumptech/glide) - Glide is a fast and efficient open source media management and image loading framework for Android.

## Improvements:
 - Can be done in Multi Module
 - Add more Unit/UI Tests
 - Build UI with Compose
 - Add more error handling 
 - Incorporate UseCases/Mapper but it will be too much for a simple app
 - 

## Running Code
1. Android Studio:
2. Build your project (Build -> Rebuild Project).
3. Run your app (Run -> Run 'app').

## Unit Tests
1. Android Studio:
- Right-click on a test class and select "Run 'test class name'".
- Use the test runner in the Android Studio toolbar.
2. Command Line:
- ./gradlew testDebugUnitTest
3. Unit Test Coverage
  Android Studio:
- Right-click on a test class and select "Run with Coverage".
- This will open a coverage report in the Android Studio window.


## 👨 Developed By
**Muhammad Usama Yasin**

