package com.example.tutorchinese.ui.controler

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.media.Image
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.data.entities.Course
import com.example.tutorchinese.ui.data.response.CartByTutor
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_alert_dialog.view.*
import kotlinx.android.synthetic.main.custom_alert_dialog.view.tvTitle
import kotlinx.android.synthetic.main.custom_alert_dialog02.view.*
import kotlinx.android.synthetic.main.custom_alert_dialog02.view.btnCancel
import kotlinx.android.synthetic.main.custom_alert_dialog02.view.btnConfirm
import kotlinx.android.synthetic.main.dialog_detail_check_order.view.*
import kotlinx.android.synthetic.main.dialog_detail_course.view.*
import kotlinx.android.synthetic.main.dialog_detail_course.view.tvCourseName
import kotlinx.android.synthetic.main.dialog_edit_course.view.*
import kotlinx.android.synthetic.main.dialog_image_view.view.*
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

    fun dialogEditContent(
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

        //setDetail
        mDialogView.textView7.text = "แก้ไขลำดับบทเรียน :"
        mDialogView.edtCourseName.hint = "ลำดับบทเรียน"
        mDialogView.tvCourseDetails.text = "แก้ไขชื่อบทเรียน :"
        mDialogView.edtCoursePrice.hint = "ชื่อบทเรียน :"

        // set title
        mDialogView.tvTitle.text = title

        //set data
        mDialogView.edtCourseName.setText(myMap2["Co_chapter_number"])
        mDialogView.edtCoursePrice.setText(myMap2["Co_name"])
        mDialogView.edtCourseDetail.setText(myMap2["Co_info"])


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
            myMap["Co_id"] = myMap2["Co_id"].toString()
            myMap["Co_chapter_number"] = mDialogView.edtCourseName.text.toString()
            myMap["Co_name"] = mDialogView.edtCoursePrice.text.toString()
            myMap["Co_info"] = mDialogView.edtCourseDetail.text.toString()
            callBack.invoke(myMap)

            alertDialog.dismiss()

        }
    }


    @SuppressLint("SetTextI18n")
    fun dialogShowImage(
        context: Context,
        src: String
    ) {

        val mDialogView =
            LayoutInflater.from(context).inflate(R.layout.dialog_image_view, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)

        val alertDialog: AlertDialog = mBuilder.create()

        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 50)
        alertDialog.window?.setBackgroundDrawable(inset)
        alertDialog.show()

        Picasso.get().load(src)
            .into(mDialogView.imgFullScreen)


    }


    @SuppressLint("SetTextI18n")
    fun dialogDetailCheckOrder(
        context: Context,
        title: String,
        cartByTutor: CartByTutor,
        // myMap2: HashMap<String, String>
        callBack: (Boolean) -> Unit
    ) {

        val mDialogView =
            LayoutInflater.from(context).inflate(R.layout.dialog_detail_check_order, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)

        val alertDialog: AlertDialog = mBuilder.create()
        alertDialog.setCancelable(false)

        // set title
        mDialogView.tvTitle.text = title

        //set data
        mDialogView.tvCourseName.text = cartByTutor.Cr_name
        mDialogView.tvNameUser.text =
            "ชื่อลูกค้า : " + cartByTutor.U_name + " " + cartByTutor.U_lastname
        mDialogView.tvBankName.text = "โอนจากธนาคาร : " + cartByTutor.O_bank
        mDialogView.tvBankNumber.text = "หมายเลขบัญชี : " + cartByTutor.O_bank_num

        mDialogView.tvPrice.text = "จำนวนเงินที่ถูกโอน : " + cartByTutor.O_price
        mDialogView.tvDateTime.text =
            "วันที่และเวลาที่โอน : " + cartByTutor.O_date + " " + cartByTutor.O_time

        //mDialogView.imgBank

        Picasso.get().load(Utils.host + "/tutor/img/imagepayment/" + cartByTutor.O_image)
            .into(mDialogView.imgBank)

        mDialogView.imgBank.setOnClickListener {
            Log.d("s5d0asd", "s5s5s5s5s")
            dialogShowImage(context, Utils.host + "/tutor/img/imagepayment/" + cartByTutor.O_image)
        }

        if (cartByTutor.O_status == "1") {
            mDialogView.btnConfirm.visibility = View.GONE
        }


        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 50)
        alertDialog.window?.setBackgroundDrawable(inset)

        alertDialog.show()

        mDialogView.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        //กดปุ่่มยืนยัน
        mDialogView.btnConfirm.setOnClickListener {
            callBack.invoke(true)
            alertDialog.dismiss()

        }
    }

    @SuppressLint("SetTextI18n")
    fun dialogDetailCourse(
        context: Context,
        title: String,
        course: Course,
        // myMap2: HashMap<String, String>
        callBack: (Boolean) -> Unit
    ) {

        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_detail_course, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)

        val alertDialog: AlertDialog = mBuilder.create()
        alertDialog.setCancelable(false)

        // set title
        mDialogView.tvTitle.text = title

        //set data
        mDialogView.tvCourseName.text = course.Cr_name
        mDialogView.tvDetails.text = "\t\t\t\t" + course.Cr_info
        mDialogView.tvpPrice.text = "ราคา : " + course.Cr_price


        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 50)
        alertDialog.window?.setBackgroundDrawable(inset)

        alertDialog.show()

        mDialogView.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        //กดปุ่่มยืนยัน
        mDialogView.btnConfirm.setOnClickListener {
            callBack.invoke(true)
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