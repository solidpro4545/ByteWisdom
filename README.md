# 📱 ByteWisdom

ByteWisdom is a simple motivational Android app built with **Jetpack Compose** and **Kotlin**.  
The app delivers daily motivational quotes along with random quotes, pulling from two sources:

- ✅ **Local deterministic quotes** (built-in, no network required)  
- 🌐 **ZenQuotes API** (https://zenquotes.io/) — includes *quote of the day* and *random quotes*  

Users can now **register and sign in** with local accounts stored securely in a Room (SQLite) database.  
Passwords are protected using **PBKDF2 with SHA-256** and unique salts for each user.

---

## ✨ Features

- **Authentication**
  - Register new accounts (username + password)  
  - Sign in/out with session persistence (stored in SharedPreferences)  
  - Passwords stored as salted PBKDF2 hashes, not plain text ✅
- **Daily local quote** (based on the date, consistent for all users)  
- **Random local quote** (manual refresh)  
- **ZenQuotes integration**  
  - Quote of the day (changes once every 24 hours)  
  - Random quote (extra inspiration)  
- **Rate limit handling**  
  - ZenQuotes API has a limit of 5 requests / 30 seconds  
  - App shows a Snackbar message when the limit is hit  
- Built with **Jetpack Compose**, **ViewModel**, **LiveData**, **Room**, **Retrofit**, and **Moshi**

---

## 🖼️ Screenshots

| Home Screen | With ZenQuotes | Login/Register |
|-------------|----------------|----------------|
| ![Home](docs/screenshots/home.png) | ![ZenQuotes](docs/screenshots/zenquotes.png) | ![Auth](docs/screenshots/auth.png) |

---

## 🛠️ Tech Stack

- **Language:** Kotlin  
- **UI:** Jetpack Compose + Material 3  
- **Architecture:** MVVM (ViewModel + Repository)  
- **Persistence:**  
  - DataStore (daily quote storage)  
  - Room (user accounts & authentication)  
- **Security:** PBKDF2 password hashing + unique salt per user  
- **Networking:** Retrofit + OkHttp + Moshi  
- **Async:** Coroutines + LiveData  

---

## 🚀 Getting Started

1. Clone the repo:
   ```bash
   git clone https://github.com/<your-username>/ByteWisdom.git
   cd ByteWisdom
