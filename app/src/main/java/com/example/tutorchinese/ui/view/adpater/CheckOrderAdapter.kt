package com.example.tutorchinese.ui.view.adpater

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorchinese.R
import com.example.tutorchinese.ui.data.entities.Contents
import com.example.tutorchinese.ui.data.entities.Course
import com.example.tutorchinese.ui.data.response.CartByTutor
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CheckOrderAdapter(
    val context: Context,
    var item: ArrayList<CartByTutor>,
    private var mOnClickList: (HashMap<String, String>, Boolean) -> (Unit)
    // private val mCloseLoadPresenter: CloseLoadPresenter
) :
    RecyclerView.Adapter<CheckOrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.layout_check_order_item,
                viewGroup,
                false
            )
        )
    }


    override fun getItemCount() = item.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {



        holder.tvCourse.text = item[i].Cr_name
        holder.tvPrice.text = item[i].O_price
        holder.tvDateTime.text = "วันที่โอนเงิน :" + item[i].O_date + " " + item[i].O_time

        if (item[i].O_status == "1") {
            holder.imgStatus.setColorFilter(
                ContextCompat.getColor(
                    context,
                    R.color.colorSubAccent2
                )
            )
            holder.tvCourse.text = item[i].U_name + " " + item[i].U_lastname
        }




        holder.itemView.setOnClickListener {
            val myMap = HashMap<String, String>()
            myMap["Co_id"] = item[i].Cr_id.toString()
            myMap["Cr_name"] = item[i].Cr_name.toString()
            myMap["O_bank"] = item[i].O_bank.toString()
            myMap["O_bank_num"] = item[i].O_bank_num.toString()
            myMap["O_date"] = item[i].O_date.toString()
            myMap["O_id"] = item[i].O_id.toString()
            myMap["O_image"] = item[i].O_image.toString()
            myMap["O_price"] = item[i].O_price.toString()
            myMap["O_status"] = item[i].O_status.toString()
            myMap["O_time"] = item[i].O_time.toString()
            myMap["U_id"] = item[i].U_id.toString()
            myMap["U_lastname"] = item[i].U_lastname.toString()
            myMap["U_name"] = item[i].U_name.toString()
            mOnClickList.invoke(myMap, true)
        }

        /*holder.itemView.setOnLongClickListener {
            val myMap = HashMap<String, String>()
            myMap["Co_id"] = item[i].Co_id.toString()
            myMap["Cr_id"] = item[i].Cr_id.toString()
            myMap["Co_name"] = item[i].Co_name.toString()
            myMap["Co_info"] = item[i].Co_info.toString()
            myMap["Co_chapter_number"] = item[i].Co_chapter_number.toString()
            mOnClickList.invoke(myMap, false)
            true
        }*/

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCourse: TextView = itemView.findViewById(R.id.tvCourse)
        var tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        var tvDateTime: TextView = itemView.findViewById(R.id.tvDateTime)
        var imgStatus: ImageView = itemView.findViewById(R.id.imgStatus)
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