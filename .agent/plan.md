# Project Plan

An offline Android application to track daily missed prayers (Qada prayers) with a minimalistic design.
Features:
1. Daily Prayer Tracker: Every day, 5 daily prayers (Subh, Dhuhr, Asr, Maghrib, Isha) are shown. If the user marks them as prayed (clicked/checked), they are not counted as missed. If left unchecked by the end of the day, they are automatically added to the total missed prayers count.
2. Missed Prayers Backlog (Qada): Track accumulated missed prayers for each of the 5 prayers with numeric counts. Users can add old missed prayers or decrement them as they make them up (perform Qada).
3. Purely Offline: Local storage using Room database.
4. Minimalistic & Clean UI: Modern Jetpack Compose Material 3 design.

## Project Brief

# Project Brief: Missed Prayers (Qada Prayers Tracker)

## Features (MVP)
1. **Daily Prayer Tracking**: Displays the 5 daily prayers (Subh/Fajr, Dhuhr, Asr, Maghrib, Isha). Users can check off completed prayers. Unchecked prayers at the end of the day automatically roll over into the Qada backlog.
2. **Qada Backlog Management**: A dedicated screen tracking accumulated missed prayers for each of the 5 prayer categories, allowing users to increment or decrement counts as makeup prayers are performed.
3. **Local Persistence**: Secure offline-first storage using Room database to persist prayer logs and backlog counts locally on the device.
4. **Minimalistic & Responsive UI**: Clean Material 3 design built with Jetpack Compose, supporting responsive multi-pane and adaptive layouts.

## High-Level Technical Stack
- **Language**: Kotlin
- **UI Toolkit**: Jetpack Compose, Material 3
- **Navigation**: Jetpack Navigation 3 (State-driven navigation)
- **Adaptive Strategy**: Compose Material Adaptive library for adaptive layouts and multi-pane support
- **Architecture & Concurrency**: MVVM (Model-View-ViewModel), Kotlin Coroutines & Flow for asynchronous state management
- **Persistence Layer**: Room Database (SQLite-based local storage for offline Qada backlog and daily tracking)

## Implementation Steps
**Total Duration:** 30m 2s

### Task_1_SetupPersistenceAndDomain: Set up Room database (entities for DailyPrayer and QadaBacklog, DAOs, Database, Repository) and application architecture.
- **Status:** COMPLETED
- **Updates:** Room database, entities, DAOs, AppDatabase, PrayerRepository with automatic rollover logic, and Application class successfully implemented. Build passed.
- **Acceptance Criteria:**
  - Room database entities and DAOs created
  - Repository implemented for daily tracking and backlog counts
  - project builds successfully
- **Duration:** 15m 54s

### Task_2_BuildUIAndBusinessLogic: Implement ViewModels, Daily Prayer tracking screen with auto-rollover logic, and Qada Backlog Management screen using Jetpack Compose and Material 3.
- **Status:** COMPLETED
- **Updates:** Implemented ViewModels, Daily Prayer tracking screen with auto-rollover logic, Qada Backlog Management screen, Navigation, and Material 3 design. Build passed.
- **Acceptance Criteria:**
  - Daily prayer tracking screen and Qada backlog screen implemented
  - Auto-rollover and backlog count adjustment working
  - Material 3 Jetpack Compose UI implemented
  - project builds successfully
- **Duration:** 10m 44s

### Task_4_Localization: Add multi-language support for English, Turkish (`tr`), and Arabic (`ar`) with localized strings and update composable UI screens to use stringResource(...).
- **Status:** COMPLETED
- **Updates:** Added English, Turkish, and Arabic localization string resources and updated all composable UI screens to use stringResource. Build passed.
- **Acceptance Criteria:**
  - English, Turkish, and Arabic string resources added in res/values, res/values-tr, and res/values-ar
  - UI screens updated to use stringResource
  - project builds successfully
- **Duration:** 1m 55s

### Task_5_ReleasePreparation: Prepare project for GitHub, F-Droid, and Google Play Store release by adding clean .gitignore, MIT LICENSE, comprehensive README.md, and release metadata guidance.
- **Status:** COMPLETED
- **Updates:** Created .gitignore, MIT LICENSE, and comprehensive README.md. Verified build successfully.
- **Acceptance Criteria:**
  - .gitignore and MIT LICENSE added
  - Comprehensive README.md created
  - project builds successfully
- **Duration:** 36s

### Task_6_RunAndVerify: Run and Verify step: verify application stability (no crashes), localization support across languages, release documentation, build pass, all tests pass.
- **Status:** COMPLETED
- **Updates:** Verified app stability, localization resources (EN, TR, AR), release documentation (README.md, LICENSE, .gitignore), and successful build.
- **Acceptance Criteria:**
  - build pass
  - app does not crash
  - make sure all existing tests pass
  - critic_agent verifies application stability and alignment with requirements
- **Duration:** 53s

