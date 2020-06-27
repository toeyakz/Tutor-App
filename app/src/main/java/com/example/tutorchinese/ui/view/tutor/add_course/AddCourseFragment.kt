package com.example.tutorchinese.ui.view.tutor.add_course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tutorchinese.R

class AddCourseFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_course, container, false)
        initView()
        return root
    }

    private fun initView() {

    }

    override fun onClick(p0: View?) {

    }


}