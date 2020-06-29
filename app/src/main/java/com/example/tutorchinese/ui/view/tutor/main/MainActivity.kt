package com.example.tutorchinese.ui.view.tutor.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.manage.Constants.Companion.DASHBOARD
import com.example.tutorchinese.ui.manage.Constants.Companion.HOME
import com.example.tutorchinese.ui.manage.Constants.Companion.PROFILE
import com.example.tutorchinese.ui.view.tutor.course.HomeFragment
import com.example.tutorchinese.ui.view.tutor.dashboard.DashboardFragment
import com.example.tutorchinese.ui.view.tutor.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import java.util.*


@Suppress("DEPRECATION", "DEPRECATED_IDENTITY_EQUALS")
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val SELECTED_ITEM = "arg_selected_item"
    private var homeFragment: HomeFragment? = null
    private var dashboardFragment: DashboardFragment? = null
    private var profileFragment: ProfileFragment? = null
    private var navView: BottomNavigationView? = null
    private var mSelectedItem = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

        setToolbar()

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
        val homeItem: MenuItem = navView!!.menu.getItem(0)
        if (mSelectedItem !== homeItem.itemId) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.navigation_view, homeFragment!!, HOME)
                .addToBackStack(null)
                .commit()
            // Select home item
            navView!!.selectedItemId = homeItem.itemId
        } else {
            super.onBackPressed()
        }
    }



    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.navigation_home -> {
              //  selectFragment(homeFragment!!)
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
            R.id.navigation_dashboard -> {
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
            }
            R.id.navigation_profile -> {
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


}

