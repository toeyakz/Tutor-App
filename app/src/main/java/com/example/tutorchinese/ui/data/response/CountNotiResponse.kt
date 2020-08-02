package com.example.tutorchinese.ui.data.response

import com.example.tutorchinese.ui.data.entities.BankDetails
import com.example.tutorchinese.ui.data.entities.Course
import com.example.tutorchinese.ui.data.entities.User

data class CountNotiResponse(
    val isSuccessful: Boolean,
    val data: List<Data>?
)

data class Data(
    var count_noti: String? = null
)