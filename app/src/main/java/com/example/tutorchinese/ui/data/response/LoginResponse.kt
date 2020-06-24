package com.example.tutorchinese.ui.data.response

import com.example.tutorchinese.ui.data.entities.User

data class LoginResponse(
    val isSuccessful: Boolean,
    val message: String,
    val type: String,
    val data: List<User>?
)