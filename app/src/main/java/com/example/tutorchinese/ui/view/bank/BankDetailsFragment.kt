package com.example.tutorchinese.ui.view.bank

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.controler.PreferencesData
import com.example.tutorchinese.ui.data.response.BankDetailsResponse
import com.example.tutorchinese.ui.view.main.MainActivity


class BankDetailsFragment : Fragment(){

    private var user: PreferencesData.Users? = null
    private lateinit var mBankPresenter: BankPresenter

    //layout
    private lateinit var layoutButtonAdd: LinearLayout
    private lateinit var layoutDetails: LinearLayout

    //text view
    private lateinit var textView17: TextView
    private lateinit var textView18: TextView
    private lateinit var textView19: TextView


    override fun onResume() {
        super.onResume()
        showData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_bank_details, container, false)
        user = PreferencesData.user(activity!!)
        mBankPresenter = BankPresenter()
        manageToolbar()
        initView(root)

        return root
    }

    private fun initView(root: View) {
        layoutDetails = root.findViewById(R.id.layoutDetails)
        layoutButtonAdd = root.findViewById(R.id.layoutButtonAdd)
        textView17 = root.findViewById(R.id.textView17)
        textView18 = root.findViewById(R.id.textView18)
        textView19 = root.findViewById(R.id.textView19)

        layoutButtonAdd.setOnClickListener {
            val detail: BankAddFragment? =
                activity!!.fragmentManager
                    .findFragmentById(R.id.fragment_bank_add) as BankAddFragment?

            if (detail == null) {
                val newFragment = BankAddFragment()
                // newFragment.arguments = bundle
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
        }

    }



    private fun showData() {
        mBankPresenter.getBankDetail(
            user?.T_id.toString(),
            object : BankPresenter.Response {
                @SuppressLint("SetTextI18n")
                override fun value(c: BankDetailsResponse) {
                    Log.d("A8sd1asda", c.isSuccessful.toString())
                    if (c.isSuccessful) {
                        layoutDetails?.visibility = View.VISIBLE
                        layoutButtonAdd?.visibility = View.GONE

                        textView17?.text = "ธนาคาร: " + c.data!![0].bank_name
                        textView18?.text = "เลขบัญชี: " + c.data[0].bank_number.toString()
                        textView19?.text = "ชื่อบัญชี: " + c.data[0].bank_name_account
                    } else {
                        layoutDetails?.visibility = View.GONE
                        layoutButtonAdd?.visibility = View.VISIBLE
                    }
                }

                override fun error(c: String?) {

                }
            })
    }

    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        tvTitle.text = "ข้อมูลบัญชีธนาคาร"
        back.visibility = View.VISIBLE

        back.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
    }



}