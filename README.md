# 📱 ByteWisdom

ByteWisdom is a simple motivational Android app built with **Jetpack Compose** and **Kotlin**.  
The app delivers daily motivational quotes along with random quotes, pulling from two sources:

- ✅ **Local deterministic quotes** (built-in, no network required)  
- 🌐 **ZenQuotes API** (https://zenquotes.io/) — includes *quote of the day* and *random quotes*  

Users can sign in, view their daily quote, fetch a new random one, or pull fresh inspiration from ZenQuotes.

---

## ✨ Features

- **Sign-in flow** (username saved in memory for the session)  
- **Daily local quote** (based on the date, consistent for all users)  
- **Random local quote** (manual refresh)  
- **ZenQuotes integration**  
  - Quote of the day (changes once every 24 hours)  
  - Random quote (extra inspiration)  
- **Rate limit handling**  
  - ZenQuotes API has a limit of 5 requests / 30 seconds  
  - App shows a Snackbar message when the limit is hit  
- Built with **Jetpack Compose**, **ViewModel**, **LiveData**, **Retrofit**, and **Moshi**

---

## 🖼️ Screenshots

| Home Screen | With ZenQuotes |
|-------------|----------------|
| ![Home](docs/screenshots/home.png) | ![ZenQuotes](docs/screenshots/zenquotes.png) |

---

## 🛠️ Tech Stack

- **Language:** Kotlin  
- **UI:** Jetpack Compose + Material 3  
- **Architecture:** MVVM (ViewModel + Repository)  
- **Persistence:** DataStore (for saving daily quotes)  
- **Networking:** Retrofit + OkHttp + Moshi  
- **Async:** Coroutines + LiveData  

---

## 🚀 Getting Started

1. Clone the repo:
   ```bash
   git clone https://github.com/<your-username>/ByteWisdom.git
   cd ByteWisdom
