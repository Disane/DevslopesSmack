package com.fakecorp.devslopessmack.Controller.Controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fakecorp.devslopessmack.Controller.Services.AuthService
import com.fakecorp.devslopessmack.R
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
    }

    fun logInLoginBtnClicked(view: View)
    {
        val email = logInEmailText.text.toString()
        val password = logInPasswordText.text.toString()

        AuthService.loginUser(this, email, password){ loginSuccess ->
            if(loginSuccess)
            {
                AuthService.findUserByEmail(this){findSuccess ->
                    if(findSuccess)
                    {
                        finish()
                    }
                }
            }
        }
    }

    fun createUserBtnClicked(view: View)
    {
        var createUserIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(createUserIntent)
        finish()
    }
}
