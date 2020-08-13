package com.example.tutorchinese.ui.view.profile

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.data.entities.Course
import com.example.tutorchinese.ui.data.entities.Tutor
import com.example.tutorchinese.ui.data.entities.UserOnly
import com.example.tutorchinese.ui.data.response.TutorOnlyResponse
import com.example.tutorchinese.ui.data.response.UserOnlyResponse
import com.example.tutorchinese.ui.view.adpater.CourseAdapter
import com.example.tutorchinese.ui.view.adpater.TutorAdapter
import com.example.tutorchinese.ui.view.adpater.UserAdapter
import com.example.tutorchinese.ui.view.main.MainActivity
import com.google.android.material.tabs.TabLayout
import java.util.ArrayList


@Suppress("DEPRECATION")
class TutorAndUserFragment : Fragment() {


    private lateinit var mTutorAdapter: TutorAdapter
    private lateinit var mUserAdapter: UserAdapter
    private lateinit var mProfilePresenter: ProfilePresenter

    private var tvTutor : TextView? = null
    private var tvUser : TextView? = null

    private var bgTutor : LinearLayout? = null
    private var bgUser : LinearLayout? = null
    private var recyclerView : RecyclerView? = null


    private var selected = "tutor"



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_tutor_and_user, container, false)
        mProfilePresenter = ProfilePresenter()

        tvTutor = root.findViewById(R.id.tvTutor)
        tvUser = root.findViewById(R.id.tvUser)
        bgTutor = root.findViewById(R.id.bgTutor)
        bgUser = root.findViewById(R.id.bgUser)
        recyclerView = root.findViewById(R.id.recyclerView)

        if(selected == "tutor"){
            bgTutor!!.setBackgroundColor(Color.parseColor("#D32F2F"))
            bgUser!!.setBackgroundColor(Color.TRANSPARENT)
            mProfilePresenter.getTutor(object : ProfilePresenter.Response.TuTor {
                override fun value(c: TutorOnlyResponse) {
                    mTutorAdapter =
                        TutorAdapter(
                            activity!!,
                            c.data as ArrayList<Tutor>
                        )

                    recyclerView!!.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = mTutorAdapter
                        mTutorAdapter.notifyDataSetChanged()
                    }
                }

                override fun error(c: String?) {

                }
            })
        }



        tvTutor!!.setOnClickListener {
            bgTutor!!.setBackgroundColor(Color.parseColor("#D32F2F"))
            bgUser!!.setBackgroundColor(Color.TRANSPARENT)
            recyclerView!!.adapter = null
            mProfilePresenter.getTutor(object : ProfilePresenter.Response.TuTor {
                override fun value(c: TutorOnlyResponse) {
                    mTutorAdapter =
                        TutorAdapter(
                            activity!!,
                            c.data as ArrayList<Tutor>
                        )

                    recyclerView!!.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = mTutorAdapter
                        mTutorAdapter.notifyDataSetChanged()
                    }


                }

                override fun error(c: String?) {

                }
            })
        }

        tvUser!!.setOnClickListener {
            bgTutor!!.setBackgroundColor(Color.TRANSPARENT)
            bgUser!!.setBackgroundColor(Color.parseColor("#D32F2F"))
            recyclerView!!.adapter = null
            mProfilePresenter.getUserOnly(object : ProfilePresenter.Response.User {
                override fun value(c: UserOnlyResponse) {
                    mUserAdapter =
                        UserAdapter(
                            activity!!,
                            c.data as ArrayList<UserOnly>
                        )

                    recyclerView!!.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = mUserAdapter
                        mUserAdapter.notifyDataSetChanged()
                    }

                }

                override fun error(c: String?) {
                    TODO("Not yet implemented")
                }

            })

        }





       //tabLayout!!.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener)
        /*tabLayout!!.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    mProfilePresenter.getTutor(object : ProfilePresenter.Response.TuTor {
                        override fun value(c: TutorOnlyResponse) {
                            for (i in c.data!!) {
                                Log.d("asfdasf", i.T_name)
                            }
                        }

                        override fun error(c: String?) {

                        }
                    })
                } else {
                    mProfilePresenter.getUserOnly(object : ProfilePresenter.Response.User {
                        override fun value(c: UserOnlyResponse) {
                            for (i in c.data!!) {
                                Log.d("asfdasf", i.U_name)
                            }

                        }

                        override fun error(c: String?) {
                            TODO("Not yet implemented")
                        }

                    })
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })*/


        return root
    }

    override fun onResume() {
        super.onResume()
        manageToolbar()
    }

    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        val nav = (activity as MainActivity).getNav()
        tvTitle.text = "ติวเตอร์และผู้ใช้งาน"
        back.visibility = View.VISIBLE

        back.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
        //  (activity as MainActivity?)!!.updateNavigationBarState(R.id.navigation_profile)
    }


}