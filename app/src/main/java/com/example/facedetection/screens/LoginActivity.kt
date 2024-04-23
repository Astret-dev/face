package com.example.facedetection.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Telephony
import androidx.appcompat.app.AppCompatActivity
import com.example.facedetection.OtpReciever
import com.example.facedetection.R
import com.example.facedetection.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity(R.layout.login_activity) {
    private lateinit var binding : LoginActivityBinding
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        binding = LoginActivityBinding.inflate(layoutInflater)

        with(binding){
            setOnClickListener()

            OtpReciever().setEditText(binding.editTextOtp)

            navigateToMainActivity()

        }


    }


    private fun navigateToMainActivity() {

    }



    private fun setOnClickListener() {
        TODO("Not yet implemented")
    }


}