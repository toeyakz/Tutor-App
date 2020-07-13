package com.example.tutorchinese.ui.view.payment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.controler.PreferencesData
import com.example.tutorchinese.ui.controler.Utils
import com.example.tutorchinese.ui.view.main.MainActivity
import com.squareup.picasso.Picasso
import java.io.File
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class PaymentConfirmationFragment : Fragment() {

    private lateinit var mPaymentPresenter: PaymentPresenter
    private var user: PreferencesData.Users? = null

    private val PICK_IMAGE = 1003
    private var imageName = ""

    //text view
    private lateinit var tvCoursePrice: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvTime: TextView

    //image view
    private lateinit var imageQRCode: ImageView

    //button
    private lateinit var btnSaveOrder: Button

    //edit text
    private lateinit var edtName: EditText
    private lateinit var edtPriceTotal: EditText
    private lateinit var edtBankNumber: EditText

    override fun onResume() {
        super.onResume()
        manageToolbar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_payment_confirmation, container, false)
        // manageToolbar()
        initView(root)
        return root
    }

    @SuppressLint("SimpleDateFormat")
    private fun initView(root: View) {
        mPaymentPresenter = PaymentPresenter()
        user = PreferencesData.user(activity!!)

        tvCoursePrice = root.findViewById(R.id.tvCoursePrice)
        tvDate = root.findViewById(R.id.tvDate)
        tvTime = root.findViewById(R.id.tvTime)
        imageQRCode = root.findViewById(R.id.imageQRCode)
        btnSaveOrder = root.findViewById(R.id.btnSaveOrder)
        edtName = root.findViewById(R.id.edtName)
        edtPriceTotal = root.findViewById(R.id.edtPriceTotal)
        edtBankNumber = root.findViewById(R.id.edtBankNumber)


        val s: String = Utils.dateFormatter.format(Date())

        tvDate.text = s
        tvTime.text = Utils.timeFormatter.format(Date().time)

        val bundle = arguments
        if (bundle != null) {
            tvCoursePrice.text = bundle.getString("Cr_price")
            Log.d("as5dasd", bundle.getString("U_id"))
        }
        onClick()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (PICK_IMAGE == requestCode && resultCode == Activity.RESULT_OK) {
            val pickedImage: Uri = data?.data!!

            val filePath = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor =
                activity!!.contentResolver.query(pickedImage, filePath, null, null, null)!!
            cursor.moveToFirst()
            val imagePath: String = cursor.getString(cursor.getColumnIndex(filePath[0]))
            imageName = imagePath

            Picasso.get().load(File(imagePath)).into(imageQRCode)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun onClick() {

        btnSaveOrder.setOnClickListener {

            if (imageName == "") {
                Toast.makeText(activity, "กรุณาแนบภาพหลักฐานการชำระ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (edtName.text.isEmpty()) {
                Toast.makeText(activity, "กรุณากรอกธนาคาร", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (edtPriceTotal.text.isEmpty()) {
                Toast.makeText(activity, "กรุณากรอกจำนวนเงิน", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (edtBankNumber.text.isEmpty()) {
                Toast.makeText(activity, "กรุณากรอกเลขบัญชี", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val bundle = arguments
            if (bundle != null) {
                mPaymentPresenter.setOrderDetails(
                    user?.U_id.toString(),
                    bundle.getString("Cr_id").toString(),
                    tvDate.text.toString(),
                    tvTime.text.toString(),
                    edtPriceTotal.text.toString(),
                    edtName.text.toString(),
                    edtBankNumber.text.toString(),
                    imageName
                ) {
                    if (it) {
                        Toast.makeText(activity, "สำเร็จ", Toast.LENGTH_SHORT).show()
                        for (i in 0 until fragmentManager!!.backStackEntryCount) {
                            if (i != 0) {
                                fragmentManager!!.popBackStack()
                            }
                        }

                    } else {
                        Toast.makeText(activity, "พบข้อผิดพลาด", Toast.LENGTH_SHORT).show()
                        for (i in 0 until fragmentManager!!.backStackEntryCount) {
                            if (i != 0) {
                                fragmentManager!!.popBackStack()
                            }
                        }
                    }
                }
            }

        }

        imageQRCode.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i, PICK_IMAGE)
        }

        tvDate.setOnClickListener {
            val calendar = Calendar.getInstance(Locale.getDefault())
            val datePickerDialog = DatePickerDialog(
                activity!!,
                DatePickerDialog.OnDateSetListener { _: DatePicker?, i: Int, i1: Int, i2: Int ->
                    val newDate = Calendar.getInstance()
                    newDate[i, i1] = i2
                    tvDate.text = Utils.dateFormatter.format(newDate.time)

                },
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            )
            datePickerDialog.show()
        }

        tvTime.setOnClickListener {
            val mCurrentTime = Calendar.getInstance()
            val hour = mCurrentTime[Calendar.HOUR_OF_DAY]
            val minute = mCurrentTime[Calendar.MINUTE]
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(
                activity!!,
                OnTimeSetListener { _, selectedHour, selectedMinute ->
                    tvTime.text = "$selectedHour:$selectedMinute"
                }, hour, minute, true
            ) //Yes 24 hour time

            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        tvTitle.text = "ยืนยันการชำระเงิน"
        back.visibility = View.VISIBLE
        (activity as MainActivity?)!!.updateNavigationBarState(R.id.navigation_home)

        back.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
    }


}