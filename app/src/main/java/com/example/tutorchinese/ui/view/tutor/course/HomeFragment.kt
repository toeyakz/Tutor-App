package com.example.tutorchinese.ui.view.tutor.course

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.data.entities.Course
import com.example.tutorchinese.ui.data.response.CourseResponse
import com.example.tutorchinese.ui.manage.PreferencesData
import com.example.tutorchinese.ui.view.adpater.CourseAdapter
import com.example.tutorchinese.ui.view.tutor.main.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var supportFragmentManager: FragmentManager? = null
    private var user: PreferencesData.Users? = null
    private lateinit var mHomePresenter: HomePresenter
    private lateinit var rvCourse: RecyclerView
    private lateinit var mCourseAdapter: CourseAdapter

    //layout
    private lateinit var layoutNetworkError: ConstraintLayout
    private lateinit var swipe: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        initView(root)
        return root
    }

    override fun onResume() {
        super.onResume()
        showCourse()
    }

    @SuppressLint("RestrictedApi")
    private fun initView(root: View) {
        manageToolbar()
        mHomePresenter = HomePresenter()
        supportFragmentManager = activity!!.supportFragmentManager
        rvCourse = root.findViewById(R.id.rvCourse)
        layoutNetworkError = root.findViewById(R.id.layoutNetworkError)
        val btnRefresh: Button = root.findViewById(R.id.btnRefresh)
        val swipe: SwipeRefreshLayout = root.findViewById(R.id.swipe)
        val btnAddCourse: FloatingActionButton = root.findViewById(R.id.btnAddCourse)

        btnRefresh.setOnClickListener(this)

        // เช็คสิทธ์ว่า user ไหนจะโชว์อะไรในหน้า home
        user = PreferencesData.user(activity!!)
        when (user?.type) {
            "user" -> {
                btnAddCourse.visibility = View.GONE
            }
            "tutor" -> {
                btnAddCourse.visibility = View.VISIBLE
            }
            else -> {
                btnAddCourse.visibility = View.GONE
            }
        }



        swipe.setOnRefreshListener {
            showCourse()
            swipe.isRefreshing = false
        }

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
                    layoutNetworkError.visibility = View.GONE
                    rvCourse.visibility = View.VISIBLE
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
                rvCourse.visibility = View.GONE
                layoutNetworkError.visibility = View.VISIBLE
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

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.btnRefresh -> {
                showCourse()
                Toast.makeText(activity, "อิอิ", Toast.LENGTH_SHORT).show()
            }
        }


    }
}