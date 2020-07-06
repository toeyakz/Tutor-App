package com.example.tutorchinese.ui.view.tutor.course_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.controler.CustomDialog
import com.example.tutorchinese.ui.controler.NetworkConnectCheck
import com.example.tutorchinese.ui.data.entities.Contents
import com.example.tutorchinese.ui.data.response.ContentResponse
import com.example.tutorchinese.ui.data.response.CourseResponse
import com.example.tutorchinese.ui.view.adpater.ContentAdapter
import com.example.tutorchinese.ui.view.tutor.add_content.AddContentFragment
import com.example.tutorchinese.ui.view.tutor.content_detail.DetailContentFragment
import com.example.tutorchinese.ui.view.tutor.main.MainActivity
import java.util.ArrayList
import java.util.HashMap

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailCourseFragment : Fragment() {
    private lateinit var mDetailCoursePresenter: DetailCoursePresenter
    private lateinit var rvContent: RecyclerView

    private lateinit var mContentAdapter: ContentAdapter
    private var mDialog = CustomDialog()

    private var courseId = ""

    //layout
    private lateinit var layoutNetworkError: ConstraintLayout
    private lateinit var notFoundContent: ConstraintLayout
    private lateinit var btnAddContent: LinearLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detail_course, container, false)
        initView(root)
        return root
    }

    override fun onResume() {
        super.onResume()
        manageToolbar()
        showContent()
    }

    private fun initView(root: View) {
        mDetailCoursePresenter = DetailCoursePresenter()

        val tvCourseName: TextView = root.findViewById(R.id.tvCourseName)
        val tvCourseDetails: TextView = root.findViewById(R.id.tvCourseDetails)
        val tvCoursePrice: TextView = root.findViewById(R.id.tvCoursePrice)
        val swipe: SwipeRefreshLayout = root.findViewById(R.id.swipe_content)
        val btnRefresh: Button = root.findViewById(R.id.btnRefresh)
        notFoundContent = root.findViewById(R.id.not_found_content)
        btnAddContent = root.findViewById(R.id.btnAddContent)

        rvContent = root.findViewById(R.id.rvContent)
        layoutNetworkError = root.findViewById(R.id.layoutNetworkError2)


        val bundle = arguments
        if (bundle != null) {
            tvCourseName.text = bundle.getString("Cr_name")
            tvCourseDetails.text = bundle.getString("Cr_info")
            tvCoursePrice.text = bundle.getString("Cr_price")

            courseId = bundle.getString("Cr_id").toString()
        }

        swipe.setOnRefreshListener {
            showContent()
            swipe.isRefreshing = false
        }

        btnRefresh.setOnClickListener {
            showContent()
        }
        btnAddContent.setOnClickListener {

            val name = "DetailCourseFragment"
            val bundle2 = Bundle()
            bundle2.putString("Cr_id", courseId)

            val addContentFragment: AddContentFragment? =
                activity!!.fragmentManager
                    .findFragmentById(R.id.fragment_add_content) as AddContentFragment?

            if (addContentFragment == null) {
                val newFragment = AddContentFragment()
                newFragment.arguments = bundle2
                fragmentManager!!.beginTransaction()
                    .replace(R.id.navigation_view, newFragment, "")
                    .addToBackStack(name)
                    .commit()
            } else {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.navigation_view, addContentFragment, "")
                    .addToBackStack(name)
                    .commit()
            }
            fragmentManager!!.popBackStack(name, 0)

        }


        //  startDate = bundle.getString("startDate", startDate)


    }

    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        tvTitle.text = "รายละเอียดคอร์สเรียน"
        back.visibility = View.VISIBLE

        back.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
    }

    private fun showContent() {
        if (NetworkConnectCheck().isNetworkConnected(activity!!)) {
            mDetailCoursePresenter.contentData(courseId, object : DetailCoursePresenter.Response {
                override fun value(c: ContentResponse) {
                    if (c.isSuccessful) {
                        if (c.data != null) {
                            notFoundContent.visibility = View.GONE
                            layoutNetworkError.visibility = View.GONE
                            rvContent.visibility = View.VISIBLE

                            mContentAdapter =
                                ContentAdapter(
                                    activity!!,
                                    c.data as ArrayList<Contents>
                                ) { hashMap, b ->
                                    // On click
                                    if (b) {

                                        val bundle = Bundle()
                                        bundle.putString("Co_id", hashMap["Co_id"].toString())
                                        bundle.putString("Cr_id", hashMap["Cr_id"].toString())
                                        bundle.putString("Co_name", hashMap["Co_name"].toString())
                                        bundle.putString("Cr_info", hashMap["Co_info"].toString())
                                        bundle.putString("Co_chapter_number",  hashMap["Co_chapter_number"].toString())

                                        val detail: DetailContentFragment? =
                                            activity!!.fragmentManager
                                                .findFragmentById(R.id.fragment_detail_content) as DetailContentFragment?

                                        if (detail == null) {
                                            val newFragment = DetailContentFragment()
                                            newFragment.arguments = bundle
                                            fragmentManager!!.beginTransaction()
                                                .replace(R.id.navigation_view, newFragment, "")
                                                .addToBackStack(null)
                                                .commit()
                                        } else {
                                            fragmentManager!!.beginTransaction()
                                                .replace(R.id.navigation_view, detail, "")
                                                .addToBackStack(null)
                                                .commit()
                                        }

                                        // On long click
                                    } else {
                                        mDialog.onDialog(activity!!, false, "เลือกการทำงาน") { s ->
                                            //เลือก แก้ไข
                                            if (s == "update") {

                                                val myMap2 = HashMap<String, String>()
                                                myMap2["Co_id"] = hashMap["Co_id"].toString()
                                                myMap2["Cr_id"] = hashMap["Cr_id"].toString()
                                                myMap2["Co_name"] = hashMap["Co_name"].toString()
                                                myMap2["Co_info"] = hashMap["Co_info"].toString()
                                                myMap2["Co_chapter_number"] =
                                                    hashMap["Co_chapter_number"].toString()

                                                mDialog.dialogEditContent(
                                                    activity!!,
                                                    "แก้ไขข้อมูล",
                                                    myMap2
                                                ) { h ->
                                                    mDetailCoursePresenter.updateCourse(
                                                        courseId,
                                                        h
                                                    ) { b, s ->
                                                        if (b) {
                                                            showContent()
                                                            Toast.makeText(
                                                                activity,
                                                                s,
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        } else {
                                                            showContent()
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
                                                        mDetailCoursePresenter.deleteCourse(
                                                            hashMap["Co_id"].toString()
                                                        ) { boolean, string ->
                                                            if (boolean) {
                                                                showContent()
                                                                Toast.makeText(
                                                                    activity,
                                                                    string,
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            } else {
                                                                showContent()
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

                            rvContent.apply {
                                layoutManager = LinearLayoutManager(activity)
                                adapter = mContentAdapter
                                mContentAdapter.notifyDataSetChanged()
                            }

                        } else {
                            notFoundContent.visibility = View.VISIBLE
                            layoutNetworkError.visibility = View.GONE
                            rvContent.visibility = View.GONE
                        }
                    } else {
                        notFoundContent.visibility = View.VISIBLE
                        layoutNetworkError.visibility = View.GONE
                        rvContent.visibility = View.GONE

                    }
                }

                override fun error(c: String?) {
                    rvContent.visibility = View.GONE
                    notFoundContent.visibility = View.GONE
                    layoutNetworkError.visibility = View.VISIBLE

                }
            })


        } else {
            rvContent.visibility = View.GONE
            notFoundContent.visibility = View.GONE
            layoutNetworkError.visibility = View.VISIBLE
        }
    }

}