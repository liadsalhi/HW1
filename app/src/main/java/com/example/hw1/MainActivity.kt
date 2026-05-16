package com.example.hw1

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    // The X center of each lane (left, middle, right) - set after layout loads
    private val laneX = FloatArray(3)

    // Player's current lane (0=left, 1=middle, 2=right) and remaining lives
    private var playerLane = 1
    private var lives = 3

    // UI views
    private lateinit var gameArea: FrameLayout
    private lateinit var playerView: ImageView
    private lateinit var heart1: ImageView
    private lateinit var heart2: ImageView
    private lateinit var heart3: ImageView

    // Glove obstacles - spawned dynamically at runtime
    private val gloves = mutableListOf<ImageView>()
    private val GLOVE_COUNT = 3
    private val GLOVE_SPEED = 12f  // pixels per frame

    // Sizes converted to pixels in onCreate
    private var glovePx = 0
    private var playerW = 0
    private var playerH = 0

    // The game loop runs every 16ms (~60 frames per second)
    private val handler = Handler(Looper.getMainLooper())
    private var isRunning = false

    private val gameLoop = object : Runnable {
        override fun run() {
            if (isRunning) {
                tick()
                handler.postDelayed(this, 16L)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Convert dp values to pixels for this device's screen density
        glovePx = dp(60)
        playerW = dp(80)
        playerH = dp(80)

        // Connect variables to the views defined in activity_main.xml
        gameArea   = findViewById(R.id.gameArea)
        playerView = findViewById(R.id.playerView)
        heart1     = findViewById(R.id.heart1)
        heart2     = findViewById(R.id.heart2)
        heart3     = findViewById(R.id.heart3)

        // Left/right buttons move the player one lane at a time
        findViewById<Button>(R.id.btnLeft).setOnClickListener  { movePlayer(-1) }
        findViewById<Button>(R.id.btnRight).setOnClickListener { movePlayer(+1) }

        // Wait for the layout to finish drawing before we can read its size
        gameArea.post { initGame() }
    }

    // Sets up lane positions, places the player, and spawns gloves
    private fun initGame() {
        val w = gameArea.width.toFloat()
        val h = gameArea.height.toFloat()

        // Divide the screen into 3 equal lanes and store each center X
        laneX[0] = w / 6f
        laneX[1] = w / 2f
        laneX[2] = w * 5f / 6f

        // Place the player at the bottom center
        playerView.x = laneX[playerLane] - playerW / 2f
        playerView.y = h - playerH - dp(20).toFloat()

        spawnGloves(h)

        isRunning = true
        handler.post(gameLoop)
    }

    // Creates gloves and spreads them evenly above the screen so they arrive one by one
    private fun spawnGloves(gameHeight: Float) {
        repeat(GLOVE_COUNT) { index ->
            val glove = ImageView(this)
            glove.setImageResource(R.drawable.glove)
            glove.scaleType = ImageView.ScaleType.FIT_CENTER
            gameArea.addView(glove, FrameLayout.LayoutParams(glovePx, glovePx))

            // Random lane, staggered start position above the screen
            glove.x = laneX[Random.nextInt(3)] - glovePx / 2f
            glove.y = -glovePx.toFloat() - (index * gameHeight / GLOVE_COUNT)
            gloves.add(glove)
        }
    }

    // Called every frame: moves all gloves down and checks for collisions
    private fun tick() {
        val bottom = gameArea.height.toFloat()
        for (glove in gloves) {
            glove.y += GLOVE_SPEED

            when {
                glove.y > bottom -> recycleGlove(glove)           // passed the bottom - reset to top
                overlaps(glove)  -> { recycleGlove(glove); onPlayerHit() }  // hit the player
            }
        }
    }

    // Moves a glove back to a random lane at the top of the screen
    private fun recycleGlove(glove: ImageView) {
        glove.x = laneX[Random.nextInt(3)] - glovePx / 2f
        glove.y = -glovePx.toFloat()
    }

    // Moves the player left (-1) or right (+1), with a smooth animation
    private fun movePlayer(dir: Int) {
        val next = (playerLane + dir).coerceIn(0, 2)
        if (next == playerLane) return  // already at the edge, do nothing

        playerLane = next
        playerView.animate()
            .x(laneX[playerLane] - playerW / 2f)
            .setDuration(120)
            .start()
    }

    // Checks if a glove and the player overlap using bounding box collision
    // Hitboxes are shrunk by 25% so near-misses feel fair
    private fun overlaps(glove: ImageView): Boolean {
        val margin = 0.25f

        val gl = glove.x      + glovePx * margin / 2f
        val gr = gl            + glovePx * (1f - margin)
        val gt = glove.y      + glovePx * margin / 2f
        val gb = gt            + glovePx * (1f - margin)

        val pl = playerView.x + playerW * margin / 2f
        val pr = pl            + playerW * (1f - margin)
        val pt = playerView.y  + playerH * margin / 2f
        val pb = pt            + playerH * (1f - margin)

        return gr > pl && gl < pr && gb > pt && gt < pb
    }

    // Called when a glove hits the player
    private fun onPlayerHit() {
        lives--
        refreshHearts()
        buzz()

        if (lives <= 0) {
            // Game over - reset and keep playing
            Toast.makeText(this, "Game Over! Starting again...", Toast.LENGTH_LONG).show()
            lives = 3
            refreshHearts()
        } else {
            Toast.makeText(this, "Ouch! $lives lives left", Toast.LENGTH_SHORT).show()
        }
    }

    // Updates the heart icons to match the current number of lives
    private fun refreshHearts() {
        listOf(heart1, heart2, heart3).forEachIndexed { index, heart ->
            heart.setImageResource(
                if (index < lives) R.drawable.heart_full else R.drawable.heart_empty
            )
        }
    }

    // Vibrates the device for 200ms to give feedback on a hit
    private fun buzz() {
        val effect = VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vm = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vm.defaultVibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(effect)
        }
    }

    // Converts dp (density-independent pixels) to real screen pixels
    private fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()

    // Pause the game when the app goes to the background
    override fun onPause() {
        super.onPause()
        isRunning = false
    }

    // Resume the game when the app comes back to the foreground
    override fun onResume() {
        super.onResume()
        if (::gameArea.isInitialized && gameArea.width > 0) {
            isRunning = true
            handler.post(gameLoop)
        }
    }

    // Clean up the handler when the activity is destroyed to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(gameLoop)
    }
}