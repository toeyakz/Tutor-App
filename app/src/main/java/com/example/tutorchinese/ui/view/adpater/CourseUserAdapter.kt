package com.example.tutorchinese.ui.view.adpater

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.data.entities.Course
import com.example.tutorchinese.ui.data.entities.CourseFromUser
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CourseUserAdapter(
    val context: Context,
    var item: ArrayList<CourseFromUser>,
    private var mOnClickList: (HashMap<String, String>) -> (Unit)
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


        holder.itemView.setOnClickListener {
            val myMap = HashMap<String, String>()
            myMap["Cr_id"] = item[i].Cr_id.toString()
            myMap["Cr_name"] = item[i].Cr_name.toString()
            myMap["T_id"] = item[i].T_id.toString()
            myMap["T_name"] = item[i].T_name.toString()
            mOnClickList.invoke(myMap)
        }

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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNameCourse: TextView = itemView.findViewById(R.id.tvNameCourse)
        var tvNameTutor: TextView = itemView.findViewById(R.id.tvNameTutor)

    }

}