CULTIVATOR'S CODEX

The Ultimate Xianxia Lore Companion

1. Project Overview

Cultivator's Codex is a dedicated, native Android application built for the passionate Xianxia and Wuxia community. It serves as a personal knowledge archive where users can meticulously track and manage every intricate detail of their favorite long-running novels—including character rosters, cultivation levels, techniques (Công Pháp), and essential plot summaries.

The application is designed to satisfy the ultimate fan's desire for comprehensive, accessible lore, enabling them to save and organize any information they love about their cherished fantasy world. It provides offline data persistence and is built on a modern MVVM architecture with Room Database.

The project demonstrates the following:

Robust local data persistence through Room Database (SQLite)

Clean MVVM architecture with clear separation of concerns

Highly polished, tab-based reader UI optimized for content lookup

Efficient offline-first design enabling usage without internet connection

2. Demo Video / GIF
![App Demo GIF](Assets%20image/gif%20and%20screenshot/gif%20demo%20app.gif)

3. Core Features (Version 1.0)
Reader Interface

A structured reader layout using tab navigation, including:

Overview

Characters

Sects

Techniques

Cultivation

Cover Flow Browsing

The main screen implements a custom ViewPager2-based cover browsing experience with smooth transitions between novels.

Offline Data Persistence

All data is stored locally using Room, ensuring full functionality even without internet.

Developer Admin Tools

A debug-mode Floating Action Button (FAB) provides quick access to add, update, and test content structures.

Image Loading

Coil is used to efficiently load remote images (covers, avatars, icons) with caching and smooth rendering.

CRUD Support

Full Create, Read, and Delete operations for:

Novels

Characters

Sects

Techniques

Cultivation stages

Plot arcs

4. Technical Architecture

This project follows the MVVM (Model – View – ViewModel) architecture to maintain clear structure, scalability, and readability.

Data Layer (Model)

Files:
data/AppDatabase.kt
data/WikiRepository.kt
data/*Entity.kt
data/*Dao.kt

Responsibilities:

Defines SQL entities (Character, Sect, Technique, Arc, Novel)

Provides DAO interfaces for Room queries

Centralized data access through the Repository pattern

ViewModel Layer (Business Logic)

Files:
ui/reader/BookReaderViewModel.kt
ui/MainViewModel.kt

Responsibilities:

Manages business logic and state

Exposes data via LiveData and Kotlin Flow

Executes background work using Coroutines (viewModelScope)

View Layer (UI)

Files:
Fragments: OverviewFragment, CharacterFragment, CultivationFragment, SectFragment, TechniqueFragment
Adapters: CharacterAdapter, CultivationAdapter, SectAdapter, etc.
Activities: MainActivity, BookReaderActivity, AddNovelActivity

Responsibilities:

Renders UI using XML layout files and Data Binding

Displays lists with RecyclerView

Coordinates user interaction and navigation

5. Project Structure
com.example.mini_library
│
├── data
│   ├── entities (Character, Sect, Technique, Novel, Arc)
│   ├── dao (CharacterDao, SectDao, TechniqueDao, NovelDao, ArcDao)
│   ├── AppDatabase.kt
│   └── WikiRepository.kt
│
└── ui
    └── reader
        ├── fragments (OverviewFragment, CharacterFragment, ...)
        ├── adapters (*.Adapter.kt)
        ├── viewmodels (*.ViewModel.kt)
        └── activities (MainActivity, BookReaderActivity)

6. Requirements

Android Studio Ladybug or newer

Minimum SDK: 26

Target SDK: 34

Kotlin JVM Target: 1.8

7. Build Instructions

Clone the repository:

git clone https://github.com/TienDatNguyen104473172/Cultivator-s_Codex

Open the project in Android Studio

Sync Gradle

Build the project

Run on a real device or emulator

*NOTE: For Quick Review (APK): A pre-compiled Debug APK file is attached to the latest v1.0.0 Release on this repository. This provides the fastest way to test the application's core UI and functionality on any Android device without needing to configure the build environment. (optional)

8. Initial Setup for New Users

Important: The Room database is stored locally on the developer's device.
The database does not contain preloaded content because novel data is not uploaded to GitHub.

During testing:

Open the app

Use the FAB on the Home screen

Select "Add Novel" to create a new entry

Tap the novel cover

Use the FAB in the Reader screen to add Characters, Arcs, Sects, etc.

9. Tech Stack

Kotlin

MVVM Architecture

Room Database (SQLite)

LiveData and Kotlin Flow

Coroutines

Data Binding / View Binding

RecyclerView + ViewPager2

Coil for image loading

10. Future Roadmap (Version 2.0)
Cloud Sync and Authentication

Firebase Authentication

Firestore-based cloud sync

External Data Ingestion

Retrofit integration to fetch book metadata (Google Books, OpenLibrary)

Advanced UI Features

Character detail pages

Visual relationship graphs (with graph layout libraries)

Localization

Full multilingual support (English, Vietnamese, Chinese)

11. License
MIT License

Copyright (c) 2025 TienDatNguyen104473172

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

12. Credits / Acknowledgements
https://github.com/kiwix/kiwix-android
https://github.com/android/views-widgets-samples/tree/main/ViewPager2
https://developer.android.com/topic/architecture
https://developer.android.com/training/data-storage/room