+# 🥊 Run Tyson                                                                                                                                                     
       2 +                                                                                                                                                                   
       3 +An Android dodge game where the player must avoid falling boxing gloves across 3 lanes.                                                                                   
       4 +                                                                                                                                                                   
       5 +---                                                                                                                                                                       
       6 +                                                                                                                                                                          
       7 +## 🎮 Controls                                                                                                                                                            
       8 +                                                                                                                                                                   
       9 +| Control | Description |                                                                                                                                                 
      10 +|--------|-------------|                                                                                                                                                  
      11 +| ← Button | Move player one lane to the left |                                                                                                                           
      12 +| → Button | Move player one lane to the right |                                                                                                                          
      13 +                                                                                                                                                                          
      14 +---                                                                                                                                                                       
      15 +                                                                                                                                                                          
      16 +## 📦 Features                                                                                                                                                            
      17 +                                                                                                                                                                          
      18 +- 3-lane grid with falling gloves (obstacles)                                                                                                                             
      19 +- 3 lives — lose one each time a glove reaches your lane                                                                                                                  
      20 +- Vibration + toast alert on collision                                                                                                                                    
      21 +- Game resets automatically on Game Over                                                                                                                                  
      22 +                                                                                                                                                                          
      23 +---                                                                                                                                                                       
      24 +                                                                                                                                                                          
      25 +## 🧱 Project Structure                                                                                                                                                   
      26 +                                                                                                                                                                          
      27 +```                                                                                                                                                                       
      28 +com.example.hw1                                                                                                                                                           
      29 +├── MainActivity.kt     # Game screen, Handler loop, button input, lifecycle                                                                                              
      30 +├── GameManager.kt      # 3×8 grid, glove spawning, movement, collision detection                                                                                         
      31 +└── SignalManager.kt    # Toast + vibration utilities                                                                                                                     
      32 +```                                                                                                                                                                       
      33 +                                                                                                                                                                          
      34 +---                                                                                                                                                                       
      35 +                                                                                                                                                                          
      36 +## 🛠️ Tech Stack                                                                                                                                                          
      37 +                                                                                                                                                                          
      38 +- Kotlin                                                                                                                                                                  
      39 +- Android SDK (minSdk 26)                                                                                                                                                 
      40 +- XML Layouts (LinearLayout / RelativeLayout)                                                                                                                             
      41 +- Handler — game tick loop                                                                                                                                                
      42 +- Vibrator API — haptic feedback on collision                                                                            
