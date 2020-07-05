package com.example.tutorchinese.ui.controler

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.example.tutorchinese.R
import kotlinx.android.synthetic.main.activity_custom_progress_dialog.*

class CustomProgressDialog(context: Context, private val messes: String?) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_progress_dialog)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        val rotate = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = 800
        rotate.repeatCount = Animation.INFINITE
        iv_logo.startAnimation(rotate)

        if (messes != null) {
            tvProgress.text = messes
        }

    }


}
