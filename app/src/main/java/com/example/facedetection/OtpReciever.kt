package com.example.facedetection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.widget.EditText

class OtpReciever : BroadcastReceiver() {

    private lateinit var ediText :EditText
    fun setEditText(editText: EditText){
        ediText = editText
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val smsMessage = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        smsMessage?.forEachIndexed{ _, value ->
            val msgBody = value.messageBody
            val otp = msgBody.split(":")[1]
            ediText.setText(otp)
        }
    }

}