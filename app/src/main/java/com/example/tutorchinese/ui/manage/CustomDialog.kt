package com.example.tutorchinese.ui.manage

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import com.example.tutorchinese.R
import kotlinx.android.synthetic.main.custom_alert_dialog.view.*
import kotlinx.android.synthetic.main.custom_alert_dialog.view.tvTitle
import kotlinx.android.synthetic.main.custom_alert_dialog02.view.*
import kotlinx.android.synthetic.main.custom_alert_dialog02.view.btnCancel
import kotlinx.android.synthetic.main.custom_alert_dialog02.view.btnConfirm
import kotlinx.android.synthetic.main.dialog_edit_course.view.*
import java.util.HashMap

class CustomDialog {

    fun onDialog(
        context: Context,
        oneBtn: Boolean,
        title: String,
        callBack: (String) -> Unit
    ) {

        val mDialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)

        val alertDialog: AlertDialog = mBuilder.create()
        // alertDialog.window!!.attributes.windowAnimations = R.style.DialogSlide

        // set title
        mDialogView.tvTitle.text = title

        // set messess
        //   mDialogView.tvMessess.text = messess

        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 50)
        alertDialog.window?.setBackgroundDrawable(inset)

        alertDialog.show()

        mDialogView.btnDeleteCourse.setOnClickListener {
            callBack.invoke("delete")
            alertDialog.dismiss()
        }

        mDialogView.btnEditCourse.setOnClickListener {
            callBack.invoke("update")
            alertDialog.dismiss()
        }

    }

    fun dialogQuestion(
        context: Context,
        oneBtn: Boolean,
        title: String,
        messess: String,
        callBack: (Boolean, String) -> Unit
    ) {

        val mDialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog02, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)

        val alertDialog: AlertDialog = mBuilder.create()

        // set title
        mDialogView.tvTitle02.text = title

        // set messess
        mDialogView.tvMessess.text = messess

        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 50)
        alertDialog.window?.setBackgroundDrawable(inset)

        alertDialog.show()
        if (!oneBtn) {
            mDialogView.btnCancel.visibility = View.VISIBLE
        } else {
            mDialogView.btnCancel.visibility = View.GONE
        }

        mDialogView.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        //กดปุ่่มยืนยัน
        mDialogView.btnConfirm.setOnClickListener {
            callBack.invoke(true, "www")
            alertDialog.dismiss()

        }


    }


    fun dialogEditCourse(
        context: Context,
        title: String,
        myMap2: HashMap<String, String>,
        callBack: (HashMap<String, String>) -> Unit
    ) {

        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_course, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)

        val alertDialog: AlertDialog = mBuilder.create()
        alertDialog.setCancelable(false)

        // set title
        mDialogView.tvTitle.text = title

        //set data
        mDialogView.edtCourseName.setText(myMap2["Cr_name"])
        mDialogView.edtCoursePrice.setText(myMap2["Cr_price"])
        mDialogView.edtCourseDetail.setText(myMap2["Cr_info"])


        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 50)
        alertDialog.window?.setBackgroundDrawable(inset)

        mDialogView.edtCourseName
        alertDialog.show()

        mDialogView.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        //กดปุ่่มยืนยัน
        mDialogView.btnConfirm.setOnClickListener {
            val myMap = HashMap<String, String>()
            myMap["Cr_id"] = myMap2["Cr_id"].toString()
            myMap["Cr_name"] = mDialogView.edtCourseName.text.toString()
            myMap["Cr_price"] = mDialogView.edtCoursePrice.text.toString()
            myMap["Cr_info"] = mDialogView.edtCourseDetail.text.toString()
            callBack.invoke(myMap)

            alertDialog.dismiss()

        }


    }
}