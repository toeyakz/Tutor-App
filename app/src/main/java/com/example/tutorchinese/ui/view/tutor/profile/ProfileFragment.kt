package com.example.tutorchinese.ui.view.tutor.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.manage.CustomProgressDialog
import com.example.tutorchinese.ui.manage.PreferencesData
import com.example.tutorchinese.ui.view.beginner.login.LoginActivity
import com.example.tutorchinese.ui.view.tutor.main.MainActivity

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
        manageToolbar()
        initView(root)
        user = PreferencesData.user(activity!!)


        return root
    }

    private fun initView(root: View) {
        mProfilePresenter = ProfilePresenter()

       // val textView: TextView = root.findViewById(R.id.text_notifications)
        val logout: TextView = root.findViewById(R.id.btnLogout)

        logout.setOnClickListener(this)

        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
           // textView.text = it
        })
    }

    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        tvTitle.text = "Profile"
        back.visibility = View.GONE
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
        }
    }


}