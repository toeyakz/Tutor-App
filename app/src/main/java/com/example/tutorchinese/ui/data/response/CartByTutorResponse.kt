package com.example.tutorchinese.ui.data.response

import com.example.tutorchinese.ui.data.entities.BankDetails
import com.example.tutorchinese.ui.data.entities.Course
import com.example.tutorchinese.ui.data.entities.User

data class CartByTutorResponse(
    val isSuccessful: Boolean,
    val data: List<CartByTutor>?
)

data class CartByTutor(
    var O_id: String?,
    var U_id: String?,
    var Cr_id: String?,
    var O_image: String?,
    var O_date: String?,
    var O_time: String?,
    var O_price: String?,
    var O_bank: String?,
    var O_bank_num: String?,
    var O_status: String?,
    var Cr_name: String?,
    var U_name: String?,
    var U_lastname: String?
)