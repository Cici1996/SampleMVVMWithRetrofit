package com.example.samplemvvmwithretrofit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.samplemvvmwithretrofit.R

class SplashActivity : AppCompatActivity() {
    private val TIME_OUT:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        loadSplashScreen();
    }

    private fun loadSplashScreen(){
        Handler().postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        },TIME_OUT)
    }
}