package com.fakecorp.devslopessmack.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fakecorp.devslopessmack.Model.Message
import com.fakecorp.devslopessmack.R
import com.fakecorp.devslopessmack.Services.UserDataService
import java.sql.Time
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(val context: Context, val messages: ArrayList<Message>): RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMessage(context, messages[position])
    }

    override fun getItemCount(): Int
    {
        return messages.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.message_list_view, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView)
    {
        private val userImage =itemView?.findViewById<ImageView>(R.id.messageUserImage)
        private val timeStamp = itemView?.findViewById<TextView>(R.id.timeStampLbl)
        private val userName = itemView?.findViewById<TextView>(R.id.messageUserNameLbl)
        private val messageBody = itemView?.findViewById<TextView>(R.id.messageBodyLbl)

        fun bindMessage(context: Context, message: Message) {
            val resourceId = context.resources.getIdentifier(message.userAvatar, "drawable", context.packageName)
            userImage?.setImageResource(resourceId)
            userImage?.setBackgroundColor(UserDataService.returnAvatarColor(message.userAvatarColor))
            userName?.text = message.userName
            timeStamp?.text = returnDateString(message.timeStamp)
            messageBody?.text = message.message
        }

        fun returnDateString(isoString: String) : String{
            // 2017-09-11T01:16:13.858Z

            // Monday 01:16 AM
            val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
            isoFormatter.timeZone = TimeZone.getTimeZone("UTC")
            var convertedDate = Date()
            try {
                convertedDate = isoFormatter.parse(isoString)
            }
            catch(e: ParseException) {
                Log.d("PARSE", "Cannot parse date")
            }

            val outDateString = SimpleDateFormat("E, h: mm a")
            return outDateString.format(convertedDate)
        }
    }
}