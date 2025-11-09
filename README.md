🎓 Student Study Buddy

Student Study Buddy is a modern Android application designed to help students stay organized, focused, and productive throughout their study journey. With built-in tools for reminders, note-taking, and a clean dashboard overview, this app simplifies how students manage their academic responsibilities.

The app is built with Jetpack Compose, follows MVVM architecture, and integrates Firebase Authentication for secure sign-in and Room Database for offline data storage.

🌐 Links

🔗 GitHub Repository: Student Study Buddy

🎥 Demo Video: Watch on YouTube
https://www.youtube.com/shorts/Eo96IgPdBYo

🚀 Features
🔐 User Authentication

Firebase Authentication (Email & Password)

Secure login and registration flow

Automatically stores user sessions

Logout functionality with navigation back to login

⏰ Reminders

Create one-time or recurring study reminders

Notifications using Android Notification API

Easy-to-use scheduling interface

Automatically displayed on the dashboard

Local persistence using Room Database

📝 Notes

Create, edit, and delete personal study notes

Organized storage with title and content fields

Auto-refresh and persistence using Room

Designed for simplicity and efficiency

🏠 Dashboard / Home Screen

Quick access to upcoming reminders

View recent notes at a glance

Centralized hub for managing your study life

🎨 Modern UI (Material 3)

Built entirely with Jetpack Compose

Dynamic, responsive, and clean design

Light and dark theme support

Adaptive layouts for different screen sizes

🧠 Architecture

The project follows the MVVM (Model-View-ViewModel) pattern for maintainable, scalable, and testable code.

Model      →  Handles data logic and database entities
ViewModel  →  Manages UI-related data using StateFlow
View       →  Jetpack Compose UI screens observing ViewModel states


Core Components:

Room Database → Local data storage for Notes & Reminders

Firebase Authentication → User login and registration

StateFlow → Reactive state management

Jetpack Compose Navigation → Seamless navigation between screens

🧰 Technology Stack
Layer	Technology
Language	Kotlin
UI	Jetpack Compose (Material 3)
Architecture	MVVM
Local Database	Room
Authentication	Firebase Authentication
Notifications	Android Notification API
State Management	StateFlow / LiveData
Dependency Injection (optional)	Hilt (if implemented)
🛠️ Installation Guide

Follow these steps to set up Student Study Buddy on your machine:

1️⃣ Clone the Repository
git clone https://github.com/Saadiq1234/StudentStudyBuddy.git
cd StudentStudyBuddy

2️⃣ Open in Android Studio

Open the project folder in Android Studio.

Let Gradle sync automatically or trigger manually via File → Sync Project with Gradle Files.

3️⃣ Configure Firebase

Go to your Firebase Console
.

Create a new Firebase project.

Add an Android app with your package name.

Download the google-services.json file.

Place it inside the /app directory.

4️⃣ Build and Run

Connect an Android device or emulator.

Click Run ▶️ in Android Studio.

Log in or register to get started!

📱 Usage

Sign Up / Log In using Firebase Authentication.

Add a Reminder via the Reminders tab to get notified before your study sessions.

Create Notes to jot down important concepts or to-do lists.

View your Dashboard to see upcoming reminders and recent notes.

Receive notifications at scheduled times to stay on track.

📸 Screenshots (Add later)
Feature	Screenshot
Login / Register	(insert image link)
Dashboard	(insert image link)
Notes	(insert image link)
Reminders	(insert image link)
🧩 Project Structure
com.studybuddy.app/
│
├── auth/                 → Login & Register Screens
├── notes/                → Notes UI + ViewModel + Entity
├── reminders/            → Reminders UI + ViewModel + Entity
├── data/                 → AppDatabase & DAO interfaces
├── dashboard/            → Home screen (overview)
├── viewmodel/            → Shared ViewModels (if applicable)
├── navigation/           → AppNavHost for Compose navigation
└── MainActivity.kt       → Entry point

🔔 Notifications

Reminders use the Android Notification API to alert users at scheduled times. Notifications are triggered using AlarmManager or WorkManager (depending on implementation), ensuring reliability even when the app is closed.

🧩 Future Enhancements

✅ Add cloud synchronization for notes and reminders.
✅ Implement study session tracking with statistics and progress charts.
✅ Add Pomodoro timer for focused study sessions.
✅ Introduce Google Calendar integration.



👤 Saadiq Jattiem
📧 [Insert your contact email or LinkedIn]
🔗 GitHub: Saadiq1234
