package com.example.tutorchinese.ui.view.course.course_main

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.controler.CustomDialog
import com.example.tutorchinese.ui.controler.NetworkConnectCheck
import com.example.tutorchinese.ui.controler.PreferencesData
import com.example.tutorchinese.ui.data.entities.Course
import com.example.tutorchinese.ui.data.entities.CourseFromUser
import com.example.tutorchinese.ui.data.response.BankDetailsResponse
import com.example.tutorchinese.ui.data.response.CourseFromUserResponse
import com.example.tutorchinese.ui.data.response.CourseResponse
import com.example.tutorchinese.ui.data.response.OrdersFromUserResponse
import com.example.tutorchinese.ui.view.adpater.CourseAdapter
import com.example.tutorchinese.ui.view.adpater.CourseUserAdapter
import com.example.tutorchinese.ui.view.course.add_course.AddCourseFragment
import com.example.tutorchinese.ui.view.course.course_detail.DetailCourseFragment
import com.example.tutorchinese.ui.view.main.MainActivity
import com.example.tutorchinese.ui.view.payment.PaymentInformationFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var supportFragmentManager: FragmentManager? = null
    private var user: PreferencesData.Users? = null
    private lateinit var mHomePresenter: HomePresenter
    private lateinit var rvCourse: RecyclerView
    private lateinit var mCourseAdapter: CourseAdapter
    private lateinit var mCourseUserAdapter: CourseUserAdapter
    private var mDialog = CustomDialog()

    private val mCourseFromUserList = ArrayList<CourseFromUser>()

    //layout
    private lateinit var layoutNetworkError: ConstraintLayout
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var frameLayout2: FrameLayout

    //edit text
    private lateinit var edtSearch: EditText

    //image view
    private lateinit var clearText: ImageView

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
        manageToolbar()
        showCourse()

    }

    @SuppressLint("RestrictedApi")
    private fun initView(root: View) {
        mHomePresenter = HomePresenter()
        supportFragmentManager = fragmentManager
        rvCourse = root.findViewById(R.id.rvCourse)
        layoutNetworkError = root.findViewById(R.id.layoutNetworkError)
        val btnRefresh: Button = root.findViewById(R.id.btnRefresh)
        val swipe: SwipeRefreshLayout = root.findViewById(R.id.swipe)
        val btnAddCourse: FloatingActionButton = root.findViewById(R.id.btnAddCourse)
        frameLayout2 = root.findViewById(R.id.frameLayout2)
        edtSearch = root.findViewById(R.id.edtSearch)
        clearText = root.findViewById(R.id.clearText)


        btnRefresh.setOnClickListener(this)
        btnAddCourse.setOnClickListener(this)

        // เช็คสิทธ์ว่า user ไหนจะโชว์อะไรในหน้า home
        user = PreferencesData.user(activity!!)
        when (user?.type) {
            "user", "admin" -> {
                btnAddCourse.visibility = View.GONE
                frameLayout2.visibility = View.VISIBLE
            }
            "tutor" -> {
                btnAddCourse.visibility = View.VISIBLE
                frameLayout2.visibility = View.GONE
            }
        }
        initSearch()

        swipe.setOnRefreshListener {
            showCourse()
            swipe.isRefreshing = false
        }

    }

    private fun initSearch() {
    }

    private fun filters(toString: String, mCourseFromUserList: ArrayList<CourseFromUser>) {
        val list = ArrayList<CourseFromUser>()

        for (t in mCourseFromUserList) {
            if (t.Cr_name?.toLowerCase()!!.contains(toString.toLowerCase())) {
                list.add(t)
            }
        }
        mCourseUserAdapter.filterList(list)

    }


    private fun showCourse() {
        user = PreferencesData.user(activity!!)
        when (user?.type) {
            "user", "admin" -> {
                user?.U_id!!
                if (NetworkConnectCheck().isNetworkConnected(activity!!)) {
                    mHomePresenter.getCourseFromUser(object : HomePresenter.Response.CourseUser {
                        override fun value(c: CourseFromUserResponse?) {
                            if (c!!.isSuccessful) {
                                layoutNetworkError.visibility = View.GONE
                                rvCourse.visibility = View.VISIBLE

                                clearText.setOnClickListener {
                                    edtSearch.text.clear()
                                }

                                edtSearch.addTextChangedListener(object : TextWatcher {
                                    override fun afterTextChanged(p0: Editable?) {
                                        filters(p0.toString(), c.data as ArrayList<CourseFromUser>)

                                        if (p0.toString().isEmpty()) {
                                            clearText.visibility = View.GONE
                                        } else {
                                            clearText.visibility = View.VISIBLE
                                        }
                                    }

                                    override fun beforeTextChanged(
                                        p0: CharSequence?,
                                        p1: Int,
                                        p2: Int,
                                        p3: Int
                                    ) {
                                    }

                                    override fun onTextChanged(
                                        p0: CharSequence?,
                                        p1: Int,
                                        p2: Int,
                                        p3: Int
                                    ) {
                                    }
                                })

                                //   mCourseFromUserList.addAll()

                                mCourseUserAdapter = CourseUserAdapter(
                                    activity!!,
                                    c.data as ArrayList<CourseFromUser>,
                                    user?.U_id.toString(),
                                    mHomePresenter
                                ) { hashMap, s ->
                                    /*                 when (s) {
                                                         "ซื้อแล้ว" -> {
                                                             Toast.makeText(activity, "ซื้อแล้วจ้า", Toast.LENGTH_SHORT).show()
                                                         }
                                                         "กำลังตรวจสอบ" -> {
                                                             Toast.makeText(activity, "กำลังตรวจสอบการสั่งซื้อกรุณารอ", Toast.LENGTH_LONG).show()
                                                         }
                                                         "ยังไม่ซื้อ" -> {*/
                                    mHomePresenter.getCourseDetailFromUser(
                                        hashMap["Cr_id"].toString(),
                                        object :
                                            HomePresenter.Response.CourseTutor {
                                            override fun value(c: CourseResponse?) {

                                                if (c!!.isSuccessful) {

                                                    val bundle = Bundle()

                                                    bundle.putString(
                                                        "T_id",
                                                        c.data!![0].T_id.toString()
                                                    )
                                                    bundle.putString(
                                                        "Cr_id",
                                                        c.data[0].Cr_id.toString()
                                                    )
                                                    bundle.putString(
                                                        "Cr_name",
                                                        c.data[0].Cr_name.toString()
                                                    )
                                                    bundle.putString(
                                                        "Cr_price",
                                                        c.data[0].Cr_price.toString()
                                                    )
                                                    bundle.putString(
                                                        "Cr_info",
                                                        c.data[0].Cr_info.toString()
                                                    )
                                                    bundle.putString(
                                                        "Cr_data_time",
                                                        c.data[0].Cr_data_time.toString()
                                                    )
                                                    bundle.putString(
                                                        "status_price",
                                                        s
                                                    )

                                                    val detailCourseFragment: DetailCourseFragment? =
                                                        activity!!.fragmentManager
                                                            .findFragmentById(R.id.fragment_add_course) as DetailCourseFragment?

                                                    if (detailCourseFragment == null) {
                                                        val newFragment = DetailCourseFragment()
                                                        newFragment.arguments = bundle
                                                        fragmentManager!!.beginTransaction()
                                                            .replace(
                                                                R.id.navigation_view,
                                                                newFragment,
                                                                ""
                                                            )
                                                            .addToBackStack(null)
                                                            .commit()
                                                    } else {
                                                        fragmentManager!!.beginTransaction()
                                                            .replace(
                                                                R.id.navigation_view,
                                                                detailCourseFragment,
                                                                ""
                                                            )
                                                            .addToBackStack(null)
                                                            .commit()
                                                    }


                                                }


                                            }

                                            override fun error(c: String?) {

                                            }
                                        })

                                }

                                rvCourse.apply {
                                    layoutManager = LinearLayoutManager(activity)
                                    adapter = mCourseUserAdapter
                                    mCourseUserAdapter.notifyDataSetChanged()
                                }


                            } else {
                                // กำหนด ui ให้โชว์หน้าว่าง
                            }
                        }

                        override fun error(c: String?) {
                            rvCourse.visibility = View.GONE
                            layoutNetworkError.visibility = View.VISIBLE
                        }
                    })
                } else {
                    rvCourse.visibility = View.GONE
                    layoutNetworkError.visibility = View.VISIBLE
                }
            }
            "tutor" -> {
                if (NetworkConnectCheck().isNetworkConnected(activity!!)) {
                    mHomePresenter.courseData(
                        user?.T_id!!,
                        object : HomePresenter.Response.CourseTutor {
                            override fun value(c: CourseResponse?) {
                                if (c!!.isSuccessful) {
                                    layoutNetworkError.visibility = View.GONE
                                    rvCourse.visibility = View.VISIBLE
                                    mCourseAdapter =
                                        CourseAdapter(
                                            activity!!,
                                            c.data as ArrayList<Course>
                                        ) { hashMap, b ->

                                            // On click
                                            if (b) {

                                                val bundle = Bundle()
                                                bundle.putString(
                                                    "Cr_id",
                                                    hashMap["Cr_id"].toString()
                                                )
                                                bundle.putString(
                                                    "Cr_name",
                                                    hashMap["Cr_name"].toString()
                                                )
                                                bundle.putString(
                                                    "Cr_price",
                                                    hashMap["Cr_price"].toString()
                                                )
                                                bundle.putString(
                                                    "Cr_info",
                                                    hashMap["Cr_info"].toString()
                                                )
                                                bundle.putString(
                                                    "Cr_data_time",
                                                    hashMap["Cr_data_time"].toString()
                                                )

                                                val detailCourseFragment: DetailCourseFragment? =
                                                    activity!!.fragmentManager
                                                        .findFragmentById(R.id.fragment_add_course) as DetailCourseFragment?

                                                if (detailCourseFragment == null) {
                                                    val newFragment = DetailCourseFragment()
                                                    newFragment.arguments = bundle
                                                    fragmentManager!!.beginTransaction()
                                                        .replace(
                                                            R.id.navigation_view,
                                                            newFragment,
                                                            ""
                                                        )
                                                        .addToBackStack(null)
                                                        .commit()
                                                } else {
                                                    fragmentManager!!.beginTransaction()
                                                        .replace(
                                                            R.id.navigation_view,
                                                            detailCourseFragment,
                                                            ""
                                                        )
                                                        .addToBackStack(null)
                                                        .commit()
                                                }

                                                // On long click
                                            } else {
                                                mDialog.onDialog(
                                                    activity!!,
                                                    false,
                                                    "เลือกการทำงาน"
                                                ) { s ->
                                                    //เลือก แก้ไข
                                                    if (s == "update") {
                                                        val myMap2 = HashMap<String, String>()
                                                        myMap2["Cr_id"] =
                                                            hashMap["Cr_id"].toString()
                                                        myMap2["Cr_name"] =
                                                            hashMap["Cr_name"].toString()
                                                        myMap2["Cr_price"] =
                                                            hashMap["Cr_price"].toString()
                                                        myMap2["Cr_info"] =
                                                            hashMap["Cr_info"].toString()
                                                        myMap2["Cr_data_time"] =
                                                            hashMap["Cr_data_time"].toString()

                                                        mDialog.dialogEditCourse(
                                                            activity!!,
                                                            "แก้ไขข้อมูล",
                                                            myMap2
                                                        ) { h ->
                                                            mHomePresenter.updateCourse(
                                                                user?.T_id!!,
                                                                h
                                                            ) { b, s ->
                                                                if (b) {
                                                                    showCourse()
                                                                    Toast.makeText(
                                                                        activity,
                                                                        s,
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                } else {
                                                                    showCourse()
                                                                    Toast.makeText(
                                                                        activity,
                                                                        s,
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                }
                                                            }
                                                        }

                                                        //เลือก ลบ
                                                    } else {
                                                        mDialog.dialogQuestion(
                                                            activity!!,
                                                            false,
                                                            "ลบข้อมูล",
                                                            "ต้องการลบข้อมูลคอร์สเรียนหรือไม่?"
                                                        ) { d, a ->
                                                            if (d) {
                                                                mHomePresenter.deleteCourse(
                                                                    user?.T_id,
                                                                    hashMap["Cr_id"]
                                                                ) { boolean, string ->
                                                                    if (boolean) {
                                                                        showCourse()
                                                                        Toast.makeText(
                                                                            activity,
                                                                            string,
                                                                            Toast.LENGTH_SHORT
                                                                        ).show()
                                                                    } else {
                                                                        showCourse()
                                                                        Toast.makeText(
                                                                            activity,
                                                                            string,
                                                                            Toast.LENGTH_SHORT
                                                                        ).show()
                                                                    }
                                                                }
                                                            }
                                                        }

                                                    }
                                                }
                                            }

                                        }
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
                            }
                        })
                } else {
                    rvCourse.visibility = View.GONE
                    layoutNetworkError.visibility = View.VISIBLE
                }
            }
            else -> {
                user?.admin_id!!
            }
        }


    }

    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        val nav = (activity as MainActivity).getNav()
        tvTitle.text = "คอร์ส"
        back.visibility = View.GONE
        (activity as MainActivity?)!!.updateNavigationBarState(R.id.navigation_home)

    }


    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.btnRefresh -> {
                showCourse()
                //Toast.makeText(activity, "อิอิ", Toast.LENGTH_SHORT).show()
            }
            R.id.btnAddCourse -> {
                val addCourseFragment: AddCourseFragment? =
                    activity!!.fragmentManager
                        .findFragmentById(R.id.fragment_add_course) as AddCourseFragment?

                if (addCourseFragment == null) {
                    val newFragment = AddCourseFragment()
                    fragmentManager!!.beginTransaction()
                        .replace(R.id.navigation_view, newFragment, "")
                        .addToBackStack(null)
                        .commit()
                } else {
                    fragmentManager!!.beginTransaction()
                        .replace(R.id.navigation_view, addCourseFragment, "")
                        .addToBackStack(null)
                        .commit()
                }

            }
        }


    }
}