package com.fakecorp.devslopessmack.Controller.Controller

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.fakecorp.devslopessmack.Controller.Services.AuthService
import com.fakecorp.devslopessmack.R
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        logInSpinner.visibility = View.INVISIBLE
    }

    fun logInLoginBtnClicked(view: View)
    {
        enableSpinner(true)

        val email = logInEmailText.text.toString()
        val password = logInPasswordText.text.toString()
        hideKeyboard()
        if(email.isNotEmpty() && password.isNotEmpty()) {
            AuthService.loginUser(email, password) { loginSuccess ->
                if (loginSuccess) {
                    AuthService.findUserByEmail(this) { findSuccess ->
                        if (findSuccess) {
                            enableSpinner(false)
                            finish()
                        }
                        else {
                            errorToast()
                        }
                    }
                }
                else {
                    errorToast()
                }
            }
        } else {
            Toast.makeText(this, "Please fill in both e-mail and password", Toast.LENGTH_LONG).show()
        }
    }

    fun createUserBtnClicked(view: View)
    {
        var createUserIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(createUserIntent)
        finish()
    }

    private fun errorToast(){
        Toast.makeText(this, "Something went wrong, please try again!", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    private fun enableSpinner(enable: Boolean){
        if(enable){
            logInSpinner.visibility = View.VISIBLE
        }
        else
        {
            logInSpinner.visibility = View.INVISIBLE
        }
        logInLoginBtn.isEnabled = !enable
        logInCreateUserBtn.isEnabled = !enable
    }

    fun hideKeyboard(){
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if(inputManager.isAcceptingText){
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }
}
