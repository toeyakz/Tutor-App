package com.example.tutorchinese.ui.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tutorchinese.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

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


        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })

        val type = activity!!.intent.extras!!.getString("type")
        val username = activity!!.intent.extras!!.getString("username")
        textView.text = "ชื่อผู้ใช้: $username"
        textView2.text = "ประเภทของผู้ใช้: $type"
        textView3.text = "ล็อคอินสำเร็จ!"





        return root
    }
}