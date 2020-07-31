package com.example.tutorchinese.ui.view.course.course_detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
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
import com.example.tutorchinese.ui.controler.PreferencesData
import com.example.tutorchinese.ui.data.entities.Contents
import com.example.tutorchinese.ui.data.response.BankDetailsResponse
import com.example.tutorchinese.ui.data.response.ContentResponse
import com.example.tutorchinese.ui.data.response.OrdersFromUserResponse
import com.example.tutorchinese.ui.view.adpater.ContentAdapter
import com.example.tutorchinese.ui.view.course.add_content.AddContentFragment
import com.example.tutorchinese.ui.view.course.content_detail.DetailContentFragment
import com.example.tutorchinese.ui.view.course.course_main.HomePresenter
import com.example.tutorchinese.ui.view.main.MainActivity
import com.example.tutorchinese.ui.view.payment.PaymentInformationFragment
import java.util.ArrayList
import java.util.HashMap

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailCourseFragment : Fragment() {
    private lateinit var mDetailCoursePresenter: DetailCoursePresenter
    private lateinit var rvContent: RecyclerView
    private var user: PreferencesData.Users? = null

    private lateinit var mContentAdapter: ContentAdapter
    private var mDialog = CustomDialog()

    private var courseId = ""
    private var status_price = ""

    //layout
    private lateinit var layoutNetworkError: ConstraintLayout
    private lateinit var notFoundContent: ConstraintLayout
    private lateinit var btnAddContent: LinearLayout
    private lateinit var btnPrice: LinearLayout


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
        user = PreferencesData.user(activity!!)

        val tvCourseName: TextView = root.findViewById(R.id.tvCourseName)
        val tvCourseDetails: TextView = root.findViewById(R.id.tvCourseDetails)
        val tvCoursePrice: TextView = root.findViewById(R.id.tvCoursePrice)
        val swipe: SwipeRefreshLayout = root.findViewById(R.id.swipe_content)
        val btnRefresh: Button = root.findViewById(R.id.btnRefresh)
        notFoundContent = root.findViewById(R.id.not_found_content)
        btnAddContent = root.findViewById(R.id.btnAddContent)
        btnPrice = root.findViewById(R.id.btnPrice)

        rvContent = root.findViewById(R.id.rvContent)
        layoutNetworkError = root.findViewById(R.id.layoutNetworkError2)


        val bundle = arguments
        if (bundle != null) {
            tvCourseName.text = bundle.getString("Cr_name")
            tvCourseDetails.text = "\t\t\t\t\t" + bundle.getString("Cr_info")
            tvCoursePrice.text = bundle.getString("Cr_price")

            courseId = bundle.getString("Cr_id").toString()
            status_price = bundle.getString("status_price").toString()
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

        btnPrice.setOnClickListener {

            val bundle = arguments
            if (bundle != null) {

            mDetailCoursePresenter.getBankDetail(
                bundle.getString("T_id").toString(),
                object : DetailCoursePresenter.Response.BankDetail {
                    override fun value(
                        bank: BankDetailsResponse
                    ) {

                        val bundle2 =
                            Bundle()
                        bundle2.putString(
                            "Cr_id",
                            bundle.getString("Cr_id").toString()
                        )
                        bundle2.putString(
                            "Cr_name",
                            bundle.getString("Cr_name").toString()
                        )
                        bundle2.putString(
                            "Cr_price",
                            bundle.getString("Cr_price").toString()
                        )
                        bundle2.putString(
                            "bank_id",
                            bank.data!![0].bank_id.toString()
                        )
                        bundle2.putString(
                            "T_id",
                            bank.data[0].T_id.toString()
                        )
                        bundle2.putString(
                            "bank_name",
                            bank.data[0].bank_name.toString()
                        )
                        bundle2.putString(
                            "bank_number",
                            bank.data[0].bank_number.toString()
                        )
                        bundle2.putString(
                            "bank_name_account",
                            bank.data[0].bank_name_account.toString()
                        )
                        bundle2.putString(
                            "bank_qr",
                            bank.data[0].bank_qr.toString()
                        )

                        val paymentInformationFragment: PaymentInformationFragment? =
                            activity!!.fragmentManager
                                .findFragmentById(
                                    R.id.fragment_payment_information
                                ) as PaymentInformationFragment?

                        if (paymentInformationFragment == null) {
                            val newFragment =
                                PaymentInformationFragment()
                            newFragment.arguments =
                                bundle2
                            fragmentManager!!.beginTransaction()
                                .replace(
                                    R.id.navigation_view,
                                    newFragment,
                                    ""
                                )
                                .addToBackStack(
                                    null
                                )
                                .commit()
                        } else {
                            fragmentManager!!.beginTransaction()
                                .replace(
                                    R.id.navigation_view,
                                    paymentInformationFragment,
                                    ""
                                )
                                .addToBackStack(
                                    null
                                )
                                .commit()
                        }
                    }

                    override fun error(
                        c: String?
                    ) {

                    }
                })
            }
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
        when (user?.type) {
            "user" -> {
                when (status_price) {
                    "ซื้อแล้ว" -> {
                        btnAddContent.visibility = View.GONE
                        btnPrice.visibility = View.GONE
                    }
                    "กำลังตรวจสอบ" -> {
                        btnAddContent.visibility = View.GONE
                        btnPrice.visibility = View.GONE
                    }
                    else -> {
                        btnAddContent.visibility = View.GONE
                        btnPrice.visibility = View.VISIBLE
                    }
                }
               /* mDetailCoursePresenter.getOrdersFromUser(
                    user?.U_id.toString(),
                    hashMap["Co_id"].toString(),
                    object : DetailCoursePresenter.Response.OrdersUser {
                        override fun value(c: OrdersFromUserResponse) {
                            //ซื้อแล้ว
                            if (c.isSuccessful) {
                                if (c.data!!.isNotEmpty()) {
                                    if (c.data[0].O_status == 0) {

                                        btnAddContent.visibility = View.GONE
                                        btnPrice.visibility = View.GONE
                                        *//*holder.itemView.setOnClickListener {
                                            mOnClickList.invoke(myMap, "กำลังตรวจสอบ")
                                        }*//*
                                    } else if (c.data[0].O_status == 1) {
                                        btnAddContent.visibility = View.GONE
                                        btnPrice.visibility = View.GONE

                                        *//* holder.itemView.setOnClickListener {
                                             mOnClickList.invoke(myMap, "ซื้อแล้ว")
                                         }*//*
                                    }
                                }
                                //ยังไม่ซื้อ
                            } else {
                                btnAddContent.visibility = View.GONE
                                btnPrice.visibility = View.VISIBLE
                            }

                        }

                        override fun error(c: String?) {

                        }

                    })*/
            }
            "tutor" -> {
                btnAddContent.visibility = View.VISIBLE
                btnPrice.visibility = View.GONE
            }
            else -> {

            }
        }
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

                                        when (user?.type) {
                                            "user" -> {
                                                when (status_price) {
                                                    "ซื้อแล้ว" -> {

                                                    }
                                                    "กำลังตรวจสอบ" -> {
                                                        return@ContentAdapter
                                                    }
                                                    else -> {
                                                       return@ContentAdapter
                                                    }
                                                }
                                            }
                                        }

                                        val bundle = Bundle()
                                        bundle.putString("Co_id", hashMap["Co_id"].toString())
                                        bundle.putString("Cr_id", hashMap["Cr_id"].toString())
                                        bundle.putString("Co_name", hashMap["Co_name"].toString())
                                        bundle.putString("Cr_info", hashMap["Co_info"].toString())
                                        bundle.putString(
                                            "Co_chapter_number",
                                            hashMap["Co_chapter_number"].toString()
                                        )

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