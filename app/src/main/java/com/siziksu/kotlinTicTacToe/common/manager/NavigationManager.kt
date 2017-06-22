package com.siziksu.kotlinTicTacToe.common.manager

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log

class NavigationManager {

    companion object {
        private const val TAG = "NavigationManager"
    }

    private var action: String? = null
    private var uri: Uri? = null
    private var clearBackStack: Boolean = false
    private var finishingCurrent: Boolean = false
    private var bundle: Bundle? = null

    fun goTo(activity: Activity?, destination: Class<*>?) {
        if (activity == null || destination == null) {
            Log.e(TAG, "No valid activity or destination class")
            return
        }
        val intent: Intent = Intent(activity, destination)
        go(activity, intent)
    }

    fun clearBackStack(): NavigationManager {
        this.clearBackStack = true
        return this
    }

    fun finishingCurrent(): NavigationManager {
        this.finishingCurrent = true
        return this
    }

    fun putExtras(bundle: Bundle): NavigationManager {
        this.bundle = bundle
        return this
    }

    fun setAction(action: String): NavigationManager {
        this.action = action
        return this
    }

    fun setData(uri: Uri): NavigationManager {
        this.uri = uri
        return this
    }

    private fun go(activity: Activity, intent: Intent?) {
        if (intent == null) {
            return
        }
        if (action != null) {
            intent.action = action
            action = null
        }
        if (uri != null) {
            intent.data = uri
            uri = null
        }
        if (bundle != null) {
            intent.putExtras(bundle)
            bundle = null
        }
        if (clearBackStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            clearBackStack = false
        }
        activity.startActivity(intent)
        if (finishingCurrent) {
            activity.finish()
            finishingCurrent = false
        }
    }
}