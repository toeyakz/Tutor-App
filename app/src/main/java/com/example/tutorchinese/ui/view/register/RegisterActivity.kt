package com.example.tutorchinese.ui.view.register

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorchinese.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var mRegisterPresenter: RegisterPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mRegisterPresenter = RegisterPresenter()
        actionOnClick()
    }

    private fun actionOnClick() {


        btnRegister2.setOnClickListener {
            checkIsEmpty()
            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()
            val name = edtName.text.toString()
            val lastName = edtLastname.text.toString()
            val email = edtEmail.text.toString()
            val birthDay = editBirthday.text.toString()
            val tel = edtTel.text.toString()

            mRegisterPresenter.sendDataToServer(
                this,
                username,
                password,
                name,
                lastName,
                email,
                birthDay,
                tel
            ) { b, t ->
                val ad: AlertDialog.Builder = AlertDialog.Builder(this)
                ad.setTitle("Error! ")
                ad.setIcon(android.R.drawable.btn_star_big_on)
                ad.setPositiveButton("Close", null)
                if (b) {
                    ad.setMessage(t)
                    ad.show()
                } else {
                    ad.setMessage(t)
                    ad.show()
                }
            }
        }
    }

    private fun checkIsEmpty() {
        // Dialog
        val ad: AlertDialog.Builder = AlertDialog.Builder(this)
        ad.setTitle("Error! ")
        ad.setIcon(android.R.drawable.btn_star_big_on)
        ad.setPositiveButton("Close", null)

        // Check Username
        if (edtUsername.text.isEmpty()) {
            ad.setMessage("Please input Username! ")
            ad.show()
            edtUsername.requestFocus()
            return
        }
        // Check Password
        if (edtPassword.text.isEmpty()) {
            ad.setMessage("Please input Password! ")
            ad.show()
            edtPassword.requestFocus()
            return
        }
        // Check Name
        if (edtName.text.isEmpty()) {
            ad.setMessage("Please input Name! ")
            ad.show()
            edtName.requestFocus()
            return
        }

        // Check Email
        if (edtLastname.text.isEmpty()) {
            ad.setMessage("Please input Last name! ")
            ad.show()
            edtLastname.requestFocus()
            return
        }

        // Check Email
        if (edtEmail.text.isEmpty()) {
            ad.setMessage("Please input Email! ")
            ad.show()
            edtEmail.requestFocus()
            return
        }

        // Check Tel
        if (edtTel.text.isEmpty()) {
            ad.setMessage("Please input !Tel ")
            ad.show()
            edtTel.requestFocus()
            return
        }
    }

}