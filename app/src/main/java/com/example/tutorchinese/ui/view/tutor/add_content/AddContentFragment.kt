package com.example.tutorchinese.ui.view.tutor.add_content

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.view.tutor.add_course.AddCoursePresenter
import com.example.tutorchinese.ui.view.tutor.main.MainActivity

class AddContentFragment : Fragment(), View.OnClickListener {

    private var supportFragmentManager: FragmentManager? = null
    private lateinit var addContentPresenter: AddContentPresenter

    private var courseId = ""

    //layout
    private var edtContentNumber: EditText? = null
    private var edtContentName: EditText? = null
    private var edtContentDetail: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_add_content, container, false)
        initView(root)
        return root
    }

    override fun onResume() {
        super.onResume()
        manageToolbar()
    }

    private fun initView(root: View) {
        supportFragmentManager = fragmentManager
        addContentPresenter = AddContentPresenter()
        val btnSaveContent: Button = root.findViewById(R.id.btnSaveContent)
        edtContentNumber = root.findViewById(R.id.edtContentNumber)
        edtContentName = root.findViewById(R.id.edtContentName)
        edtContentDetail = root.findViewById(R.id.edtContentDetail)

        btnSaveContent.setOnClickListener(this)

        val bundle = arguments
        if (bundle != null) {
            courseId = bundle.getString("Cr_id").toString()
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSaveContent -> {
                Toast.makeText(activity!!, "การที่เรานั้นจะทำ", Toast.LENGTH_SHORT).show()
                addContentPresenter.sendDataCourseToServer(
                    activity,
                    edtContentNumber?.text.toString(),
                    edtContentName?.text.toString(),
                    edtContentDetail?.text.toString(),
                    courseId
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
        //nav.visibility = View.GONE
        tvTitle.text = "เพิ่มบทเรียน"
        back.visibility = View.VISIBLE



        back.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
    }

}