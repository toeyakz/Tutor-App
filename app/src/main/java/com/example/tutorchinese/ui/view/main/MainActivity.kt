package com.example.tutorchinese.ui.view.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.controler.Constants.Companion.HOME
import com.example.tutorchinese.ui.controler.Constants.Companion.PROFILE
import com.example.tutorchinese.ui.controler.PreferencesData
import com.example.tutorchinese.ui.view.course.course_main.HomeFragment
import com.example.tutorchinese.ui.view.dashboard.DashboardFragment
import com.example.tutorchinese.ui.view.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import java.util.*


@Suppress("DEPRECATION", "DEPRECATED_IDENTITY_EQUALS", "UNREACHABLE_CODE")
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val SELECTED_ITEM = "arg_selected_item"
    private var homeFragment: HomeFragment? = null
    private var dashboardFragment: DashboardFragment? = null
    private var profileFragment: ProfileFragment? = null
    private var navView: BottomNavigationView? = null
    private var mSelectedItem = 0

    private var user: PreferencesData.Users? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        user = PreferencesData.user(applicationContext!!)
        //val navView: BottomNavigationView = findViewById(R.id.nav_view)
        //val navController = findNavController(R.id.nav_host_fragment)
        //   navView.setupWithNavController(navController)
        /* val host = NavHostFragment.create(R.navigation.mobile_navigation)
         supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment,host).setPrimaryNavigationFragment(host).commit()*/
        // navController.navigate()
        homeFragment = HomeFragment()
        dashboardFragment = DashboardFragment()
        profileFragment = ProfileFragment()

        navView = findViewById(R.id.nav_view)
        navView!!.setOnNavigationItemSelectedListener(this)
        navView!!.selectedItemId = R.id.navigation_home


/*        val fragmentManager: FragmentManager = fragmentManager
        val fragmentTransaction =
            fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.navigation_view, fragment)

        fragmentTransaction.commit()*/

        setToolbar()

    }

    fun updateNavigationBarState(actionId: Int) {
        val menu: Menu = nav_view.menu
        var i = 0
        val size = menu.size()
        while (i < size) {
            val item = menu.getItem(i)
            if (item.itemId == actionId) {
                item.isChecked = true
            }
            i++
        }
    }

    fun getTvTitle(): TextView {
        return text_toolbar
    }

    fun getBack(): ImageView {
        return back
    }

    fun getNav(): BottomNavigationView {
        return nav_view
    }


    @SuppressLint("SetTextI18n")
    private fun setToolbar() {
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayShowCustomEnabled(false)
    }

    override fun onBackPressed() {

        Log.d("asd5asda", supportFragmentManager.backStackEntryCount.toString())

        val isFragmentPopped = handleNestedFragmentBackStack(supportFragmentManager)


        if (!isFragmentPopped && supportFragmentManager.backStackEntryCount == 2) {
            // navView!!.selectedItemId = R.id.navigation_home
        } else if (!isFragmentPopped && supportFragmentManager.backStackEntryCount <= 1) {
            finish()
        } else if (!isFragmentPopped) {
            super.onBackPressed()
        }
    }


    private fun handleNestedFragmentBackStack(fragmentManager: FragmentManager): Boolean {
        val childFragmentList = fragmentManager.fragments

        if (childFragmentList.size > 0) {
            for (index in childFragmentList.size - 1 downTo 0) {
                val fragment = childFragmentList[index]
                val isPopped = handleNestedFragmentBackStack(fragment.childFragmentManager)
                return when {
                    isPopped -> true
                    fragmentManager.backStackEntryCount > 1 -> {

                        fragmentManager.popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }
        return false
    }



    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.navigation_home -> {
                //  selectFragment(homeFragment!!)
                //  viewFragment(HomeFragment(), "FRAGMENT_HOME")
                val fm = supportFragmentManager
                for (entry in 0 until fm.backStackEntryCount) {
                    if (entry > 0) {
                        fm.popBackStack()
                    }
                }

                mSelectedItem = 99
                if (homeFragment == null) {
                    val newFragment = HomeFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.navigation_view, newFragment, HOME)
                        .addToBackStack(null)
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.navigation_view, homeFragment!!, HOME)
                        .addToBackStack(null)
                        .commit()
                }
                return true
            }
            /*R.id.navigation_dashboard -> {
                mSelectedItem = 0
                // selectFragment(dashboardFragment!!)
                if (dashboardFragment == null) {
                    val newFragment = DashboardFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.navigation_view, newFragment, DASHBOARD)
                        .addToBackStack(null)
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.navigation_view, dashboardFragment!!, DASHBOARD)
                        .addToBackStack(null)
                        .commit()
                }
                return true
            }*/
            R.id.navigation_profile -> {
                //   viewFragment(ProfileFragment(), "FRAGMENT_OTHER")
                mSelectedItem = 0
                //  selectFragment(profileFragment!!)
                if (profileFragment == null) {
                    val newFragment = ProfileFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.navigation_view, newFragment, PROFILE)
                        .addToBackStack(null)
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.navigation_view, profileFragment!!, PROFILE)
                        .addToBackStack(null)
                        .commit()
                }
                return true
            }
        }
        return false
    }

    /*  private fun viewFragment(fragment: Fragment, name: String) {
          val fragmentTransaction =
              supportFragmentManager.beginTransaction().replace(R.id.navigation_view, fragment)
                  .addToBackStack(null)
          // 1. Know how many fragments there are in the stack
          val count = fragmentManager.backStackEntryCount
          // 2. If the fragment is **not** "home type", save it to the stack
         *//* if (name == "FRAGMENT_OTHER") {
            fragmentTransaction.addToBackStack(name)
        }*//*
        // Commit !
        fragmentTransaction.commit()
        // 3. After the commit, if the fragment is not an "home type" the back stack is changed, triggering the
        // OnBackStackChanged callback
        fragmentManager.addOnBackStackChangedListener {
            // If the stack decreases it means I clicked the back button
            if (fragmentManager.backStackEntryCount <= count) {
                // pop all the fragment and remove the listener
                *//* fragmentManager.popBackStack("FRAGMENT_OTHER", POP_BACK_STACK_INCLUSIVE)
                            fragmentManager.removeOnBackStackChangedListener(this)*//*
                // set the home button selected
                navView?.menu?.getItem(0)?.isChecked = true
            }
        }
    }*/


}

