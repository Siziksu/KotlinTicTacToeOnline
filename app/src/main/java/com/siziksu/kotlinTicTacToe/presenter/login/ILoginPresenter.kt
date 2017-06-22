package com.siziksu.kotlinTicTacToe.presenter.login

import android.view.View

interface ILoginPresenter {

    /**
     * On activity start.
     */
    fun onStart()

    /**
     * On button click.
     */
    fun buttonClick(view: View)
}
