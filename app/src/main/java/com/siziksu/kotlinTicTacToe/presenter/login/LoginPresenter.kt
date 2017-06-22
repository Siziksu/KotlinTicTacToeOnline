package com.siziksu.kotlinTicTacToe.presenter.login

import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.siziksu.kotlinTicTacToe.R
import com.siziksu.kotlinTicTacToe.common.manager.NavigationManager
import com.siziksu.kotlinTicTacToe.data.FirebaseDatabaseClient
import com.siziksu.kotlinTicTacToe.view.main.MainActivity

class LoginPresenter(private val view: ILoginView) : ILoginPresenter {

    companion object {
        private const val TAG: String = "LoginPresenter"
    }

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val firebaseDatabase by lazy { FirebaseDatabaseClient(view.getActivity()) }

    override fun onStart() {
        loadMainActivity()
    }

    override fun buttonClick(view: View) {
        when (view.id) {
            R.id.loginButton -> {
                signInIntoFirebase(view)
            }
            else -> return
        }
    }

    private fun signInIntoFirebase(view: View) {
        Log.i(TAG, "Trying to Sign in into Firebase")
        firebaseAuth?.signInWithEmailAndPassword(this.view.getEmail(), this.view.getPassword())
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        loadMainActivity()
                    } else {
                        if (it.exception != null && it.exception is FirebaseAuthInvalidUserException) {
                            Snackbar.make(view, it.exception?.message ?: view.context.getString(R.string.firebase_sign_in_fail), Snackbar.LENGTH_SHORT).show()
                            Log.e(TAG, it.exception?.message, it.exception)
                        }
                    }
                } ?: nullFirebaseAuthInstance()
    }

    private fun signUpIntoFirebase(view: View) {
        Log.i(TAG, "Trying to Sign up into Firebase")
        firebaseAuth?.createUserWithEmailAndPassword(this.view.getEmail(), this.view.getPassword())
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        loadMainActivity()
                    } else {
                        Snackbar.make(view, it.exception?.message ?: view.context.getString(R.string.firebase_sign_up_fail), Snackbar.LENGTH_SHORT).show()
                        Log.e(TAG, it.exception?.message, it.exception)
                    }
                } ?: nullFirebaseAuthInstance()
    }

    private fun nullFirebaseAuthInstance() {
        Log.e(TAG, view.getActivity().getString(R.string.firebase_auth_instance_null))
    }

    private fun loadMainActivity() {
        val currentUser = firebaseAuth?.currentUser ?: return
        NavigationManager()
                .finishingCurrent()
                .putExtras(firebaseDatabase.getUserBundle(currentUser))
                .goTo(view.getActivity(), MainActivity::class.java)
    }
}
