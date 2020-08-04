package com.example.tutorchinese.ui.view.course.content_detail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.view.main.MainActivity


class DetailContentFragment : Fragment() {

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

    private fun initView(root: View) {

        val tvContentNumber: TextView = root.findViewById(R.id.tvContentNumber)
        val tvContentName: TextView = root.findViewById(R.id.tvContentName)
        val tvContentDetails: TextView = root.findViewById(R.id.tvContentDetails)

        val bundle = arguments
        if (bundle != null) {
            tvContentNumber.text = "บทที่ : " + bundle.getString("Co_chapter_number")
            tvContentName.text = bundle.getString("Co_name")
           // tvContentDetails.text = "\t\t\t\t\tรายละเอียด: " + bundle.getString("Cr_info")

            val text = "กดที่นี่เพื่อดูรายละเอียด"
            val ss = SpannableString(text)

            val clickableSpan1: ClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {

                    val url = bundle.getString("Cr_info")
                    if (URLUtil.isValidUrl(url)) {
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        startActivity(i)
                    }else{
                        Toast.makeText(activity, "ไม่สามารถดูรายละเอียดได้ กรุณาติดต่อติวเตอร์", Toast.LENGTH_LONG).show()
                    }


                }

                @SuppressLint("NewApi")
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = Color.RED
                    ds.isUnderlineText = true
                }
            }
            ss.setSpan(clickableSpan1, 0, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            tvContentDetails.text = ss
            tvContentDetails.movementMethod = LinkMovementMethod.getInstance();

        }
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