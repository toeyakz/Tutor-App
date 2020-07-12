package com.example.tutorchinese.ui.view.payment

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.controler.Utils
import com.example.tutorchinese.ui.view.main.MainActivity
import com.squareup.picasso.Picasso


class PaymentInformationFragment : Fragment() {

    //text view
    private lateinit var tvCourseName: TextView
    private lateinit var tvCoursePrice: TextView
    private lateinit var tvBankName: TextView
    private lateinit var tvBankNumber: TextView
    private lateinit var tvBankAccountName: TextView
    private lateinit var tvCopy: TextView

    //image view
    private lateinit var imageView4: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_payment_information, container, false)
        manageToolbar()
        initView(root)
        return root
    }

    private fun initView(root: View) {
        tvCourseName = root.findViewById(R.id.tvCourseName)
        tvCoursePrice = root.findViewById(R.id.tvCoursePrice)
        tvBankName = root.findViewById(R.id.tvBankName)
        tvBankNumber = root.findViewById(R.id.tvBankNumber)
        tvBankAccountName = root.findViewById(R.id.tvBankAccountName)
        imageView4 = root.findViewById(R.id.imageView4)
        tvCopy = root.findViewById(R.id.tvCopy)

        val bundle = arguments
        if (bundle != null) {
            tvCourseName.text = bundle.getString("Cr_name")
            tvBankName.text = bundle.getString("bank_name")
            tvCoursePrice.text = bundle.getString("Cr_price")
            tvBankNumber.text = bundle.getString("bank_number")
            tvBankAccountName.text = "ชื่อบัญชี: "+bundle.getString("bank_name_account")

            Picasso.get().load(Utils.host + "/tutor/img/imageqrcode/" + bundle.getString("bank_qr")).into(imageView4)

            //courseId = bundle.getString("Cr_id").toString()
        }

        tvCopy.setOnClickListener {
            val clipboard: ClipboardManager = activity!!.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", tvBankNumber.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(activity, "คัดลอกแล้ว", Toast.LENGTH_SHORT).show()
        }


    }

    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        tvTitle.text = "ข้อมูลการชำระเงิน"
        back.visibility = View.VISIBLE

        back.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
    }


}