package com.siziksu.kotlinTicTacToe.presenter.login

import android.app.Activity

interface ILoginView {

    /**
     * Gets the activity of the view.
     */
    fun getActivity(): Activity

    /**
     * Gets the email entered.
     */
    fun getEmail(): String

    /**
     * Gets the password entered.
     */
    fun getPassword(): String
}