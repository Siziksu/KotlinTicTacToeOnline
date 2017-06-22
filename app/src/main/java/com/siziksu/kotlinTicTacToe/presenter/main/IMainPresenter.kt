package com.siziksu.kotlinTicTacToe.presenter.main

interface IMainPresenter {

    /**
     * Lifecycle method.
     */
    fun onResume()

    /**
     * Lifecycle method.
     */
    fun onStop()

    /**
     * Sets the user email.
     *
     * @param email the email to set
     */
    fun setEmail(email: String?)

    /**
     * Sends a request to a player.
     *
     * @param opponentEmail the opponent email
     */
    fun sendRequest(opponentEmail: String)

    /**
     * Accepts a request from a player. It will init the game as a
     * second player.
     *
     * @param opponentEmail the opponent email
     */
    fun acceptRequest(opponentEmail: String)

    /**
     * Starts listening incoming requests and accepted requests. If there is one, it will init the game as a
     * first player. Also checks if there is a game already in progress. If that is the case, it will return
     * the opponent's email and start listening the movements. If there is not a game in progress just won't
     * do anything.
     */
    fun startFullListening()

    /**
     * Starts listening incoming requests and accepted requests.
     */
    fun startEventListeners()

    /**
     * To reset a game after is over.
     */
    fun resetGame()

    /**
     * Sends the move to the opponent.
     *
     * @param id the game button id
     *
     */
    fun sendMove(id: Int)

    /**
     * Returns if the player is currently playing a game.
     */
    fun isPlaying(): Boolean
}
