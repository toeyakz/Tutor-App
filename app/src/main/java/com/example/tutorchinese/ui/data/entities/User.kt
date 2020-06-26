package com.example.tutorchinese.ui.data.entities

data class User (
    val session: Boolean? = false,
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