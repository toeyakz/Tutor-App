package com.example.tutorchinese.ui.view.course.content_detail

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.controler.Utils
import com.example.tutorchinese.ui.view.main.MainActivity
import com.koushikdutta.ion.Ion
import java.io.File


@Suppress(
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION",
    "DEPRECATED_IDENTITY_EQUALS"
)
class DetailContentFragment : Fragment() {

    var mProgressDialog: ProgressDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detail_content, container, false)
        initView(root)
        return root
    }

    override fun onResume() {
        super.onResume()
        manageToolbar()
    }

    @SuppressLint("SetTextI18n")
    private fun initView(root: View) {

        val tvContentNumber: TextView = root.findViewById(R.id.tvContentNumber)
        val tvContentName: TextView = root.findViewById(R.id.tvContentName)
        val tvContentDetails: TextView = root.findViewById(R.id.tvContentDetails)
        val tvContentLink: TextView = root.findViewById(R.id.tvContentLink)
        val tvContentFile: TextView = root.findViewById(R.id.tvContentFile)

        val bundle = arguments
        if (bundle != null) {
            tvContentNumber.text = "บทที่ : " + bundle.getString("Co_chapter_number")
            tvContentName.text = bundle.getString("Co_name")
            tvContentDetails.text = "\t\t\t\tรายละเอียด: " + bundle.getString("Co_info")

            intentLink(bundle.getString("Co_link").toString(), tvContentLink)
            downloadFileFromUrl(
                Utils.host + "/tutor/file_upload/" + bundle.getString("Co_file").toString(),
                tvContentFile
            )

            if(bundle.getString("Co_info") == ""){
                tvContentDetails.visibility = View.GONE
            }
            if(bundle.getString("Co_link") == ""){
                tvContentLink.text = "ไม่มี"
            }
            if(bundle.getString("Co_file") == ""){
                tvContentFile.text = "ไม่มี"
            }


        }
    }


    private fun downloadFileFromUrl(url: String, textView: TextView) {

        val surname: String = url.substring(url.lastIndexOf("/") + 1)

        var mWakeLock: WakeLock? = null
        mProgressDialog = ProgressDialog(activity)
        mProgressDialog!!.setMessage("A message")
        mProgressDialog!!.isIndeterminate = true
        mProgressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        mProgressDialog!!.setCancelable(true)

        val text = "ดาวน์โหลดไฟล์"
        val ss = SpannableString(surname)

        val clickableSpan1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {

                val pm: PowerManager =
                    activity!!.getSystemService(Context.POWER_SERVICE) as PowerManager
                mWakeLock = pm.newWakeLock(
                    PowerManager.PARTIAL_WAKE_LOCK,
                    javaClass.name
                )



                Ion.with(activity)
                    .load(url)
                    // have a ProgressBar get updated automatically with the percent
                    .progressDialog(mProgressDialog) // can also use a custom callback
                    .progressHandler { downloaded, total ->
                        mWakeLock!!.acquire()
                        mProgressDialog!!.show()
                    }
                    .progress { downloaded, total ->

                        mProgressDialog?.isIndeterminate = false
                        mProgressDialog?.max = total.toInt()
                        mProgressDialog?.progress = downloaded.toInt()
                    }
                    .write(File("/sdcard/download/$surname"))
                    .setCallback { e, file ->
                        mWakeLock?.release()
                        mProgressDialog?.dismiss()
                        Toast.makeText(activity, "ดาวน์โหลดสำเร็จ", Toast.LENGTH_SHORT).show()
                    }

            }

            @SuppressLint("NewApi")
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.RED
                ds.isUnderlineText = true
            }
        }

        ss.setSpan(clickableSpan1, 0, surname.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
    }


    private fun intentLink(url: String, textView: TextView) {
        val text = "กดที่นี่เพื่อดูรายละเอียด"
        val ss = SpannableString(text)

        val clickableSpan1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {

                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)


            }

            @SuppressLint("NewApi")
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.RED
                ds.isUnderlineText = true
            }
        }
        ss.setSpan(clickableSpan1, 0, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
    }


    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        tvTitle.text = "รายละเอียดบทเรียนบทเรียน"
        back.visibility = View.VISIBLE

        back.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
    }


}