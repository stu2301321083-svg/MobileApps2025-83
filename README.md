# VetClinicMobile – MobileApps2025-83

## 1. Idea
**VetClinicMobile** is an Android application designed to manage a small veterinary clinic. It provides functionality for handling doctors, pets and appointments in a structured, user-friendly mobile interface.  
The goal is to demonstrate full mobile application architecture using Kotlin, Room database (SQLite), MVVM pattern, and material design (light & dark themes).  
This project is submitted for the course “Mobile Applications” (Academic year 2025) under the repository **MobileApps2025-83**.

## 2. How it works
The application is structured around three main modules (tabs/fragments):
- **Doctors** – allows listing, adding, editing, deleting doctors (name, specialization).
- **Pets** – allows listing, adding, editing, deleting pets (name, type).
- **Appointments** – allows listing, creating, editing, deleting appointments. Each appointment links a doctor and a pet via their IDs and uses a date and reason.  
  When the user launches the app, the local database (Room/SQLite) is loaded. Navigation is via bottom navigation or tabs. The UI theme supports light and dark modes automatically. Data persists between app launches.  
  Future enhancement (for highest grade) includes an additional feature such as QR-code generation for pet cards, or REST API sync.

## 3. Architecture
- **Language**: Kotlin (or optionally Java)
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: latest stable Android version
- **Architecture pattern**: MVVM + Repository layer
- **Data layer**: Room (SQLite) for local storage
- **Presentation layer**: Activities / Fragments, ViewModels, LiveData / StateFlow
- **UI/UX**: Material 3 components, light/dark theme toggle, responsive layout
- **Version control**: GitHub – public repository named `MobileApps2025-83`
- **Tests**: Unit tests covering at least 15% of business logic; at least one Espresso UI test for main user flow

## 4. User flow
1. On launch, the main screen shows navigation across three tabs (Doctors / Pets / Appointments).
2. User selects the “Doctors” tab: can view list of doctors, tap “Add” to input name & specialization, select a doctor row to edit or delete.
3. User selects the “Pets” tab: can view list of pets, add new pet (name, type), edit or delete existing.
4. User selects the “Appointments” tab: drop-down or spinner selection of doctor and pet (populated from existing records), input date (YYYY-MM-DD) and reason, then add. Also features update and deletion of appointments. Search functionality: filter by doctor, pet, or date.
5. Data is automatically saved to Room. On app restart, all data remains intact.
6. (For highest grade) Additional feature: e.g., scanning a QR code to retrieve pet info, or syncing appointments to a remote REST endpoint, etc.

## 5. Steps to run
1. Clone the repository:
```bash
   git clone https://github.com/stu2301321083-svg/MobileApps2025-83.git
   ```
2. 	Open Android Studio and choose Open an existing project, select the MobileApps2025-83 directory.
3.	Wait for Gradle sync and build to complete.
4.	Connect an Android device or start an emulator.
5.	Run the application (Run → app) or build a release APK (Build → Generate Signed Bundle / APK).
6.	(Optional) Use the APK in /apk/app-release.apk for installation.

## 6. Test accounts

No remote authentication is currently required (local storage only).
For demo purposes:
•	Launch the app.
•	Use “Add Doctor” to create e.g., Dr. Smith, Veterinary Surgery.
•	Use “Add Pet” to create e.g., Fluffy, Cat.
•	In Appointments tab: select Dr. Smith, Fluffy, date 2025-11-17, reason “Annual check-up”.

## 7. Screenshots

(Insert sample screenshots here)
•	Doctors tab list view
•	Pets tab edit view
•	Appointments tab creation view

## 8. APK

The release APK is located at:
/apk/app-release.apk

## 9. License

This project is for academic assessment and uses no third-party licensed code except permitted libraries. For open sourcing, you may apply MIT or Apache 2.0 license as appropriate.

## 10. Author
Name:   Valeriia Dehtiarova
Faculty number:  2301321083
GitHub: https://github.com/stu2301321083-svg
Repository: https://github.com/stu2301321083-svg/MobileApps2025-83