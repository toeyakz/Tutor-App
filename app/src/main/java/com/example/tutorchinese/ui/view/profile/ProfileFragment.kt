package com.example.tutorchinese.ui.view.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.controler.CustomProgressDialog
import com.example.tutorchinese.ui.controler.PreferencesData
import com.example.tutorchinese.ui.view.bank.BankDetailsFragment
import com.example.tutorchinese.ui.view.beginner.login.LoginActivity
import com.example.tutorchinese.ui.view.main.MainActivity

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var user: PreferencesData.Users? = null
    private lateinit var mProfilePresenter: ProfilePresenter
    private var dialog: CustomProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        user = PreferencesData.user(activity!!)
        manageToolbar()
        initView(root)



        return root
    }

    private fun initView(root: View) {
        mProfilePresenter = ProfilePresenter()

        // val textView: TextView = root.findViewById(R.id.text_notifications)
        val logout: LinearLayout = root.findViewById(R.id.btnLogout)
        val btnBank: LinearLayout = root.findViewById(R.id.btnBank)
        val linearLayout5: LinearLayout = root.findViewById(R.id.linearLayout5)

        logout.setOnClickListener(this)
        btnBank.setOnClickListener(this)

        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            // textView.text = it
        })
        if(user?.type == "user"){
            btnBank.visibility = View.GONE
            linearLayout5.visibility = View.GONE
        }else if(user?.type == "tutor"){
            btnBank.visibility = View.VISIBLE
            linearLayout5.visibility = View.VISIBLE
        }


    }

    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        val nav = (activity as MainActivity).getNav()
        tvTitle.text = "เมนู"
        back.visibility = View.GONE
        (activity as MainActivity?)!!.updateNavigationBarState(R.id.navigation_profile)
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.btnLogout -> {
                dialog = CustomProgressDialog(activity!!, "กำลังโหลด..")
                dialog?.show()
                mProfilePresenter.logout(activity!!) {
                    if (it) {
                        if (dialog?.isShowing!!) {
                            dialog?.dismiss()
                        }
                        val intent = Intent(activity, LoginActivity::class.java)
                        activity?.startActivity(intent)
                        activity?.finish()
                    } else {
                        if (dialog?.isShowing!!) {
                            dialog?.dismiss()
                        }
                    }
                }
            }
            R.id.btnBank -> {
                val detail: BankDetailsFragment? =
                    activity!!.fragmentManager
                        .findFragmentById(R.id.fragment_bank_detail) as BankDetailsFragment?

                if (detail == null) {
                    val newFragment = BankDetailsFragment()
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
    }


}