package com.siziksu.kotlinTicTacToe.common.manager

import com.siziksu.kotlinTicTacToe.R
import com.siziksu.kotlinTicTacToe.common.Constants
import com.siziksu.kotlinTicTacToe.common.model.Winner
import java.util.*

class GameLogic {

    var currentPlayer: Int = Constants.FIRST_PLAYER
    var gameFinished = false
    val moves: Int
        get() = player1Buttons.size + player2Buttons.size

    private var player1Buttons = ArrayList<Int>()
    private var player2Buttons = ArrayList<Int>()

    /**
     * Returns if the player can play depending on the player number and the number of moves.
     *
     * @return true or false
     */
    fun canPlay(player: Int): Boolean {
        when (player) {
            Constants.FIRST_PLAYER -> return moves % 2 == 0
            Constants.SECOND_PLAYER -> return moves % 2 != 0
            else -> return false
        }
    }

    /**
     * Resets the game.
     */
    fun resetGame() {
        currentPlayer = Constants.FIRST_PLAYER
        gameFinished = false
        clearButtons()
    }

    /**
     * Clears the moves.
     */
    fun clearButtons() {
        player1Buttons.clear()
        player2Buttons.clear()
    }

    /**
     * Manages the moves. It returns the drawable to be displayed for the move depending on the current player.
     *
     * @return the drawable to display for the move
     */
    fun play(buttonId: Int): Int {
        when (currentPlayer) {
            Constants.FIRST_PLAYER -> {
                player1Buttons.add(buttonId)
                currentPlayer = Constants.SECOND_PLAYER
                return R.drawable.cell_x
            }
            Constants.SECOND_PLAYER -> {
                player2Buttons.add(buttonId)
                currentPlayer = Constants.FIRST_PLAYER
                return R.drawable.cell_o
            }
            else -> return 0
        }
    }

    /**
     * Checks all the win conditions. Possible returns are Player1, Player2, Draw or null if none of these.
     * If it's not a null return, it will return a Winner object with the winner and the condition the made
     * him win.
     *
     * @return Winner object
     */
    fun checkWinner(): Winner? {
        val player: Int
        val winnerValues: IntArray
        if (player1Buttons.contains(1) && player1Buttons.contains(2) && player1Buttons.contains(3)) {
            player = Constants.FIRST_PLAYER
            winnerValues = intArrayOf(1, 2, 3)
        } else if (player1Buttons.contains(4) && player1Buttons.contains(5) && player1Buttons.contains(6)) {
            player = Constants.FIRST_PLAYER
            winnerValues = intArrayOf(4, 5, 6)
        } else if (player1Buttons.contains(7) && player1Buttons.contains(8) && player1Buttons.contains(9)) {
            player = Constants.FIRST_PLAYER
            winnerValues = intArrayOf(7, 8, 9)
        } else if (player1Buttons.contains(1) && player1Buttons.contains(4) && player1Buttons.contains(7)) {
            player = Constants.FIRST_PLAYER
            winnerValues = intArrayOf(1, 4, 7)
        } else if (player1Buttons.contains(2) && player1Buttons.contains(5) && player1Buttons.contains(8)) {
            player = Constants.FIRST_PLAYER
            winnerValues = intArrayOf(2, 5, 8)
        } else if (player1Buttons.contains(3) && player1Buttons.contains(6) && player1Buttons.contains(9)) {
            player = Constants.FIRST_PLAYER
            winnerValues = intArrayOf(3, 6, 9)
        } else if (player1Buttons.contains(1) && player1Buttons.contains(5) && player1Buttons.contains(9)) {
            player = Constants.FIRST_PLAYER
            winnerValues = intArrayOf(1, 5, 9)
        } else if (player1Buttons.contains(7) && player1Buttons.contains(5) && player1Buttons.contains(3)) {
            player = Constants.FIRST_PLAYER
            winnerValues = intArrayOf(7, 5, 3)
        } else if (player2Buttons.contains(1) && player2Buttons.contains(2) && player2Buttons.contains(3)) {
            player = Constants.SECOND_PLAYER
            winnerValues = intArrayOf(1, 2, 3)
        } else if (player2Buttons.contains(4) && player2Buttons.contains(5) && player2Buttons.contains(6)) {
            player = Constants.SECOND_PLAYER
            winnerValues = intArrayOf(4, 5, 6)
        } else if (player2Buttons.contains(7) && player2Buttons.contains(8) && player2Buttons.contains(9)) {
            player = Constants.SECOND_PLAYER
            winnerValues = intArrayOf(7, 8, 9)
        } else if (player2Buttons.contains(1) && player2Buttons.contains(4) && player2Buttons.contains(7)) {
            player = Constants.SECOND_PLAYER
            winnerValues = intArrayOf(1, 4, 7)
        } else if (player2Buttons.contains(2) && player2Buttons.contains(5) && player2Buttons.contains(8)) {
            player = Constants.SECOND_PLAYER
            winnerValues = intArrayOf(2, 5, 8)
        } else if (player2Buttons.contains(3) && player2Buttons.contains(6) && player2Buttons.contains(9)) {
            player = Constants.SECOND_PLAYER
            winnerValues = intArrayOf(3, 6, 9)
        } else if (player2Buttons.contains(1) && player2Buttons.contains(5) && player2Buttons.contains(9)) {
            player = Constants.SECOND_PLAYER
            winnerValues = intArrayOf(1, 5, 9)
        } else if (player2Buttons.contains(7) && player2Buttons.contains(5) && player2Buttons.contains(3)) {
            player = Constants.SECOND_PLAYER
            winnerValues = intArrayOf(7, 5, 3)
        } else if (moves == 9) return Winner(Constants.DRAW, intArrayOf())
        else return null
        gameFinished = true
        return Winner(player, winnerValues)
    }
}