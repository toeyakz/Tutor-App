package com.example.tutorchinese.ui.view.bank

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.controler.PreferencesData
import com.example.tutorchinese.ui.view.main.MainActivity
import java.io.File


class BankAddFragment : Fragment() {

    private var user: PreferencesData.Users? = null
    private lateinit var mBankPresenter: BankPresenter
    private lateinit var imageName: File

    private val PICK_IMAGE = 1001

    //image view
    private lateinit var addImageQRCode: ImageView

    //button
    private lateinit var btnSaveBank: Button

    //edit text
    private lateinit var edtBankName: EditText
    private lateinit var edtBankNumber: EditText
    private lateinit var edtAccountName: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_bank_add, container, false)
        user = PreferencesData.user(activity!!)
        mBankPresenter = BankPresenter()
        manageToolbar()
        initView(root)
        return root
    }

    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        tvTitle.text = "เพิ่มข้อมูลบัญชีธนาคาร"
        back.visibility = View.VISIBLE

        back.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
    }

    private fun initView(root: View) {
        addImageQRCode = root.findViewById(R.id.addImageQRCode)
        btnSaveBank = root.findViewById(R.id.btnSaveBank)
        edtBankName = root.findViewById(R.id.edtBankName)
        edtBankNumber = root.findViewById(R.id.edtBankNumber)
        edtAccountName = root.findViewById(R.id.edtAccountName)



        addImageQRCode.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i, PICK_IMAGE)
        }

        btnSaveBank.setOnClickListener {
            mBankPresenter.upLoadBankDetails(
                user?.T_id.toString(),
                imageName,
                edtBankName.text.toString(),
                edtBankNumber.text.toString(),
                edtAccountName.text.toString()
            )
         //   Log.d("As5da1sda", imageName)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("As6dasd", "1")
        if (PICK_IMAGE == requestCode && resultCode == Activity.RESULT_OK) {
            val pickedImage: Uri = data?.data!!

            val filePath = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor =
                activity!!.contentResolver.query(pickedImage, filePath, null, null, null)!!
            cursor.moveToFirst()
            val imagePath: String = cursor.getString(cursor.getColumnIndex(filePath[0]))
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            val bitmap = BitmapFactory.decodeFile(imagePath, options)
            imageName = File(imagePath)
            addImageQRCode.setImageBitmap(bitmap)


        }
    }


}