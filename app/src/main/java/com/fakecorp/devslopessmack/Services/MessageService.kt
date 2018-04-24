package com.fakecorp.devslopessmack.Services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.fakecorp.devslopessmack.Controller.Services.AuthService
import com.fakecorp.devslopessmack.Model.Channel
import com.fakecorp.devslopessmack.Utilities.URL_GET_CHANNELS
import org.json.JSONException
import java.lang.reflect.Method

object MessageService {
    val TAG:String? = MessageService::class.java.name
    val channels = ArrayList<Channel>()

    fun getChannels(context: Context, complete: (Boolean) -> Unit)
    {
        val channelsRequest = object : JsonArrayRequest(Method.GET, URL_GET_CHANNELS, null, Response.Listener { response ->
            try
            {
                for(x in 0 until response.length())
                {
                    val channel = response.getJSONObject(x)
                    val name = channel.getString("name")
                    val chanDesc = channel.getString("description")
                    val channelId = channel.getString("_id")

                    val newChannel = Channel(name, chanDesc, channelId)
                    this.channels.add(newChannel)
                }
                complete(true)
            }
            catch(e: JSONException)
            {
                Log.d(TAG, "EXC: " + e.localizedMessage)
                complete(false)
            }
        }, Response.ErrorListener {error ->
            Log.d(TAG, "Could not retrieve channels!")
            complete(false)
        }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${AuthService.authToken}"
                return headers
            }
        }

        Volley.newRequestQueue(context).add(channelsRequest)
    }
}