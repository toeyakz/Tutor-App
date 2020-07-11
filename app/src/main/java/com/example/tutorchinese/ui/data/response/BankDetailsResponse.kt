package com.example.tutorchinese.ui.data.response

import com.example.tutorchinese.ui.data.entities.BankDetails
import com.example.tutorchinese.ui.data.entities.Course
import com.example.tutorchinese.ui.data.entities.User

data class BankDetailsResponse(
    val isSuccessful: Boolean,
    val data: List<BankDetails>?
)