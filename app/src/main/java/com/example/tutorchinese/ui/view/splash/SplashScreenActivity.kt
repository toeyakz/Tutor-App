package com.example.tutorchinese.ui.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.view.login.LoginActivity

class SplashScreenActivity : AppCompatActivity() {

    private val timeOut: Long = 2000 // 2 sec
    private val MULTIPLE_PERMISSIONS = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val myIntent = Intent(applicationContext, LoginActivity::class.java)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myIntent)
            finish()
        }, timeOut)
    }
}