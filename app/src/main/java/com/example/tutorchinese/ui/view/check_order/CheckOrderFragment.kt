package com.example.tutorchinese.ui.view.check_order

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.view.main.MainActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener


@Suppress("DEPRECATION")
class CheckOrderFragment : Fragment() {

    private lateinit var mCheckOrderPresenter: CheckOrderPresenter

    // tab
    private lateinit var  tabLayout : TabLayout
    private lateinit var  viewPager : ViewPager

    override fun onResume() {
        super.onResume()

        manageToolbar()
        setTab()
        Log.d("as36dasd", "ssssssss")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_check_order, container, false)
        initView(root)
        return root
    }

    @SuppressLint("SetTextI18n")
    private fun initView(root: View) {
      //  tvCourseName = root.findViewById(R.id.tvCourseName)
         tabLayout = root.findViewById(R.id.tabs) as TabLayout
         viewPager = root.findViewById(R.id.viewpager) as ViewPager

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            setTab()
        } else {
        }
    }



    private fun setTab(){
        tabLayout.addTab(tabLayout.newTab().setText("ยังไม่ตรวจสอบ"))
        tabLayout.addTab(tabLayout.newTab().setText("ตรวจสอบแล้ว"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL


        viewPager.adapter = PagerAdapter(fragmentManager, tabLayout.tabCount)
        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    inner class PagerAdapter(fm: FragmentManager?, var mNumOfTabs: Int) :
        FragmentStatePagerAdapter(fm!!) {
        override fun getItem(position: Int): Fragment {
            return if(position == 0) {
                CheckOrderNotConfirmFragment()
            }else{
                CheckOrderConfirmFragment()

            }
        }

        override fun getCount(): Int {
            return mNumOfTabs
        }

    }

    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        tvTitle.text = "ตรวจสอบการซื้อของลูกค้า"
        back.visibility = View.VISIBLE
        (activity as MainActivity?)!!.updateNavigationBarState(R.id.navigation_home)

        back.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
    }


}