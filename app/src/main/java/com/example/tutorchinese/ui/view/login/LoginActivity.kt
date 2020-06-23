package com.example.tutorchinese.ui.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.view.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mLoginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mLoginPresenter = LoginPresenter()
        actionOnClick()
    }

    private fun actionOnClick() {
        btnRegister.setOnClickListener {
            val myIntent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(myIntent)
        }
    }
}