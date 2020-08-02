package com.example.tutorchinese.ui.view.check_order

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.view.main.MainActivity


class DetailCheckOrderFragment : Fragment() {


    override fun onResume() {
        super.onResume()
        manageToolbar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root =  inflater.inflate(R.layout.fragment_detail_check_order, container, false)

        return root
    }

    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        tvTitle.text = "รายละเอียดการซื้อ"
        back.visibility = View.VISIBLE
        (activity as MainActivity?)!!.updateNavigationBarState(R.id.navigation_home)

        back.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
    }


}