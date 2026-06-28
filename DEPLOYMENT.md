# Deployment & Build Guide

## 📦 Build & Release Process

### Prerequisites
- Android Studio Jellyfish or latest
- JDK 11+
- Gradle 8.0+
- Android SDK API 36

---

## 🏗️ Build Types

### Debug Build
```bash
# Build debug APK
./gradlew assembleDebug

# Install on device
./gradlew installDebug

# Run with logging
./gradlew installDebug --info
```

### Release Build
```bash
# Build release APK
./gradlew assembleRelease

# Build signed release
./gradlew bundleRelease
```

---

## 🔐 Signing Configuration

### Create Keystore
```bash
keytool -genkey -v -keystore my-upload-key.jks -keyalg RSA \
  -keysize 2048 -validity 10000 -alias upload
```

### Setup Environment Variables
```bash
export KEYSTORE_PATH=/path/to/my-upload-key.jks
export STORE_PASSWORD=your_store_password
export KEY_PASSWORD=your_key_password
```

### Build Signed APK
```bash
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
```

---

## 📱 Google Play Store Deployment

### Step 1: Prepare App
1. Increase `versionCode` in `build.gradle.kts`
2. Update `versionName`
3. Update CHANGELOG.md
4. Run all tests
5. Build signed APK/AAB

### Step 2: Create Developer Account
- Go to [Google Play Console](https://play.google.com/console)
- Sign in with Google account
- Pay $25 one-time fee
- Accept agreements

### Step 3: Create App
1. Click "Create App"
2. App name: "Safe-Her"
3. Default language: English
4. App category: Health & Fitness / Lifestyle
5. Accept policies

### Step 4: Add App Information
- **Short description** (50 chars)
- **Full description** (4000 chars)
- **Screenshots** (2-8 per device type)
- **Feature graphic** (1024 × 500)
- **App icon** (512 × 512)
- **Privacy policy** URL
- **Contact details**

### Step 5: Upload Build
1. Go to Testing → Internal Testing
2. Click "Create new release"
3. Upload APK/AAB
4. Add release notes
5. Review & publish

### Step 6: Beta Testing (Optional)
1. Move to Testing → Closed Testing
2. Add beta testers
3. Collect feedback
4. Fix issues

### Step 7: Release to Production
1. Move to Production
2. Review store listing
3. Complete pricing & distribution
4. Submit for review

---

## ✅ Pre-Release Checklist

### Code Quality
- [ ] All tests passing
- [ ] No lint warnings
- [ ] Code coverage > 70%
- [ ] No deprecated APIs used

### Security
- [ ] API keys in .env (not in code)
- [ ] Biometric auth working
- [ ] Encryption functional
- [ ] Firebase rules updated
- [ ] Privacy policy reviewed

### Performance
- [ ] App size optimized
- [ ] No ANRs (Application Not Responding)
- [ ] Startup time < 3s
- [ ] Memory leaks fixed

### Functionality
- [ ] All features tested on device
- [ ] SOS button working
- [ ] Location tracking accurate
- [ ] Gemini AI responding
- [ ] Notifications working
- [ ] Offline mode functional

### UI/UX
- [ ] All screens tested
- [ ] Responsive on various devices
- [ ] Accessibility checked (TalkBack)
- [ ] Dark mode working
- [ ] Landscape orientation tested

### Documentation
- [ ] README updated
- [ ] CHANGELOG updated
- [ ] Privacy policy available
- [ ] Help docs complete
- [ ] Contact support info added

### Store Listing
- [ ] Compelling title
- [ ] Clear description
- [ ] Quality screenshots
- [ ] Proper categorization
- [ ] Contact information

---

## 📊 Version Management

### Semantic Versioning
Format: `MAJOR.MINOR.PATCH`

- **MAJOR**: Breaking changes
- **MINOR**: New features
- **PATCH**: Bug fixes

Example: `1.2.3` → Major 1, Minor 2, Patch 3

### Version Code (Internal)
- Used for update detection
- Incremented for every release
- Cannot be decreased

```gradle
versionCode = 1 // Increment for each release
versionName = "1.0.0" // Semantic versioning
```

---

## 🚀 Continuous Deployment

### GitHub Actions (Future)
```yaml
name: Build & Release

on:
  push:
    tags:
      - 'v*'

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Build
        run: ./gradlew assembleRelease
      - name: Upload to Play Store
        run: # Upload script
```

---

## 🔄 Update Process

### Minor Update
1. Update version in gradle
2. Push code to `main`
3. Create release tag
4. Build and sign APK
5. Upload to Play Store

### Hotfix
1. Create hotfix branch
2. Fix critical issue
3. Test thoroughly
4. Merge to main
5. Release immediately

---

## 📈 Monitoring Post-Release

### Firebase Analytics
- Monitor user engagement
- Track crash reports
- Check performance

### Google Play Console
- Monitor ratings & reviews
- Check installation stats
- Track uninstalls
- Monitor ANR rate

### User Feedback
- Read store reviews
- Check GitHub issues
- Respond to users
- Fix reported bugs

---

## 🆘 Rollback Procedure

If critical issues found:
1. Unpublish current version
2. Release hotfix version
3. Communicate with users
4. Provide workarounds

---

## 📞 Support

For deployment issues:
- Check Android developer docs
- Review Google Play policies
- Contact Google Play support
- Email: atharvraval05@gmail.com

---

**Last Updated**: June 28, 2026
