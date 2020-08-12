package com.example.tutorchinese.ui.view.course.add_course

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.controler.PreferencesData
import com.example.tutorchinese.ui.view.main.MainActivity


class AddCourseFragment : Fragment(), View.OnClickListener {

    private var supportFragmentManager: FragmentManager? = null
    private lateinit var addCoursePresenter: AddCoursePresenter
    private var user: PreferencesData.Users? = null

    private val FILE_SELECT_CODE = 1001

    //layout
    private var edtCourseName: EditText? = null
    private var edtCoursePrice: EditText? = null
    private var edtCourseDetail: EditText? = null
    private var edtCourseLink: EditText? = null

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
       /* val nav = (activity as MainActivity).getNav()
        nav.visibility = View.VISIBLE*/
    }

    private fun initView(root: View) {
        supportFragmentManager = fragmentManager
        addCoursePresenter = AddCoursePresenter()
        user = PreferencesData.user(activity!!)


        val btnSaveCourse: Button = root.findViewById(R.id.btnSaveCourse)
        val btnCourseFile: Button = root.findViewById(R.id.btnCourseFile)
        edtCourseName = root.findViewById(R.id.edtCourseName)
        edtCoursePrice = root.findViewById(R.id.edtCoursePrice)
        edtCourseDetail = root.findViewById(R.id.edtCourseDetail)
        edtCourseLink = root.findViewById(R.id.edtCourseLink)


        btnSaveCourse.setOnClickListener(this)
        btnCourseFile.setOnClickListener(this)



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
            R.id.btnCourseFile->{
                chooseFile()
            }
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            FILE_SELECT_CODE -> {
                // Get the Uri of the selected file
                val uri = data?.data!!
                Log.d("as685d12asd", "File Uri: $uri")
                // Get the path


                val path: String = convertMediaUriToPath(uri)!!
                Log.d("as685d12asd", "File Path: $path")
                // Get the file instance
                // File file = new File(path);
                // Initiate the upload
            }
        }
        super.onActivityResult(requestCode, resultCode, data)

    }



    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        val nav = (activity as MainActivity).getNav()
        //nav.visibility = View.GONE
        tvTitle.text = "เพิ่มคอร์สเรียน"
        back.visibility = View.VISIBLE



        back.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
    }
}