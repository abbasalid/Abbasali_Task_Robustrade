📈 News Application

A sample News application to view your Google News, built with Kotlin, Jetpack Compose, and Clean
MVVM Architecture.

---

🛠️ Tech Stack

This app demonstrates modern Android development best practices:

📱 UI → Jetpack Compose

🏗️ Architecture → MVVM + Clean

⚡ Concurrency → Kotlin Coroutines + Flow

💉 Dependency Injection → Dagger Hilt

🧪 Testing → JUnit, MockK

---

📂 Project Structure

The project follows Clean MVVM Architecture. All main layers are inside the app/ module:
app/
```bash
app/                
├─ 🏢 Application.kt        # Main Application class
├─ data/                   # 📦 Repositories implementation, Response Models, Api service
├─ domain/                 # 🎯 UseCases, UseCases implementation, Domain models, Repository interfaces
└─ presentation/           # 🖥️ Activity, ViewModel, Compose screens
```

---

📱 Screen
![ss3](https://github.com/user-attachments/assets/842b12c1-492e-4795-9da5-ed47c6c80d24)
![ss1](https://github.com/user-attachments/assets/e64fc183-ef4c-48e6-a083-28d237f1cd2c)
![ss2](https://github.com/user-attachments/assets/5c09dc8a-61b6-481f-9494-8ad0d22f0bab)
