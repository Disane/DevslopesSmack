package com.fakecorp.devslopessmack.Controller.Controller

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.Toast
import com.fakecorp.devslopessmack.Controller.Services.AuthService
import com.fakecorp.devslopessmack.R
import com.fakecorp.devslopessmack.Services.UserDataService
import com.fakecorp.devslopessmack.Utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity()
{
    private var userAvatar = "profileDefault"
    private var avatarColor = "[0.5, 0.5, 0,5, 1]"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        createSpinner.visibility = View.INVISIBLE
    }

    fun generateUserAvatarClick(view:View)
    {
        val random = Random()
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)

        /*if(color == 0){
            userAvatar = "light$avatar"
        }
        else
        {
            userAvatar = "dark$avatar"
        }*/
        userAvatar = when(color)
        {
            0 ->  "light$avatar"
            else -> "dark$avatar"
        }
        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        createAvatarImageView.setImageResource(resourceId)
    }

    fun generateBtnClicked(view: View)
    {
        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        createAvatarImageView.setBackgroundColor(Color.rgb(r,g,b))

        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() / 255
        val savedB = b.toDouble() / 255

        avatarColor = "[$savedR, $savedG, $savedB, 1]"
        println(avatarColor)
    }

    fun createUserClicked(view: View)
    {
        enableSpinner(true)

        val userName = createUserNameText.text.toString()
        val email = createEmailText.text.toString()
        val password = createPasswordText.text.toString()

        if(userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            AuthService.registerUser(this, email, password) { registerSuccess ->
                if (registerSuccess) {
                    AuthService.loginUser(this, email, password) { loginSuccess ->
                        if (loginSuccess) {
                            AuthService.createUser(this, userName, email, userAvatar, avatarColor) { createSuccess ->
                                if (createSuccess) {
                                    // notify Activities that a user was created
                                    val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                    // show load screen spinner
                                    enableSpinner(false)
                                    // close CreateUserActivity
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
                }
                else {
                    errorToast()
                }
            }
        }else{
            Toast.makeText(this, "Make sure user name, email and password are filled in before continuing!", Toast.LENGTH_LONG).show()
            enableSpinner(false)
        }
    }

    private fun errorToast(){
        Toast.makeText(this, "Something went wrong, please try again!", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    private fun enableSpinner(enable: Boolean){
        if(enable){
            createSpinner.visibility = View.VISIBLE
        }
        else
        {
            createSpinner.visibility = View.INVISIBLE
        }
        createUserBtn.isEnabled = !enable
        createAvatarImageView.isEnabled = !enable
        backgroundColorBtn.isEnabled = !enable
    }
}
