package com.example.tutorchinese.ui.view.adpater

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.data.entities.Course
import com.example.tutorchinese.ui.data.entities.CourseFromUser
import com.example.tutorchinese.ui.data.response.BankDetailsResponse
import com.example.tutorchinese.ui.data.response.CourseResponse
import com.example.tutorchinese.ui.data.response.OrdersFromUserResponse
import com.example.tutorchinese.ui.view.course.course_main.HomePresenter
import com.example.tutorchinese.ui.view.payment.PaymentInformationFragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CourseUserAdapter(
    val context: Context,
    var item: ArrayList<CourseFromUser>,
    var user_id: String,
    val mHomePresenter: HomePresenter,
    private var mOnClickList: (HashMap<String, String>, String) -> (Unit)
    // private val mCloseLoadPresenter: CloseLoadPresenter
) :
    RecyclerView.Adapter<CourseUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.item_couse_from_user,
                viewGroup,
                false
            )
        )
    }


    override fun getItemCount() = item.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {

        holder.tvNameCourse.text = item[i].Cr_name
        holder.tvNameTutor.text = item[i].T_name

        val myMap = HashMap<String, String>()
        myMap["Cr_id"] = item[i].Cr_id.toString()
        myMap["Cr_name"] = item[i].Cr_name.toString()
        myMap["T_id"] = item[i].T_id.toString()
        myMap["T_name"] = item[i].T_name.toString()

        mHomePresenter.getOrdersFromUser(
            user_id,
            item[i].Cr_id.toString(),
            object : HomePresenter.Response.OrdersUser {
                override fun value(c: OrdersFromUserResponse) {
                    //ซื้อแล้ว
                    if (c.isSuccessful) {
                        if (c.data!!.isNotEmpty()) {
                            if (c.data[0].O_status == 0) {
                                holder.tvStatus.visibility = View.VISIBLE
                                holder.tvStatus.text = "กำลังตรวจสอบ"
                                holder.tvStatus.setTextColor(Color.parseColor("#FFF4511E"))
                                holder.itemView.setOnClickListener {
                                    mOnClickList.invoke(myMap, "กำลังตรวจสอบ")
                                }
                            } else if (c.data[0].O_status == 1) {
                                holder.tvStatus.visibility = View.VISIBLE
                                holder.tvStatus.text = "ซื้อแล้ว"
                                holder.itemView.setOnClickListener {
                                    mOnClickList.invoke(myMap, "ซื้อแล้ว")
                                }
                            }
                        }
                        //ยังไม่ซื้อ
                    } else {
                        holder.tvStatus.visibility = View.GONE
                        holder.itemView.setOnClickListener {
                            mOnClickList.invoke(myMap, "ยังไม่ซื้อ")
                        }
                    }

                }

                override fun error(c: String?) {

                }

            })


        /*holder.itemView.setOnLongClickListener {
            val myMap = HashMap<String, String>()
            myMap["Cr_id"] = item[i].Cr_id.toString()
            myMap["Cr_name"] = item[i].Cr_name.toString()
            myMap["Cr_price"] = item[i].Cr_price.toString()
            myMap["Cr_info"] = item[i].Cr_info.toString()
            myMap["Cr_data_time"] = item[i].Cr_data_time.toString()
            mOnClickList.invoke(myMap, false)
            true
        }*/

    }
    fun filterList(list: ArrayList<CourseFromUser>) {
        item = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNameCourse: TextView = itemView.findViewById(R.id.tvNameCourse)
        var tvNameTutor: TextView = itemView.findViewById(R.id.tvNameTutor)
        var tvStatus: TextView = itemView.findViewById(R.id.tvStatus)

    }

}