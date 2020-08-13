package com.example.tutorchinese.ui.view.course.add_content

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.view.main.MainActivity
import java.io.File

@Suppress("DEPRECATED_IDENTITY_EQUALS", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AddContentFragment : Fragment(), View.OnClickListener {

    private var supportFragmentManager: FragmentManager? = null
    private lateinit var addContentPresenter: AddContentPresenter

    private var courseId = ""

    private var fileUri: Uri? = null
    private var filePath: String? = null
    var tempFile : File? = null

    private val FILE_SELECT_CODE = 1001

    //layout
    private var edtContentNumber: EditText? = null
    private var edtContentName: EditText? = null
    private var edtContentDetail: EditText? = null

    private var edtCourseLink: EditText? = null

    private var tvFileName: TextView? = null

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
        val btnCourseFile: Button = root.findViewById(R.id.btnCourseFile)
        edtContentNumber = root.findViewById(R.id.edtContentNumber)
        edtContentName = root.findViewById(R.id.edtContentName)
        edtContentDetail = root.findViewById(R.id.edtContentDetail)

        edtCourseLink = root.findViewById(R.id.edtCourseLink)
        tvFileName = root.findViewById(R.id.tvFileName)

        btnSaveContent.setOnClickListener(this)
        btnCourseFile.setOnClickListener(this)

        val bundle = arguments
        if (bundle != null) {
            courseId = bundle.getString("Cr_id").toString()
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSaveContent -> {
               // Toast.makeText(activity!!, "การที่เรานั้นจะทำ", Toast.LENGTH_SHORT).show()
                addContentPresenter.sendDataCourseToServer(
                    activity,
                    edtContentNumber?.text.toString(),
                    edtContentName?.text.toString(),
                    edtContentDetail?.text.toString(),
                    courseId,
                    edtCourseLink?.text.toString(),
                    tempFile!!
                ) { b, t ->
                    if (b) {
                        Toast.makeText(activity, t, Toast.LENGTH_SHORT).show()
                        fragmentManager!!.popBackStack()
                    }
                }
            }
            R.id.btnCourseFile->{
                chooseFile()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            FILE_SELECT_CODE -> if (resultCode === -1) {
                fileUri = data!!.data
                filePath = fileUri?.path
                /*  tvFileName?.text = filePath*/
                tempFile = File(fileUri?.path)

                tvFileName?.text = tempFile!!.name

            }
        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun chooseFile() {

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        try {
            startActivityForResult(
                Intent.createChooser(intent, "เลือกไฟล์"),
                FILE_SELECT_CODE
            )
        } catch (ex: ActivityNotFoundException) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(
                activity, "กรุณาติดตั้ง File Manager..",
                Toast.LENGTH_SHORT
            ).show()
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