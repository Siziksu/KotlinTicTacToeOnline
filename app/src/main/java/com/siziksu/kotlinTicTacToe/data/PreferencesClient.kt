package com.siziksu.kotlinTicTacToe.data

import android.content.Context
import android.preference.PreferenceManager
import com.siziksu.kotlinTicTacToe.common.Constants

class PreferencesClient(context: Context) {

    companion object {
        private const val TAG: String = "PreferencesClient"
    }

    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

    fun setIsPlaying(value: Boolean) {
        setBoolean(Constants.PLAYING_KEY, value)
    }

    fun isPlaying(): Boolean {
        return getBoolean(Constants.PLAYING_KEY, false)
    }

    fun setSessionId(sessionId: String) {
        return setString(Constants.SESSION_ID_KEY, sessionId)
    }

    fun getSessionId(): String {
        return getString(Constants.SESSION_ID_KEY, "")
    }

    fun setEmail(email: String?) {
        setString(Constants.EMAIL_KEY, email ?: "")
    }

    fun getEmail(): String {
        return getString(Constants.EMAIL_KEY, "")
    }

    fun setOpponentEmail(email: String?) {
        setString(Constants.OPPONENT_EMAIL_KEY, email ?: "")
    }

    fun getOpponentEmail(): String {
        return getString(Constants.OPPONENT_EMAIL_KEY, "")
    }

    fun setPlayerNumber(value: Int) {
        setInt(Constants.PLAYER_NUMBER_KEY, value)
    }

    fun getPlayerNumber(): Int {
        return getInt(Constants.PLAYER_NUMBER_KEY, 0)
    }

    fun resetGame() {
        sharedPreferences?.edit()?.remove(Constants.PLAYING_KEY)?.apply()
        sharedPreferences?.edit()?.remove(Constants.SESSION_ID_KEY)?.apply()
        sharedPreferences?.edit()?.remove(Constants.PLAYER_NUMBER_KEY)?.apply()
        sharedPreferences?.edit()?.remove(Constants.OPPONENT_EMAIL_KEY)?.apply()
    }

    private fun setInt(key: String, value: Int) {
        sharedPreferences?.edit()?.putInt(key, value)?.apply()
    }

    private fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences?.getInt(key, defaultValue) ?: 0
    }

    private fun setString(key: String, value: String) {
        sharedPreferences?.edit()?.putString(key, value)?.apply()
    }

    private fun getString(key: String, defaultValue: String): String {
        return sharedPreferences?.getString(key, defaultValue) ?: ""
    }

    private fun setBoolean(key: String, value: Boolean) {
        sharedPreferences?.edit()?.putBoolean(key, value)?.apply()
    }

    private fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences?.getBoolean(key, defaultValue) ?: false
    }
}