package com.example.tutorchinese.ui.view.beginner.splash

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.controler.PreferencesData
import com.example.tutorchinese.ui.view.beginner.login.LoginActivity
import com.example.tutorchinese.ui.view.main.MainActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.custom_alert_dialog2.view.*

class SplashScreenActivity : AppCompatActivity() {

    private val timeOut: Long = 2000 // 2 sec
    private var user: PreferencesData.Users? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        user = PreferencesData.user(applicationContext)


        try {
            setContentView(R.layout.activity_splash_screen)
            permission()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        /* if (user?.session!!) {
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
         }*/


    }

    private fun permission() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        } else {
            listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

        Dexter.withActivity(this)
            .withPermissions(permissions)
            .withListener(object : MultiplePermissionsListener {

                @SuppressLint("SetTextI18n")
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            if (user?.session!!) {
                                Handler().postDelayed({
                                    val myIntent =
                                        Intent(applicationContext, MainActivity::class.java)
                                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(myIntent)
                                    finish()
                                }, timeOut)
                            } else {
                                Handler().postDelayed({
                                    val myIntent =
                                        Intent(applicationContext, LoginActivity::class.java)
                                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(myIntent)
                                    finish()
                                }, timeOut)
                            }
                        }
                        /*else {
                            dialog()
                        }*/
                    }


                }

                @SuppressLint("SetTextI18n")
                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    //  tvSerial.text = ": unknowns"
                    token?.continuePermissionRequest()
                }

            })
            .check()
    }

    fun dialog() {
        val mDialogView = LayoutInflater.from(this@SplashScreenActivity)
            .inflate(R.layout.custom_alert_dialog2, null)
        val mBuilder = AlertDialog.Builder(this@SplashScreenActivity)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.create()
        mAlertDialog.setCancelable(false)
        // mAlertDialog.window!!.attributes.windowAnimations = R.style.DialogSlide
        mAlertDialog.show()
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 50)
        mAlertDialog.window?.setBackgroundDrawable(inset)
        // mAlertDialog.window?.setBackgroundDrawableResource(R.drawable.background_dialog)
        // set title
        mDialogView.tvTitle.text = "คำแนะนำ"
        // set messess
        mDialogView.tvMessess.text = "กรุณากดยินยอมการอนุญาตให้ครบ"


        mDialogView.btnCancel.visibility = View.GONE
        mDialogView.btnConfirm.text = "ปิด"
        mDialogView.btnConfirm.setOnClickListener {
            mAlertDialog.dismiss()
            finish()
        }
    }
}