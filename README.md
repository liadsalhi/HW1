# 🥊 Run Tyson

An Android dodge game where the player must avoid falling boxing gloves across 3 lanes.

---

## 🎮 Controls

| Control | Description |
|---------|-------------|
| ← Button | Move player one lane to the left |
| → Button | Move player one lane to the right |

---

## 📦 Features

- 3-lane grid with falling gloves (obstacles)
- 3 lives — lose one each time a glove reaches your lane
- Vibration + toast alert on collision
- Game resets automatically on Game Over

---

## 🧱 Project Structure
com.example.hw1

├── MainActivity.kt     # Game screen, Handler loop, button input, lifecycle

├── GameManager.kt      # 3×8 grid, glove spawning, movement, collision detection

└── SignalManager.kt    # Toast + vibration utilities

---

## 🛠️ Tech Stack

- Kotlin
- Android SDK (minSdk 26)
- XML Layouts (LinearLayout / RelativeLayout)
- Handler — game tick loop
- Vibrator API — haptic feedback on collision                                                                         
