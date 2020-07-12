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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.controler.PreferencesData
import com.example.tutorchinese.ui.controler.Utils
import com.example.tutorchinese.ui.view.main.MainActivity
import com.squareup.picasso.Picasso
import java.io.File
import java.lang.Exception


class BankEditFragment : Fragment() {

    private var user: PreferencesData.Users? = null
    private lateinit var mBankPresenter: BankPresenter
    private var imageName =""

    private val PICK_IMAGE = 1002

    //image view
    private lateinit var addImageQRCode: ImageView
    private lateinit var imageQRCode: ImageView

    //edit text
    private lateinit var edtBankName: EditText
    private lateinit var edtBankNumber: EditText
    private lateinit var edtAccountName: EditText

    //button
    private lateinit var btnSaveBank: Button

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_bank_edit, container, false)

        user = PreferencesData.user(activity!!)
        mBankPresenter = BankPresenter()
        manageToolbar()
        initView(root)

        return root
    }

    private fun initView(root: View) {
        addImageQRCode = root.findViewById(R.id.addImageQRCode)
        edtBankName = root.findViewById(R.id.edtBankName)
        edtBankNumber = root.findViewById(R.id.edtBankNumber)
        edtAccountName = root.findViewById(R.id.edtAccountName)
        btnSaveBank = root.findViewById(R.id.btnSaveBank)
        imageQRCode = root.findViewById(R.id.imageQRCode)


        addImageQRCode.setOnClickListener {
           // addImageQRCode.setImageDrawable(null)

            val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i, PICK_IMAGE)
        }

        setData()


    }

    private fun setData() {
        val bundle = arguments
        if (bundle != null) {
            edtBankName.setText(bundle.getString("bank_name"))
            edtBankNumber.setText(bundle.getString("bank_number"))
            edtAccountName.setText(bundle.getString("bank_name_account"))

            /*imageQRCode.visibility = View.GONE
            addImageQRCode.visibility = View.VISIBLE
*/
            Picasso.get()
                .load(Utils.host + "/tutor/img/imageqrcode/" + bundle.getString("bank_qr"))
                .into(addImageQRCode)


            btnSaveBank.setOnClickListener {
                mBankPresenter.upLoadEditBankDetails(
                    bundle.getString("T_id").toString(),
                    bundle.getString("bank_id").toString(),
                    imageName,
                    edtBankName.text.toString(),
                    edtBankNumber.text.toString(),
                    edtAccountName.text.toString()
                ){
                    if(it){
                        fragmentManager!!.popBackStack()
                        Toast.makeText(activity, "แก้ไขข้อมูลบัญชีสำเร็จ!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(activity, "พบข้อผิดพลาด!", Toast.LENGTH_SHORT).show()
                    }
                }
                //   Log.d("As5da1sda", imageName)
            }
        }
    }

    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        tvTitle.text = "แก้ไขข้อมูลบัญชีธนาคาร"
        back.visibility = View.VISIBLE

        back.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("As6dasd", "1")
        if (PICK_IMAGE == requestCode && resultCode == Activity.RESULT_OK) {

            try {

                /*imageQRCode.visibility = View.VISIBLE
                addImageQRCode.visibility = View.GONE*/

                val pickedImage: Uri = data?.data!!

                val filePath = arrayOf(MediaStore.Images.Media.DATA)
                val cursor: Cursor =
                    activity!!.contentResolver.query(pickedImage, filePath, null, null, null)!!
                cursor.moveToFirst()
                val imagePath: String = cursor.getString(cursor.getColumnIndex(filePath[0]))
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                val bitmap = BitmapFactory.decodeFile(imagePath, options)

                Log.d("As5da1sda", File(imagePath).absolutePath )
                imageName = imagePath

                Picasso.get().load(File(imagePath)).into(addImageQRCode)

            }catch (e: Exception){
                e.printStackTrace()
            }

            // addImageQRCode.setImageBitmap(bitmap)


        }
    }


}