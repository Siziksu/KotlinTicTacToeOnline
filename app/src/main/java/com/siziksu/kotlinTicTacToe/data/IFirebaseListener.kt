package com.siziksu.kotlinTicTacToe.data

interface IFirebaseListener {

    /**
     * When an incoming request is fired.
     */
    fun onIncomingRequests(opponentEmail: String)

    /**
     * When a request is accepted.
     */
    fun onAcceptedRequests(opponentEmail: String)

    /**
     * When a game event is fired.
     */
    fun onGameEvent(moves: HashMap<*, *>)
}
