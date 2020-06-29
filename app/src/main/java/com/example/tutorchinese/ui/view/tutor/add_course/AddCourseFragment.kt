package com.example.tutorchinese.ui.view.tutor.add_course

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.manage.PreferencesData
import com.example.tutorchinese.ui.view.tutor.main.MainActivity

class AddCourseFragment : Fragment(), View.OnClickListener {

    private var supportFragmentManager: FragmentManager? = null
    private lateinit var addCoursePresenter: AddCoursePresenter
    private var user: PreferencesData.Users? = null

    //layout
    private var edtCourseName: EditText? = null
    private var edtCoursePrice: EditText? = null
    private var edtCourseDetail: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_course, container, false)
        initView(root)
        return root
    }

    override fun onResume() {
        super.onResume()
        manageToolbar()
    }

    override fun onPause() {
        super.onPause()
        val nav = (activity as MainActivity).getNav()
        nav.visibility = View.VISIBLE
    }

    private fun initView(root: View) {
        supportFragmentManager = fragmentManager
        addCoursePresenter = AddCoursePresenter()
        user = PreferencesData.user(activity!!)


        val btnSaveCourse: Button = root.findViewById(R.id.btnSaveCourse)
        edtCourseName = root.findViewById(R.id.edtCourseName)
        edtCoursePrice = root.findViewById(R.id.edtCoursePrice)
        edtCourseDetail = root.findViewById(R.id.edtCourseDetail)

        btnSaveCourse.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.btnSaveCourse -> {
                addCoursePresenter.sendDataCourseToServer(
                    activity,
                    edtCourseName?.text.toString(),
                    edtCoursePrice?.text.toString(),
                    edtCourseDetail?.text.toString(),
                    user?.T_id.toString(),
                    user?.T_username!!
                ) { b, t ->
                    if (b) {
                        Toast.makeText(activity, t, Toast.LENGTH_SHORT).show()
                        fragmentManager!!.popBackStack()
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        val nav = (activity as MainActivity).getNav()
        nav.visibility = View.GONE
        tvTitle.text = "เพิ่มคอร์สเรียน"
        back.visibility = View.VISIBLE



        back.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
    }
}