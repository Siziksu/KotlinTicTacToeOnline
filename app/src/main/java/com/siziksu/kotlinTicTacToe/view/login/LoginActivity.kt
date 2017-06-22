package com.siziksu.kotlinTicTacToe.view.login

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.siziksu.kotlinTicTacToe.common.utils.closeSoftKeyboard
import com.siziksu.kotlinTicTacToe.R
import com.siziksu.kotlinTicTacToe.presenter.login.ILoginView
import com.siziksu.kotlinTicTacToe.presenter.login.ILoginPresenter
import com.siziksu.kotlinTicTacToe.presenter.login.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), ILoginView {

    companion object {
        private const val TAG: String = "LoginActivity"
    }

    private val presenter: ILoginPresenter by lazy { LoginPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun getEmail(): String {
        return loginEmail.text.toString()
    }

    override fun getPassword(): String {
        return loginUserPassword.text.toString()
    }

    fun loginButtonClick(view: View) {
        when (view.id) {
            R.id.loginButton -> presenter.buttonClick(view)
            else -> {
            }
        }
        closeSoftKeyboard(this, view)
        view.requestFocusFromTouch()
    }
}
