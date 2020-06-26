package com.example.tutorchinese.ui.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.manage.PreferencesData
import com.example.tutorchinese.ui.view.main.MainActivity

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var supportFragmentManager: FragmentManager? = null
    private  var user : PreferencesData.Users? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        val textView2: TextView = root.findViewById(R.id.text_home2)
        val textView3: TextView = root.findViewById(R.id.text_home3)
        user = PreferencesData.user(activity!!)
        manageToolbar()
        supportFragmentManager = activity!!.supportFragmentManager




        when (val type = user?.type) {
            "user" -> {
                val username = user?.U_username
                textView.text = "ชื่อผู้ใช้: $username"
                textView2.text = "ประเภทของผู้ใช้: $type"
                textView3.text = "ล็อคอินสำเร็จ!"
            }
            "tutor" -> {
                val username = user?.T_username
                textView.text = "ชื่อผู้ใช้: $username"
                textView2.text = "ประเภทของผู้ใช้: $type"
                textView3.text = "ล็อคอินสำเร็จ!"
            }
            else -> {
                val username = user?.admin_username
                textView.text = "ชื่อผู้ใช้: $username"
                textView2.text = "ประเภทของผู้ใช้: $type"
                textView3.text = "ล็อคอินสำเร็จ!"
            }
        }



        return root
    }

    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        tvTitle!!.text = "Course"
        back!!.visibility = View.GONE
    }
}