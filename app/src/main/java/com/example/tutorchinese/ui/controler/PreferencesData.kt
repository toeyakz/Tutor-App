package com.example.tutorchinese.ui.controler

import android.content.Context

class PreferencesData {
    companion object {
        fun user(context: Context): Users {
            val prefs =
                context.getSharedPreferences(Constants.PREFERENCES_USER, Context.MODE_PRIVATE)
            val session = prefs.getBoolean("session", false)
            val type = prefs.getString("type", "")
            val U_id = prefs.getInt("U_id", 0)
            val U_username = prefs.getString("U_username", "")
            val U_password = prefs.getString("U_password", "")
            val U_name = prefs.getString("U_name", "")
            val U_lastname = prefs.getString("U_lastname", "")
            val U_birth_day = prefs.getString("U_birth_day", "")
            val U_email = prefs.getString("U_email", "")
            val U_tel = prefs.getString("U_tel", "")
            val U_img = prefs.getString("U_img", "")
            //admin
            val admin_id = prefs.getInt("admin_id", 0)
            val admin_username = prefs.getString("admin_username", "")
            val admin_password = prefs.getString("admin_password", "")
            //tutor
            val T_id = prefs.getInt("T_id", 0)
            val T_username = prefs.getString("T_username", "")
            val T_password = prefs.getString("T_password", "")
            val T_name = prefs.getString("T_name", "")
            val T_lastname = prefs.getString("T_lastname", "")
            val T_date = prefs.getString("T_date", "")
            val T_education = prefs.getString("T_education", "")
            val T_experience = prefs.getString("T_experience", "")
            val T_expert = prefs.getString("T_expert", "")
            val T_course = prefs.getString("T_course", "")
            val T_address = prefs.getString("T_address", "")
            val T_email = prefs.getString("T_email", "")
            val T_tel = prefs.getString("T_tel", "")
            val T_img = prefs.getString("T_img", "")

            return Users(
                session,
                type,
                U_id,
                U_username,
                U_password,
                U_name,
                U_lastname
                ,
                U_birth_day,
                U_email,
                U_tel,
                U_img,
                admin_id,
                admin_username,
                admin_password
                ,
                T_id,
                T_username,
                T_password,
                T_name,
                T_lastname,
                T_date,
                T_education,
                T_experience,
                T_expert,
                T_course,
                T_address,
                T_email,
                T_tel,
                T_img
            )
        }


    }

    data class Users(
        val session: Boolean? = false,
        val type: String? = null,
        //user
        var U_id: Int? = null,
        var U_username: String? = null,
        var U_password: String? = null,
        var U_name: String? = null,
        var U_lastname: String? = null,
        var U_birth_day: String? = null,
        var U_email: String? = null,
        var U_tel: String? = null,
        var U_img: String? = null,
        //admin
        var admin_id: Int? = null,
        var admin_username: String? = null,
        var admin_password: String? = null,
        //tutor
        var T_id: Int? = null,
        var T_username: String? = null,
        var T_password: String? = null,
        var T_name: String? = null,
        var T_lastname: String? = null,
        var T_date: String? = null,
        var T_education: String? = null,
        var T_experience: String? = null,
        var T_expert: String? = null,
        var T_course: String? = null,
        var T_address: String? = null,
        var T_email: String? = null,
        var T_tel: String? = null,
        var T_img: String? = null
    )


}