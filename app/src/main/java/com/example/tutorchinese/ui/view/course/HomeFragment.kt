package com.example.tutorchinese.ui.view.course

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.data.entities.Course
import com.example.tutorchinese.ui.data.response.CourseResponse
import com.example.tutorchinese.ui.manage.PreferencesData
import com.example.tutorchinese.ui.view.adpater.CourseAdapter
import com.example.tutorchinese.ui.view.main.MainActivity
import java.util.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var supportFragmentManager: FragmentManager? = null
    private var user: PreferencesData.Users? = null
    private lateinit var mHomePresenter: HomePresenter
    private lateinit var rvCourse: RecyclerView
    private lateinit var mCourseAdapter: CourseAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        initView(root)
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

    private fun initView(root: View) {
        manageToolbar()
        mHomePresenter = HomePresenter()
        supportFragmentManager = activity!!.supportFragmentManager
        rvCourse = root.findViewById(R.id.rvCourse)


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
                    mCourseAdapter = CourseAdapter(activity!!, c.data as ArrayList<Course>)
                    rvCourse.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = mCourseAdapter
                        mCourseAdapter.notifyDataSetChanged()
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