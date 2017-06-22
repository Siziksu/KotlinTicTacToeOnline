package com.siziksu.kotlinTicTacToe.data

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.siziksu.kotlinTicTacToe.common.Constants
import com.siziksu.kotlinTicTacToe.common.utils.getUserFromEmailForFirebase

class FirebaseDatabaseClient(context: Context) {

    companion object {
        private const val TAG: String = "FirebaseDatabaseClient"
    }

    private val preferences by lazy { PreferencesClient(context) }
    private val firebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    private val gameListeners: HashMap<DatabaseReference, ValueEventListener> by lazy { HashMap<DatabaseReference, ValueEventListener>() }
    private val incomingRequestsListeners: HashMap<DatabaseReference, ValueEventListener> by lazy { HashMap<DatabaseReference, ValueEventListener>() }
    private val acceptedRequestsListeners: HashMap<DatabaseReference, ValueEventListener> by lazy { HashMap<DatabaseReference, ValueEventListener>() }
    private var firebaseListener: IFirebaseListener? = null

    fun setListener(firebaseListener: IFirebaseListener): FirebaseDatabaseClient {
        this.firebaseListener = firebaseListener
        return this
    }

    /**
     * Creates a bundle to send to another activity from a FirebaseUser.
     */
    fun getUserBundle(currentUser: FirebaseUser): Bundle {
        val bundle: Bundle = Bundle()
        bundle.putString(Constants.UID_KEY, currentUser.uid)
        bundle.putString(Constants.EMAIL_KEY, currentUser.email)
        return bundle
    }

    /**
     * Sends a request to an opponent.
     *
     * @param opponentEmail the opponent email
     */
    fun sendRequest(opponentEmail: String?) {
        if (isPlaying()) return
        userReference(opponentEmail)
                .child(Constants.INCOMING_REQUEST_FROM_KEY)
                .push()
                .setValue(preferences.getEmail())
    }

    /**
     * Sends a request from an opponent.
     *
     * @param opponentEmail the opponent email
     */
    fun acceptRequest(opponentEmail: String?) {
        if (isPlaying()) return
        userReference(opponentEmail)
                .child(Constants.ACCEPTED_REQUEST_FROM_KEY)
                .child(Constants.EMAIL_KEY)
                .setValue(preferences.getEmail())
    }

    /**
     * Clears the incoming requests.
     */
    fun clearIncomingRequests() {
        userReference(preferences.getEmail())
                .child(Constants.INCOMING_REQUEST_FROM_KEY)
                .setValue("")
    }

    /**
     * Clears the accepted requests.
     */
    fun clearAcceptedRequests() {
        userReference(preferences.getEmail())
                .child(Constants.ACCEPTED_REQUEST_FROM_KEY)
                .setValue("")
    }

    /**
     * Sends a move.
     *
     * @param id the id of the game button
     */
    fun sendMove(id: String) {
        gameReference()
                .child(Constants.MOVES_KEY)
                .child(id)
                .setValue(preferences.getEmail())
    }

    /**
     * Resets the game.
     */
    fun resetGame() {
        gameReference().setValue("")
    }

    /**
     * Starts listening incoming requests.
     */
    fun listenIncomingRequests() {
        Log.i(TAG, "Initializing the INCOMING REQUESTS LISTENER")
        removeIncomingRequestsListeners()
        val reference = userReference(preferences.getEmail())
        val listener = Listener()
        listener.callback {
            val data = it as HashMap<*, *>
            if (data.contains(Constants.INCOMING_REQUEST_FROM_KEY)) {
                val requests = data[Constants.INCOMING_REQUEST_FROM_KEY] as HashMap<*, *>
                for (key in requests.keys) {
                    Log.i(TAG, "Incoming request from: " + requests[key])
                    firebaseListener?.onIncomingRequests(requests[key] as String)
                    break
                }
            }
        }
        reference.addValueEventListener(listener)
        incomingRequestsListeners.put(reference, listener)
    }

    /**
     * Starts listening accepted requests.
     */
    fun listenAcceptedRequests() {
        Log.i(TAG, "Initializing the ACCEPT REQUESTS LISTENER")
        removeAcceptedRequestsListeners()
        val reference = userReference(preferences.getEmail())
        val listener = Listener()
        listener.callback {
            val data = it as HashMap<*, *>
            if (data.contains(Constants.ACCEPTED_REQUEST_FROM_KEY)) {
                val requests = data[Constants.ACCEPTED_REQUEST_FROM_KEY] as HashMap<*, *>
                for (key in requests.keys) {
                    Log.i(TAG, "Accepted request from: " + requests[key])
                    firebaseListener?.onAcceptedRequests(requests[key] as String)
                    break
                }
            }
        }
        reference.addValueEventListener(listener)
        acceptedRequestsListeners.put(reference, listener)
    }

    /**
     * Starts listening the game.
     */
    fun listenGame() {
        Log.i(TAG, "Initializing the GAME LISTENER")
        removeGameListeners()
        val reference = gameReference().child(Constants.MOVES_KEY)
        val listener = Listener()
        listener.callback {
            Log.i(TAG, "Game event")
            firebaseListener?.onGameEvent(it as HashMap<*, *>)
        }
        reference.addValueEventListener(listener)
        gameListeners.put(reference, listener)
    }

    fun remove() {
        removeIncomingRequestsListeners()
        removeAcceptedRequestsListeners()
        removeGameListeners()
    }

    private fun removeIncomingRequestsListeners() {
        for ((key, value) in incomingRequestsListeners) {
            key.removeEventListener(value)
            incomingRequestsListeners.remove(key)
        }
    }

    private fun removeAcceptedRequestsListeners() {
        for ((key, value) in acceptedRequestsListeners) {
            key.removeEventListener(value)
            acceptedRequestsListeners.remove(key)
        }
    }

    private fun removeGameListeners() {
        for ((key, value) in gameListeners) {
            key.removeEventListener(value)
            gameListeners.remove(key)
        }
    }

    private fun isPlaying(): Boolean {
        return preferences.isPlaying()
    }

    private fun userReference(userEmail: String?): DatabaseReference {
        return firebaseDatabase.reference
                .child(Constants.USERS_KEY)
                .child(getUserFromEmailForFirebase(userEmail))
    }

    private fun gameReference(): DatabaseReference {
        return firebaseDatabase.reference
                .child(Constants.GAME_SESSIONS_KEY)
                .child(preferences.getSessionId())
    }

    private class Listener : ValueEventListener {

        private var func: (Any) -> Unit = {}

        fun callback(func: (Any) -> Unit) {
            this.func = func
        }

        override fun onDataChange(dataSnapshot: DataSnapshot?) {
            try {
                val data = dataSnapshot?.value as HashMap<*, *>
                func(data)
            } catch (ex: Exception) {
                func(HashMap<String, String>())
            }
        }

        override fun onCancelled(databaseError: DatabaseError?) {
            Log.e(TAG, "Error while listening to the database")
        }
    }
}