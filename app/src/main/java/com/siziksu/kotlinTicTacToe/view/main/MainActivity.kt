package com.siziksu.kotlinTicTacToe.view.main

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.siziksu.kotlinTicTacToe.common.utils.closeSoftKeyboard
import com.siziksu.kotlinTicTacToe.R
import com.siziksu.kotlinTicTacToe.common.Constants
import com.siziksu.kotlinTicTacToe.presenter.main.IMainView
import com.siziksu.kotlinTicTacToe.presenter.main.IMainPresenter
import com.siziksu.kotlinTicTacToe.presenter.main.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), IMainView {

    companion object {
        private const val TAG: String = "MainActivity"
    }

    private val presenter: IMainPresenter by lazy { MainPresenter(this) }
    private val count: Int by lazy { gameButtonsContainer.childCount }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manageExtrasAndStartListening()
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter()
        filter.addAction(Constants.BROADCAST_ACTION_LISTENER)
        registerReceiver(notificationReceiver, filter)
    }

    override fun onResume() {
        super.onResume()
        setRequestGameBlockVisibility(presenter.isPlaying())
        presenter.onResume()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
        unregisterReceiver(notificationReceiver)
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun onAcceptedRequest(opponentEmail: String) {
        opponentInfo.text = opponentEmail
        setRequestGameBlockVisibility(true)
    }

    override fun onGameListening(opponentEmail: String) {
        opponentInfo.text = opponentEmail
    }

    override fun onGameFinished() {
        resetGameButton.visibility = View.VISIBLE
        enableGameButtons(false)
        setTurnVisually(0)
    }

    override fun setTurnVisually(player: Int) {
        playerInfo.isSelected = player == Constants.FIRST_PLAYER
        opponentInfo.isSelected = player == Constants.SECOND_PLAYER
    }

    override fun getGameButton(id: Int): ImageView {
        when (id) {
            1 -> return gameButton1
            2 -> return gameButton2
            3 -> return gameButton3
            4 -> return gameButton4
            5 -> return gameButton5
            6 -> return gameButton6
            7 -> return gameButton7
            8 -> return gameButton8
            9 -> return gameButton9
            else -> {
                return gameButton1
            }
        }
    }

    override fun resetGame() {
        opponentInfo.text = ""
        resetGameButton.visibility = View.GONE
        setRequestGameBlockVisibility(false)
        resetBoard()
        setTurnVisually(0)
    }

    override fun resetBoard() {
        val drawable: Drawable = ContextCompat.getDrawable(this, android.R.color.transparent)
        (0..count - 1)
                .filter { gameButtonsContainer.getChildAt(it) is ImageView }
                .forEach {
                    gameButtonsContainer.getChildAt(it).isEnabled = true
                    (gameButtonsContainer.getChildAt(it) as ImageView).setImageDrawable(drawable)
                }
    }

    fun buttonClick(view: View) {
        when (view.id) {
            R.id.resetGameButton -> {
                presenter.resetGame()
            }
            R.id.sendRequestButton -> presenter.sendRequest(sendRequestEmail.text.toString())
            else -> {
            }
        }
        closeSoftKeyboardAndRequestFocus(view)
    }

    fun gameButtonClick(view: View) {
        presenter.sendMove(getGameButtonId(view))
        closeSoftKeyboardAndRequestFocus(view)
    }

    private fun getGameButtonId(view: View): Int {
        val id: Int
        when (view.id) {
            R.id.gameButton1 -> id = 1
            R.id.gameButton2 -> id = 2
            R.id.gameButton3 -> id = 3
            R.id.gameButton4 -> id = 4
            R.id.gameButton5 -> id = 5
            R.id.gameButton6 -> id = 6
            R.id.gameButton7 -> id = 7
            R.id.gameButton8 -> id = 8
            R.id.gameButton9 -> id = 9
            else -> id = 0
        }
        return id
    }

    private fun manageExtrasAndStartListening() {
        if (intent.extras != null) {
            if (intent.extras.containsKey(Constants.EMAIL_KEY)) {
                val email = intent.extras.getString(Constants.EMAIL_KEY)
                playerInfo.text = email
                presenter.setEmail(email)
            }
        }
        presenter.startFullListening()
    }

    private fun enableGameButtons(value: Boolean) {
        (0..count - 1)
                .filter { gameButtonsContainer.getChildAt(it) is ImageView }
                .forEach { gameButtonsContainer.getChildAt(it).isEnabled = value }
    }

    private fun closeSoftKeyboardAndRequestFocus(view: View) {
        closeSoftKeyboard(this, view)
        view.requestFocusFromTouch()
    }

    private fun setRequestGameBlockVisibility(isPlaying: Boolean) {
        sendRequestEmail.visibility = if (isPlaying) View.GONE else View.VISIBLE
        sendRequestButton.visibility = if (isPlaying) View.GONE else View.VISIBLE
    }

    private val notificationReceiver = object : BroadcastReceiver() {

        private val TAG: String = "NotificationReceiver"

        override fun onReceive(context: Context, intent: Intent) {
            if (presenter.isPlaying() || intent.extras == null || !intent.extras.containsKey(Constants.OPPONENT_EMAIL_KEY)) return
            Log.i(TAG, "Accepted match, opponent's email: " + intent.getStringExtra(Constants.OPPONENT_EMAIL_KEY))
            val opponentEmail = intent.extras.getString(Constants.OPPONENT_EMAIL_KEY)
            presenter.acceptRequest(opponentEmail)
            opponentInfo.text = opponentEmail
            sendRequestEmail.setText(opponentEmail)
            setRequestGameBlockVisibility(true)
        }
    }
}