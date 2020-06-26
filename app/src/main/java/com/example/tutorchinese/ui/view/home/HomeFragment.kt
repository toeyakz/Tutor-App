package com.example.tutorchinese.ui.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.data.response.CourseResponse
import com.example.tutorchinese.ui.manage.PreferencesData
import com.example.tutorchinese.ui.view.main.MainActivity

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var supportFragmentManager: FragmentManager? = null
    private var user: PreferencesData.Users? = null
    private lateinit var mHomePresenter: HomePresenter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        initView()
       /* when (val type = user?.type) {
            "user" -> {
                val username = user?.U_username
                textView.text = "ชื่อผู้ใช้: $username"
                textView2.text = "ประเภทของผู้ใช้: $type"
                textView3.text = "ล็อคอินสำเร็จ!"
            }
            "tutor" -> {
                val username = user?.T_username
                textView.text = "ชื่อผู้ใช้: $username"
                textView2.text = "ประเภทของผู้ใช้: $type"
                textView3.text = "ล็อคอินสำเร็จ!"
            }
            else -> {
                val username = user?.admin_username
                textView.text = "ชื่อผู้ใช้: $username"
                textView2.text = "ประเภทของผู้ใช้: $type"
                textView3.text = "ล็อคอินสำเร็จ!"
            }
        }*/

        return root
    }

    private fun initView() {
        manageToolbar()
        mHomePresenter = HomePresenter()
        supportFragmentManager = activity!!.supportFragmentManager

        showCourse()
    }


    private fun showCourse() {
        user = PreferencesData.user(activity!!)
        val id_: Int = when (user?.type) {
            "user" -> {
                user?.U_id!!
            }
            "tutor" -> {
                user?.T_id!!
            }
            else -> {
                user?.admin_id!!
            }
        }
        mHomePresenter.courseData(id_, object : HomePresenter.Response {
            override fun value(c: CourseResponse?) {
                if (c!!.isSuccessful) {
                    for (i in c.data!!) {
                        Log.d("8s1as96da", i.Cr_name)
                    }
                } else {
                    // กำหนด ui ให้โชว์หน้าว่าง
                }
            }

            override fun error(c: String?) {
                Log.d("8s1as96da", c)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        tvTitle.text = "Course"
        back.visibility = View.GONE
    }
}