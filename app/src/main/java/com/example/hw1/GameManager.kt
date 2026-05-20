package com.example.hw1

import android.view.View
import android.widget.ImageView

class GameManager(
    private val players: Array<ImageView>,
    private val gloves: Array<Array<ImageView>>
) {

    companion object {
        const val LANES = 3
        private const val MAX_LIVES = 3
        private const val ROWS = 8
        private const val SPAWN_INTERVAL = 3
    }

    private var currentLane: Int = LANES / 2
    private var lives: Int = MAX_LIVES
    private var frameCount = 0

    init {
        showOnlyCurrentPlayer()
        resetGloves()
    }

    fun moveLeft() = move(-1)
    fun moveRight() = move(1)

    private fun move(direction: Int) {
        val newLane = currentLane + direction
        if (newLane in 0 until LANES) {
            currentLane = newLane
            showOnlyCurrentPlayer()
        }
    }

    fun getLives(): Int = lives

    fun onHit(): Boolean {
        lives--
        return lives > 0
    }

    fun resetGame() {
        lives = MAX_LIVES
        frameCount = 0
        resetGloves()
    }

    private fun showOnlyCurrentPlayer() {
        players.forEachIndexed { index, player ->
            player.visibility = if (index == currentLane) View.VISIBLE else View.INVISIBLE
        }
    }

    fun resetGloves() {
        for (lane in 0 until LANES)
            for (row in 0 until ROWS)
                gloves[lane][row].visibility = View.INVISIBLE
    }

    fun tick(): Boolean {
        frameCount++

        for (lane in 0 until LANES) {
            for (row in ROWS - 1 downTo 1) {
                gloves[lane][row].visibility = gloves[lane][row - 1].visibility
            }
            gloves[lane][0].visibility = View.INVISIBLE
        }

        if (frameCount % SPAWN_INTERVAL == 0) {
            val emptyLanes = (0 until LANES).filter {
                gloves[it][0].visibility == View.INVISIBLE
            }
            if (emptyLanes.isNotEmpty()) {
                gloves[emptyLanes.random()][0].visibility = View.VISIBLE
            }
        }

        val hit = gloves[currentLane][ROWS - 1].visibility == View.VISIBLE
        if (hit) {
            gloves[currentLane][ROWS - 1].visibility = View.INVISIBLE
        }
        return hit
    }
}
