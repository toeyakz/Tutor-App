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
import com.example.tutorchinese.ui.data.entities.Contents
import com.example.tutorchinese.ui.data.entities.Course
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ContentAdapter(
    val context: Context,
    var item: ArrayList<Contents>,
    private var mOnClickList: (HashMap<String, String>, Boolean) -> (Unit)
   // private val mCloseLoadPresenter: CloseLoadPresenter
) :
    RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.layout_content_item,
                viewGroup,
                false
            )
        )
    }


    override fun getItemCount() = item.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {

        holder.tvNameContent.text = item[i].Co_name
        holder.tvChapterContent.text = item[i].Co_chapter_number.toString()

        holder.itemView.setOnClickListener {
            val myMap = HashMap<String, String>()
            myMap["Co_id"] = item[i].Co_id.toString()
            myMap["Cr_id"] = item[i].Cr_id.toString()
            myMap["Co_name"] = item[i].Co_name.toString()
            myMap["Co_info"] = item[i].Co_info.toString()
            myMap["Co_chapter_number"] = item[i].Co_chapter_number.toString()
            mOnClickList.invoke(myMap, true)
        }

        holder.itemView.setOnLongClickListener {
            val myMap = HashMap<String, String>()
            myMap["Co_id"] = item[i].Co_id.toString()
            myMap["Cr_id"] = item[i].Cr_id.toString()
            myMap["Co_name"] = item[i].Co_name.toString()
            myMap["Co_info"] = item[i].Co_info.toString()
            myMap["Co_chapter_number"] = item[i].Co_chapter_number.toString()
            mOnClickList.invoke(myMap, false)
            true
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNameContent: TextView = itemView.findViewById(R.id.tvNameContent)
        var tvChapterContent: TextView = itemView.findViewById(R.id.tvChapterContent)
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