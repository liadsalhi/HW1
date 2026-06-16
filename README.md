# HW1 – Dodge the Gloves                                                                                                                                                      
   2 +                                                                                                                                                                             
   3 +**Assignment 1 | User Interface Development Course**                                                                                                                         
   4 +                                                                                                                                                                              
   5 +An Android dodge game built with Kotlin and XML layouts. The player controls Mike Tyson and must avoid boxing gloves falling down three lanes.                                
   6 +                                                                                                                                                                              
   7 +---                                                                                                                                                                           
   8 +                                                                                                                                                                              
   9 +## Gameplay                                                                                                                                                                   
  10 +                                                                                                                                                                              
  11 +- Boxing gloves fall from the top of the screen across **3 lanes**                                                                                                            
  12 +- Use the **← / →** buttons to move the player between lanes                                                                                                                  
  13 +- The player starts with **3 lives** (hearts)                                                                                                                                 
  14 +- Each collision removes one life and triggers a vibration + toast notification                                                                                               
  15 +- Losing all 3 lives resets the game automatically                                                                                                                            
  16 +                                                                                                                                                                              
  17 +## Project Structure                                                                                                                                                          
  18 +                                                                                                                                                                              
  19 +```                                                                                                                                                                           
  20 +app/src/main/java/com/example/hw1/                                                                                                                                            
  21 +├── MainActivity.kt    # Activity — game loop, UI binding, lifecycle                                                                                                          
  22 +├── GameManager.kt     # Game logic — movement, glove spawning, collision detection                                                                                           
  23 +└── SignalManager.kt   # Utility — toast messages and vibration feedback                                                                                                      
  24 +```                                                                                                                                                                           
  25 +                                                                                                                                                                              
  26 +## Tech Stack                                                                                                                                                                 
  27 +                                                                                                                                                                              
  28 +- **Language:** Kotlin                                                                                                                                                        
  29 +- **UI:** XML layouts (LinearLayout / RelativeLayout)                                                                                                                         
  30 +- **Min SDK:** 26 (Android 8.0)                                                                                                                                               
  31 +- **Target SDK:** 36 
