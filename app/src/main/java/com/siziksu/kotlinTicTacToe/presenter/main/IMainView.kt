package com.siziksu.kotlinTicTacToe.presenter.main

import android.app.Activity
import android.widget.ImageView

interface IMainView {

    /**
     * Gets the activity of the view.
     */
    fun getActivity(): Activity

    /**
     * When the player accepts a request.
     */
    fun onAcceptedRequest(opponentEmail: String)

    /**
     * When there is a game to listen to.
     */
    fun onGameListening(opponentEmail: String)

    /**
     * Communicates that the game is finshed.
     */
    fun onGameFinished()

    /**
     * Communicates to the view to set the turn visually after a move.
     *
     * @param player the player to give the turn.
     */
    fun setTurnVisually(player: Int)

    /**
     * Gets the game button for a supplied id.
     *
     * @param id the id of the button
     */
    fun getGameButton(id: Int): ImageView

    /**
     * Resets the game.
     */
    fun resetGame()

    /**
     * Resets the board (game buttons).
     */
    fun resetBoard()
}