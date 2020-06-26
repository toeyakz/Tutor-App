package com.example.tutorchinese.ui.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.manage.PreferencesData
import com.example.tutorchinese.ui.view.login.LoginActivity
import com.example.tutorchinese.ui.view.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    private val timeOut: Long = 2000 // 2 sec
    private var user: PreferencesData.Users? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        user = PreferencesData.user(applicationContext)

        if (user?.session!!) {
            Handler().postDelayed({
                val myIntent = Intent(applicationContext, MainActivity::class.java)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent)
                finish()
            }, timeOut)
        } else {
            Handler().postDelayed({
                val myIntent = Intent(applicationContext, LoginActivity::class.java)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent)
                finish()
            }, timeOut)
        }


    }
}