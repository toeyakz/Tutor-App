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
import com.example.tutorchinese.ui.data.entities.Tutor
import com.example.tutorchinese.ui.data.entities.UserOnly
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UserAdapter(
    val context: Context,
    var item: ArrayList<UserOnly>
) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.item_user,
                viewGroup,
                false
            )
        )
    }


    override fun getItemCount() = item.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {

        holder.tvName.text = item[i].U_name + " " + item[i].U_lastname
        holder.tvUsername.text = "ชื่อผู้ใช่: " + item[i].U_username
        holder.tvEmail.text = "อีเมล: " + item[i].U_email
        holder.tvTel.text = "เบอร์โทร: " + item[i].U_tel


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tvName)
        var tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        var tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        var tvTel: TextView = itemView.findViewById(R.id.tvTel)


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