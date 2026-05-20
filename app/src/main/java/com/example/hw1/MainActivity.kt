package com.example.hw1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var main_IMG_hearts: Array<ImageView>
    private lateinit var main_IMG_players: Array<ImageView>
    private lateinit var gloves: Array<Array<ImageView>>

    private lateinit var gameManager: GameManager

    private val handler = Handler(Looper.getMainLooper())
    private val interval: Long = 700L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViews()
        initGame()
        initViews()
        startGameLoop()
    }

    private fun findViews() {
        main_IMG_hearts = arrayOf(
            findViewById(R.id.heart1),
            findViewById(R.id.heart2),
            findViewById(R.id.heart3)
        )

        main_IMG_players = arrayOf(
            findViewById(R.id.player_0),
            findViewById(R.id.player_1),
            findViewById(R.id.player_2)
        )

        gloves = arrayOf(
            arrayOf(
                findViewById(R.id.glove_00), findViewById(R.id.glove_01),
                findViewById(R.id.glove_02), findViewById(R.id.glove_03),
                findViewById(R.id.glove_04), findViewById(R.id.glove_05),
                findViewById(R.id.glove_06), findViewById(R.id.glove_07)
            ),
            arrayOf(
                findViewById(R.id.glove_08), findViewById(R.id.glove_09),
                findViewById(R.id.glove_10), findViewById(R.id.glove_11),
                findViewById(R.id.glove_12), findViewById(R.id.glove_13),
                findViewById(R.id.glove_14), findViewById(R.id.glove_15)
            ),
            arrayOf(
                findViewById(R.id.glove_16), findViewById(R.id.glove_17),
                findViewById(R.id.glove_18), findViewById(R.id.glove_19),
                findViewById(R.id.glove_20), findViewById(R.id.glove_21),
                findViewById(R.id.glove_22), findViewById(R.id.glove_23)
            )
        )
    }

    private fun initGame() {
        gameManager = GameManager(main_IMG_players, gloves)
        updateHearts()
    }

    private fun initViews() {
        findViewById<Button>(R.id.btnLeft).setOnClickListener { gameManager.moveLeft() }
        findViewById<Button>(R.id.btnRight).setOnClickListener { gameManager.moveRight() }
    }

    private fun startGameLoop() {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(object : Runnable {
            override fun run() {
                val hit = gameManager.tick()

                if (hit) {
                    val alive = gameManager.onHit()
                    updateHearts()
                    SignalManager.toast(this@MainActivity, "התנגשות!")
                    SignalManager.vibrate(this@MainActivity)

                    if (!alive) {
                        SignalManager.toast(this@MainActivity, "Game Over! Starting again...")
                        gameManager.resetGame()
                        updateHearts()
                    }
                }

                handler.postDelayed(this, interval)
            }
        }, interval)
    }

    private fun updateHearts() {
        for (i in main_IMG_hearts.indices) {
            main_IMG_hearts[i].visibility =
                if (i < gameManager.getLives()) View.VISIBLE else View.INVISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        startGameLoop()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
