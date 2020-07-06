package com.example.tutorchinese.ui.view.tutor.content_detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.view.tutor.main.MainActivity

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
            tvContentDetails.text = bundle.getString("Cr_info")
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