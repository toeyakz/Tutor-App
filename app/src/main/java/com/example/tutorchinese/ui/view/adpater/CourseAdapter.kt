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
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CourseAdapter(
    val context: Context,
    var item: ArrayList<Course>,
    private var mOnClickList: (HashMap<String, String>, Boolean) -> (Unit)
   // private val mCloseLoadPresenter: CloseLoadPresenter
) :
    RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.item_couse,
                viewGroup,
                false
            )
        )
    }


    override fun getItemCount() = item.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {

        holder.tvNameCourse.text = item[i].Cr_name
        holder.tvPriceCourse.text = item[i].Cr_price


        holder.itemView.setOnClickListener {
            val myMap = HashMap<String, String>()
            myMap["Cr_id"] = item[i].Cr_id.toString()
            myMap["Cr_name"] = item[i].Cr_name.toString()
            myMap["Cr_price"] = item[i].Cr_price.toString()
            myMap["Cr_info"] = item[i].Cr_info.toString()
            myMap["Cr_data_time"] = item[i].Cr_data_time.toString()
            mOnClickList.invoke(myMap, true)
        }

        holder.itemView.setOnLongClickListener {
            val myMap = HashMap<String, String>()
            myMap["Cr_id"] = item[i].Cr_id.toString()
            myMap["Cr_name"] = item[i].Cr_name.toString()
            myMap["Cr_price"] = item[i].Cr_price.toString()
            myMap["Cr_info"] = item[i].Cr_info.toString()
            myMap["Cr_data_time"] = item[i].Cr_data_time.toString()
            mOnClickList.invoke(myMap, false)
            true
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNameCourse: TextView = itemView.findViewById(R.id.tvNameCourse)
        var tvPriceCourse: TextView = itemView.findViewById(R.id.tvPriceCourse)


    }

    @SuppressLint("SimpleDateFormat")
    private fun dateNewFormat(pattern: String): String? {
        var pattern: String? = pattern
        val pattern2 = "dd/MM/yyyy kk:mm"
        var spf = SimpleDateFormat("yyyy-MM-dd kk:mm:ss")
        var newDate: Date? = null
        try {
            newDate = spf.parse(pattern)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        spf = SimpleDateFormat(pattern2, Locale("th", "th"))
        pattern = spf.format(newDate)
        return pattern
    }
}