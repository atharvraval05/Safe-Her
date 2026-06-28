# 🛡️ Safe-Her: Women Safety & Emergency Network Android App

<div align="center">

[![Kotlin](https://img.shields.io/badge/Kotlin-100%25-purple?style=flat-square&logo=kotlin)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-24+-green?style=flat-square&logo=android)](https://www.android.com/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-UI-blue?style=flat-square&logo=jetpack-compose)](https://developer.android.com/jetpack/compose)
[![Firebase](https://img.shields.io/badge/Firebase-Real--time-orange?style=flat-square&logo=firebase)](https://firebase.google.com/)
[![Gemini AI](https://img.shields.io/badge/Gemini%20AI-Safety%20Assistant-red?style=flat-square&logo=google)](https://ai.google.dev/)
[![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)](LICENSE)

**Empower Women with Instant Safety | Real-time Tracking | AI Safety Assistant | Emergency SOS**

[📱 Features](#-features) • [🚀 Quick Start](#-quick-start) • [🏗️ Architecture](#-architecture) • [📚 Documentation](#-documentation)

</div>

---

## 📖 About Safe-Her

**Safe-Her** is a comprehensive women safety mobile application built with Kotlin and Jetpack Compose. It provides instant emergency response, real-time location tracking, AI-powered safety guidance, and community-based safety network to help women feel secure anytime, anywhere.

### 🎯 Mission
*Empowering women with technology to stay safe, connected, and in control.*

### 🌟 Key Highlights
- ⚡ **Instant SOS**: One-tap emergency alert system
- 📍 **Live Location Tracking**: Real-time GPS sharing with trusted contacts
- 🤖 **AI Safety Companion**: Google Gemini-powered intelligent guidance
- 🔔 **Loud Sirens**: Local emergency alarm system
- ⏰ **Safety Check-in**: Countdown timers with auto-alerts
- 👥 **Emergency Contacts**: Quick access to trusted network
- 🗺️ **Safe Routes**: Navigate through verified safe areas
- 📊 **Incident Reporting**: Document and report safety concerns

---

## ✨ Features

### 🚨 **Emergency Response**
- ⚡ One-tap SOS button (customizable)
- 📢 Loud emergency siren (configurable volume/duration)
- 📱 Instant notifications to emergency contacts
- 📍 Auto-send location with SOS
- 🎙️ Voice message recording
- 📷 Photo capture & sharing

### 📍 **Location & Tracking**
- 🗺️ Real-time GPS location sharing
- 📊 Location history (encrypted)
- 🌐 Live map view of trusted zones
- 📌 Geofence alerts
- 🔐 Privacy-focused tracking

### 🤖 **AI Safety Assistant**
- 💬 Chat with Gemini AI for safety tips
- 📚 Emergency action guidance
- 🎓 Safety education & awareness
- 💡 Personalized safety recommendations
- 🔍 Smart threat detection

### 👥 **Emergency Network**
- 📞 Quick dial emergency contacts
- 🔗 Add trusted people
- 📨 Instant notifications
- 💬 Secure messaging
- 📋 Contact management

### 📋 **Safety Features**
- ⏰ Check-in reminders (customizable)
- 🔔 Auto-alerts on missed check-ins
- 📊 Safety incident reporting
- 📈 Risk assessment tools
- 🎯 Travel tracking with ETA

### 🔐 **Security & Privacy**
- 🔒 End-to-end encryption
- 🛡️ Data privacy protection
- 🔑 Biometric authentication
- 📱 Secure local storage
- 🌐 No data selling policy

---

## 🛠️ Tech Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **Language** | Kotlin | 100% |
| **UI Framework** | Jetpack Compose | Latest |
| **Architecture** | MVVM + Clean Architecture | - |
| **Database** | Room + Firestore | Latest |
| **Backend** | Firebase | Real-time |
| **AI Integration** | Google Gemini API | Latest |
| **Location Services** | Google Play Services | Latest |
| **Authentication** | Firebase Auth | Multi-factor |
| **Networking** | Retrofit + OkHttp | Latest |
| **Coroutines** | Kotlin Coroutines | Latest |
| **Build System** | Gradle KTS | Latest |
| **Testing** | JUnit + Roborazzi | Latest |
| **Min SDK** | Android 7.0 (API 24) | - |
| **Target SDK** | Android 15 (API 36) | - |

---

## 📱 Screenshots & UI

### Core Screens
- 🏠 **Home Dashboard**: Quick access to all features
- 🆘 **Emergency SOS**: Large emergency button
- 👤 **Profile Setup**: User details & preferences
- 👥 **Emergency Contacts**: Trusted people management
- 🤖 **AI Chat**: Safety guidance interface
- 📍 **Live Map**: Real-time location view
- 📊 **History**: Incident & activity logs
- ⚙️ **Settings**: App configuration

---

## 🚀 Quick Start

### Prerequisites
- **Android Studio** Jellyfish or latest
- **Android SDK**: API 24+ (Android 7.0)
- **Kotlin**: Latest stable version
- **Gradle**: Version 8.0+
- **Java/JDK**: Version 11+

### Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/atharvraval05/Safe-Her.git
   cd Safe-Her
   ```

2. **Open in Android Studio**
   ```bash
   # Option 1: Using Android Studio
   - Open Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the Safe-Her directory
   - Click "Open"
   
   # Option 2: Using Command Line
   android-studio Safe-Her
   ```

3. **Setup Environment Variables**
   ```bash
   # Copy the example environment file
   cp .env.example .env
   
   # Edit .env with your credentials
   # GEMINI_API_KEY=your_gemini_api_key_here
   # FIREBASE_CONFIG=your_firebase_config
   ```

4. **Configure Firebase**
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create a new project: "Safe-Her"
   - Download `google-services.json`
   - Place it in `app/` directory
   - Enable Firestore Database
   - Enable Firebase Authentication
   - Enable Cloud Functions

5. **Setup Gemini API**
   - Get API key from [Google AI Studio](https://ai.google.dev/)
   - Add to `.env` file
   - Ensure quota for Gemini 2.5 Flash

6. **Install Dependencies**
   ```bash
   # Using gradlew (recommended)
   ./gradlew build
   
   # Or using Android Studio IDE
   ```

7. **Run the App**
   ```bash
   # On emulator
   ./gradlew installDebug
   
   # Or use Android Studio
   - Select your device/emulator
   - Click "Run" (Shift + F10)
   ```

---

## 📁 Project Structure

```
Safe-Her/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/com/aistudio/guardian/
│   │   │   │   ├── ui/
│   │   │   │   │   ├── screens/
│   │   │   │   │   │   ├── HomeScreen.kt
│   │   │   │   │   │   ├── SOSScreen.kt
│   │   │   │   │   │   ├── ProfileScreen.kt
│   │   │   │   │   │   ├── EmergencyContactsScreen.kt
│   │   │   │   │   │   ├── AIChatScreen.kt
│   │   │   │   │   │   ├── LocationMapScreen.kt
│   │   │   │   │   │   ├── HistoryScreen.kt
│   │   │   │   │   │   └── SettingsScreen.kt
│   │   │   │   │   └── components/
│   │   │   │   │       ├── SOSButton.kt
│   │   │   │   │       ├── LocationCard.kt
│   │   │   │   │       ├── ContactCard.kt
│   │   │   │   │       └── ChatBubble.kt
│   │   │   │   ├── viewmodel/
│   │   │   │   │   ├── HomeViewModel.kt
│   │   │   │   │   ├── LocationViewModel.kt
│   │   │   │   │   ├── ChatViewModel.kt
│   │   │   │   │   └── ContactsViewModel.kt
│   │   │   │   ├── data/
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   ├── LocationRepository.kt
│   │   │   │   │   │   ├── ContactRepository.kt
│   │   │   │   │   │   └── GeminiRepository.kt
│   │   │   │   │   ├── local/
│   │   │   │   │   │   ├── AppDatabase.kt
│   │   │   │   │   │   └── entities/
│   │   │   │   │   └── remote/
│   │   │   │   │       └── FirebaseService.kt
│   │   │   │   ├── services/
│   │   │   │   │   ├── LocationService.kt
│   │   │   │   │   ├── SOSService.kt
│   │   │   │   │   ├── NotificationService.kt
│   │   │   │   │   └── GeminiService.kt
│   │   │   │   ├── utils/
│   │   │   │   │   ├── PermissionManager.kt
│   │   │   │   │   ├── CryptoUtil.kt
│   │   │   │   │   ├── LocationUtil.kt
│   │   │   │   │   └── NotificationUtil.kt
│   │   │   │   └── MainActivity.kt
│   │   │   ├── res/
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   └── dimen.xml
│   │   │   │   ├── drawable/
│   │   │   │   └── mipmap/
│   │   │   └── AndroidManifest.xml
│   │   ├── test/
│   │   │   ├── LocationServiceTest.kt
│   │   │   ├── ContactRepositoryTest.kt
│   │   │   └── GeminiServiceTest.kt
│   │   └── androidTest/
│   │       ├── HomeScreenTest.kt
│   │       ├── SOSScreenTest.kt
│   │       └── LocationMapScreenTest.kt
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── .env.example
├── README.md
├── LICENSE
└── metadata.json
```

---

## 📋 Available Gradle Commands

```bash
# Build the app
./gradlew build

# Clean build
./gradlew clean build

# Run debug build
./gradlew assembleDebug

# Run release build
./gradlew assembleRelease

# Install on device/emulator
./gradlew installDebug
./gradlew installRelease

# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

# Run lint checks
./gradlew lint

# View code style violations
./gradlew detekt

# Generate documentation
./gradlew dokka
```

---

## 🔒 Permissions Required

```xml
<!-- Location -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />

<!-- Contacts & Phone -->
<uses-permission android:name="android.permission.READ_CONTACTS" />
<uses-permission android:name="android.permission.CALL_PHONE" />

<!-- Media & Camera -->
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />

<!-- Internet -->
<uses-permission android:name="android.permission.INTERNET" />

<!-- Notifications -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

<!-- System Alert Window (for emergency overlay)-->
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
```

---

## 🔐 Security & Privacy

### Implemented Security Measures
- ✅ End-to-end encryption for messages
- ✅ Biometric authentication (fingerprint/face)
- ✅ Secure local data storage (encrypted)
- ✅ Firebase security rules enforced
- ✅ HTTPS for all network requests
- ✅ API key protection (.env)
- ✅ Rate limiting on API calls
- ✅ User consent for data collection

### Privacy Policy
- 🔐 User data stored securely
- 🚫 No data sold to third parties
- 📋 GDPR compliant
- 🗑️ User data deletion on request
- 🔒 Location data encrypted

---

## 📊 Architecture

### Clean Architecture Pattern
```
Presentation Layer (UI - Jetpack Compose)
        ↓
Domain Layer (Use Cases)
        ↓
Data Layer (Repositories)
        ↓
Services Layer (Firebase, Gemini, Location)
```

### MVVM Pattern
- **Model**: Data classes & entities
- **View**: Compose UI screens
- **ViewModel**: Business logic & state management

---

## 🧪 Testing

### Unit Tests
```bash
./gradlew test
```

Tests include:
- Location service tests
- Contact repository tests
- Gemini API integration tests
- Permission manager tests

### Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

Tests include:
- UI screen tests (Roborazzi)
- Navigation tests
- Database tests
- Firebase integration tests

### Code Coverage
```bash
./gradlew jacocoTestReport
```

---

## 🚀 Deployment

### Build Release APK
```bash
# Set up keystore
export KEYSTORE_PATH=/path/to/my-upload-key.jks
export STORE_PASSWORD=your_store_password
export KEY_PASSWORD=your_key_password

# Build release APK
./gradlew assembleRelease

# Output: app/build/outputs/apk/release/app-release.apk
```

### Upload to Google Play Store
1. Go to [Google Play Console](https://play.google.com/console)
2. Create new app: "Safe-Her"
3. Upload APK
4. Add app details, screenshots, description
5. Set up pricing & distribution
6. Submit for review

---

## 📚 Features Documentation

### SOS Feature
- Trigger with customizable gesture
- Auto-send SMS/call to contacts
- Location auto-share
- Loud alarm (customizable)

### Location Tracking
- Real-time GPS updates
- Encrypted transmission
- Battery optimization
- Privacy controls

### AI Safety Chat
- Ask safety questions
- Get instant guidance
- Emergency procedure help
- Self-defense tips

### Emergency Contacts
- Add trusted people
- Quick dial
- Auto-notification on SOS
- Priority management

---

## 🤝 Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Follow Kotlin style guide
4. Write tests for new features
5. Submit a pull request

---

## 📞 Support

- 📧 **Email**: atharvraval05@gmail.com
- 💬 **GitHub Issues**: [Report issues](https://github.com/atharvraval05/Safe-Her/issues)
- 📱 **Emergency**: Call 112 (India) or local emergency number

---

## 📄 License

This project is licensed under the **MIT License** - see [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- Google Gemini AI for intelligent safety guidance
- Firebase for backend infrastructure
- Jetpack Compose team for modern UI toolkit
- Android community for excellent libraries

---

<div align="center">

**Built with ❤️ to empower and protect women**

*Safety is not a privilege, it's a right.*

Last Updated: June 28, 2026

</div>
