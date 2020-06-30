package com.example.tutorchinese.ui.view.tutor.course_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tutorchinese.R

class DetailCourseFragment : Fragment() {
    private lateinit var mDetailCoursePresenter: DetailCoursePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detail_course, container, false)
        initView(root)
        return root
    }

    private fun initView(root: View) {
        mDetailCoursePresenter = DetailCoursePresenter()
    }

}