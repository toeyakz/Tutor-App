package com.example.tutorchinese.ui.view.check_order

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.controler.CustomDialog
import com.example.tutorchinese.ui.controler.PreferencesData
import com.example.tutorchinese.ui.data.response.CartByTutor
import com.example.tutorchinese.ui.data.response.CartByTutorResponse
import com.example.tutorchinese.ui.view.adpater.CheckOrderAdapter
import java.util.*

@Suppress("DEPRECATION")
class CheckOrderConfirmFragment : Fragment() {

    private lateinit var mCheckOrderPresenter: CheckOrderPresenter
    private var user: PreferencesData.Users? = null
    private lateinit var mCheckOrderAdapter: CheckOrderAdapter

    private var mDialog = CustomDialog()

    // recycler view
    private lateinit var rvConfirm: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_check_order_confirm, container, false)
        initView(root)
        return root
    }

    @SuppressLint("SetTextI18n")
    private fun initView(root: View) {
        mCheckOrderPresenter = CheckOrderPresenter()
        user = PreferencesData.user(activity!!)

        rvConfirm = root.findViewById(R.id.rvConfirm)
    }

    override fun onResume() {
        super.onResume()
        contentView()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            contentView()
            Log.d("Asd6asd", "toast_02")
        }else{

        }
    }

    private fun contentView() {
        mCheckOrderPresenter = CheckOrderPresenter()
        user = PreferencesData.user(activity!!)


        val lists = ArrayList<CartByTutor>()

        mCheckOrderPresenter.getBankDetail(
            user?.T_id.toString(),
            object : CheckOrderPresenter.Response.CartByTutor {
                override fun value(c: CartByTutorResponse?) {

                    if (c != null) {
                        for (i in c.data!!) {
                            if (i.O_status == "1") {
                                lists.add(i)
                            }

                        }
                        mCheckOrderAdapter =
                            CheckOrderAdapter(activity!!, lists){hashMap, b ->

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

                                mDialog.dialogDetailCheckOrder(activity!!, "ข้อมูลการโอนเงิน", cartByTutor){b ->

                                }
                            }

                        rvConfirm.apply {
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