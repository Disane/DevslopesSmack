package com.fakecorp.devslopessmack.Services

import android.graphics.Color
import com.fakecorp.devslopessmack.Controller.App
import com.fakecorp.devslopessmack.Controller.Services.AuthService
import java.util.*

object UserDataService {
    var id = ""
    var avatarColor = ""
    var avatarName = ""
    var email = ""
    var name = ""

    fun logout()
    {
        id = ""
        avatarColor = ""
        avatarName = ""
        email = ""
        name = ""
        App.prefs.authToken = ""
        App.prefs.userEmail = ""
        App.prefs.isLoggedIn = false
    }

    fun returnAvatarColor(components: String) : Int
    {
        // Sample
        // "avatarColor": "[0.09019607843137255, 0.33725490196078434, 0.7137254901960784, 1]",
        // After clean up
        // 0.09019607843137255 0.33725490196078434 0.7137254901960784 1
        val strippedColor = components
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")
        var r = 0
        var g = 0
        var b = 0

        val scanner = Scanner(strippedColor)
        if(scanner.hasNext())
        {
            r = (scanner.nextDouble()*255).toInt()
            g = (scanner.nextDouble()*255).toInt()
            b = (scanner.nextDouble()*255).toInt()
        }

        return Color.rgb(r,g,b)
    }
}