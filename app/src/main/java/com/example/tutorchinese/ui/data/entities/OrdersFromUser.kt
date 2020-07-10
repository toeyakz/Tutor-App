package com.example.tutorchinese.ui.data.entities

data class OrdersFromUser (
    val O_id: Int?,
    var U_id: Int? ,
    var Cr_id: Int? ,
    var P_id: Int? ,
    var O_data_time: String? = null,
    var O_money: String? = null
)