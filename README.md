# Missed Prayers / Qada Prayers Tracker

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://www.android.com)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-blue.svg)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Design-Material%203-purple.svg)](https://m3.material.io)

**Missed Prayers (Qada Prayers Tracker)** is a modern, privacy-focused, open-source Android application designed to help Muslims track, manage, and make up their missed (Qada) daily prayers (Fardh) and Witr. Built with native Android technologies adhering to the highest standards of Material Design 3 and offline privacy.

---

## 🌟 Key Features

- **Daily Prayer Tracking**: Effortlessly log daily prayers (Fajr, Dhuhr, Asr, Maghrib, Isha, and Witr) with status indicators (Performed, Missed, Excused).
- **Automatic End-of-Day Rollover**: Seamlessly transitions daily unperformed prayers into your Qada backlog at midnight, ensuring accurate long-term accountability.
- **Qada Backlog Numeric Tracking**: Maintain precise lifetime tallies of missed prayers across all prayer types with quick increment/decrement controls and bulk updates.
- **Purely Offline & Privacy-Focused**: Zero network requests, zero telemetry, zero user tracking, and no account required. All data is securely stored locally on your device using Room Database.
- **Multi-Language Localization**: Fully localized in **English**, **Turkish**, and **Arabic** with robust Right-to-Left (RTL) layout support.
- **Adaptive Material 3 Design**: Dynamic color support (Android 12+), smooth Edge-to-Edge navigation, and an expressive, accessible user interface optimized for phones and tablets.

---

## 🛠️ Tech Stack & Architecture

- **Language**: [Kotlin](https://kotlinlang.org) 100% Kotlin codebase.
- **UI Toolkit**: [Jetpack Compose](https://developer.android.com/jetpack/compose) for declarative, reactive UI components.
- **Design System**: [Material 3 (M3)](https://m3.material.io) with Material Color Utilities and Dynamic Color.
- **Architecture**: MVVM (Model-View-ViewModel) with unidirectional data flow using `StateFlow` and Coroutines.
- **Local Storage**: [Room Database](https://developer.android.com/training/data-storage/room) with DAO patterns and reactive Flow queries.
- **Build System**: Gradle with Kotlin DSL (`build.gradle.kts`) and version catalogs (`libs.versions.toml`).

---

## 🚀 Getting Started & Local Build

### Prerequisites
- [Android Studio Ladybug | 2024.2.1](https://developer.android.com/studio) or newer.
- JDK 17+.
- Android SDK (API level 24 to 35+).

### Building and Running Locally
1. Clone the repository:
   ```bash
   git clone https://github.com/username/missed-prayers.git
   cd missed-prayers
   ```
2. Open the project folder in Android Studio.
3. Sync project with Gradle files.
4. Run debug build via Gradle wrapper:
   ```bash
   ./gradlew :app:assembleDebug
   ```
5. Run unit tests:
   ```bash
   ./gradlew :app:testDebugUnitTest
   ```

---

## 📦 F-Droid & Google Play Store Readiness

Missed Prayers is structured to adhere to rigorous open-source distribution standards for F-Droid and mainstream app marketplaces like Google Play:

- **Open Source & Reproducible**: Released under the permissive **MIT License**, encouraging community auditability, translation contributions, and transparent code reviews.
- **Privacy-First Compliance**: Contains no proprietary third-party advertising, analytics SDKs, or tracking libraries. Complete transparency regarding local storage usage (`backup_rules.xml` and `data_extraction_rules.xml` configured).
- **Clean Dependency Management**: Pinned, stable dependencies utilizing modern Android Jetpack libraries without hidden binary dependencies or closed-source tracking components.

---

## 📄 License

This project is licensed under the terms of the **MIT License**. See the [LICENSE](LICENSE) file for details.
