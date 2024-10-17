# Current Topics of Software Engineering (2024) - Smart Alert Android App

## Project Overview

The Smart Alert Android App is designed to provide immediate notifications to citizens during high-risk events, such as natural disasters, in real time. The app enables users to report emergencies, receive location-based alerts, and manage their safety by staying informed about ongoing hazardous situations. The Smart Alert system is integrated with a backend that processes and categorizes these alerts, ensuring that only relevant notifications are sent to affected users.

This project was developed as part of the 7th-semester coursework for the "Modern Topics in Software Technology - Mobile Software" class at the University of Piraeus, Department of Informatics (Academic Year 2023-2024).

## Course Information

- **Institution:** University of Piraeus
- **Department:** Department of Informatics
- **Course:** Current Topics of Software Engineering (2024)
- **Semester:** 7th

## Technologies Used

- Kotlin
- Jetpack Compose
- Firebase Cloud Messaging (FCM)
- Firebase Authentication
- Firebase Realtime Database
- Firebase Cloud Storage
- SQLite

## App Features

1. **User Roles**

   - **Citizen:** Users can report a high-risk incident, including information like type (e.g., flood, fire), location, timestamp, and optionally attach a photo.
   - **Employee (Civil Protection):** Employees review, approve, or reject reported incidents and can trigger notifications for citizens in affected areas.

2. **Incident Reporting and Notification**

   - Citizens can report emergencies, categorized into pre-defined event types such as floods, fires, or earthquakes.
   - Notifications are automatically sent to users in close proximity to the event, including details such as location, timestamp, and safety instructions.
  
3. **Multilingual Support**

    The app supports English and one additional language, ensuring users receive information in their preferred language.

4. **Statistics and Event History**

    Users can view statistics about past incidents, filtered by time and category, through a dedicated interface.

## Setup Instructions

1. Clone the repository:
    ```bash
    git clone https://github.com/thkox/current-topics-of-software-engineering_2023-smart-alert.git
    ```

2. Open the project in Android Studio.
3. Configure Firebase credentials by adding the necessary `google-services.json` file.
4. Build and run the app on an Android device or emulator.

## Screenshots



## Related Repositories
[Smart Alert Backend](https://github.com/thkox/smart-alert-server)

The Smart Alert Backend serves as the core infrastructure that processes and ranks reports of high-risk incidents submitted by users through the Smart Alert mobile app. The backend is responsible for managing user data, triggering notifications, and prioritizing alerts based on factors such as proximity and severity. It works seamlessly with the Android app to ensure that only relevant notifications are sent to users in affected areas.

## Contributors

<table>
  <tr>
    <td align="center"><a href="https://github.com/ApostolisSiampanis"><img src="https://avatars.githubusercontent.com/u/75365398?v=4" width="100px;" alt="Apostolis Siampanis"/><br /><sub><b>Apostolis Siampanis</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/thkox"><img src="https://avatars.githubusercontent.com/u/79880468?v=4" width="100px;" alt="Theodore Koxanoglou"/><br /><sub><b>Theodore Koxanoglou</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/AlexanderCholis"><img src="https://avatars.githubusercontent.com/u/66769337?v=4" width="100px;" alt="Alexander Cholis"/><br /><sub><b>Alexander Cholis</b></sub></a><br /></td>
  </tr>
</table>

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details.
