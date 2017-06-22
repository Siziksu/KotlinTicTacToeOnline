package com.siziksu.kotlinTicTacToe.common.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Function to close the soft keyboard.
 *
 * @param context the context
 * @param view the anchor view
 */
fun closeSoftKeyboard(context: Context, view: View?) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}
