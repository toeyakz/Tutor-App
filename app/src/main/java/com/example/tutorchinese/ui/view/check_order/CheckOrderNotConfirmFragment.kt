package com.example.tutorchinese.ui.view.check_order

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.controler.Constants
import com.example.tutorchinese.ui.controler.CustomDialog
import com.example.tutorchinese.ui.controler.CustomProgressDialog
import com.example.tutorchinese.ui.controler.PreferencesData
import com.example.tutorchinese.ui.data.response.CartByTutor
import com.example.tutorchinese.ui.data.response.CartByTutorResponse
import com.example.tutorchinese.ui.view.adpater.CheckOrderAdapter
import java.util.*

@Suppress("DEPRECATION")
class CheckOrderNotConfirmFragment : Fragment() {

    private lateinit var mCheckOrderPresenter: CheckOrderPresenter
    private var user: PreferencesData.Users? = null
    private lateinit var mCheckOrderAdapter: CheckOrderAdapter

    private var mDialog = CustomDialog()
    private var dialog: CustomProgressDialog? = null


    // recycler view
    private lateinit var rvNotConfirm: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_check_order_not_confirm, container, false)
        initView(root)
        return root
    }

    @SuppressLint("SetTextI18n")
    private fun initView(root: View) {
        mCheckOrderPresenter = CheckOrderPresenter()
        user = PreferencesData.user(activity!!)

        rvNotConfirm = root.findViewById(R.id.rvNotConfirm)
    }

    override fun onResume() {
        super.onResume()
        contentView()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            contentView()
            Log.d("Asd6asd", "toast_01")
        }
    }


    private fun contentView() {
        mCheckOrderPresenter = CheckOrderPresenter()
        // user = PreferencesData.user(activity!!)

        val lists = ArrayList<CartByTutor>()

        mCheckOrderPresenter.getBankDetail(
            user?.T_id.toString(),
            object : CheckOrderPresenter.Response.CartByTutor {
                override fun value(c: CartByTutorResponse?) {

                    if (c != null) {
                        for (i in c.data!!) {
                            if (i.O_status == "0") {
                                lists.add(i)
                            }

                        }
                        mCheckOrderAdapter =
                            CheckOrderAdapter(activity!!, lists) { hashMap, b ->

                                if (b) {
                                    val bundle = Bundle()
                                    bundle.putString("O_bank", hashMap["O_bank"].toString())
                                    bundle.putString("Cr_id", hashMap["Cr_id"].toString())
                                    bundle.putString("Cr_name", hashMap["Cr_name"].toString())
                                    bundle.putString("O_bank_num", hashMap["O_bank_num"].toString())
                                    bundle.putString("O_date", hashMap["O_date"].toString())
                                    bundle.putString("O_id", hashMap["O_id"].toString())
                                    bundle.putString("O_image", hashMap["O_image"].toString())
                                    bundle.putString("O_price", hashMap["O_price"].toString())
                                    bundle.putString("O_status", hashMap["O_status"].toString())
                                    bundle.putString("O_time", hashMap["O_time"].toString())
                                    bundle.putString("U_id", hashMap["U_id"].toString())

                                    val cartByTutor = CartByTutor(
                                        O_time = hashMap["O_time"].toString(),
                                        U_id = hashMap["U_id"].toString(),
                                        O_status = hashMap["O_status"].toString(),
                                        O_price = hashMap["O_price"].toString(),
                                        O_image = hashMap["O_image"].toString(),
                                        O_id = hashMap["O_id"].toString(),
                                        O_date = hashMap["O_date"].toString(),
                                        O_bank_num = hashMap["O_bank_num"].toString(),
                                        Cr_name = hashMap["Cr_name"].toString(),
                                        O_bank = hashMap["O_bank"].toString(),
                                        Cr_id = hashMap["Cr_id"].toString(),
                                        U_lastname = hashMap["U_lastname"].toString(),
                                        U_name = hashMap["U_name"].toString()
                                    )

                                    mDialog.dialogDetailCheckOrder(
                                        activity!!,
                                        "ข้อมูลการโอนเงิน",
                                        cartByTutor
                                    ) { b ->
                                        if (b) {
                                            val builder: AlertDialog.Builder =
                                                AlertDialog.Builder(activity)
                                            builder.setTitle("ตรวจสอบการโอนเงิน")
                                                .setMessage("คุณต้องการยืนยันการตรวจสอบข้อมูลการโอนเงินหรือไม่?")
                                                .setPositiveButton(
                                                    "ยืนยัน"
                                                ) { _, _ ->
                                                    mCheckOrderPresenter.updateCourse(hashMap["O_id"].toString()) { bb, s ->
                                                        if (bb) {
                                                            contentView()
                                                        }
                                                    }
                                                }
                                                .setNegativeButton("ยกเลิก") { _, _ ->

                                                }
                                                .show()
                                        }
                                    }


                                    /*val paymentInformationFragment: DetailCheckOrderFragment? =
                                        activity!!.fragmentManager
                                            .findFragmentById(
                                                R.id.fragment_detail_check_order
                                            ) as DetailCheckOrderFragment?

                                    if (paymentInformationFragment == null) {
                                        val newFragment =
                                            DetailCheckOrderFragment()
                                        newFragment.arguments =
                                            bundle
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
                                    }*/

                                }

                            }

                        rvNotConfirm.apply {
                            layoutManager = LinearLayoutManager(activity)
                            adapter = mCheckOrderAdapter
                            mCheckOrderAdapter.notifyDataSetChanged()

                        }

                    }
                }

                override fun error(c: String?) {

                }
            })
    }


}