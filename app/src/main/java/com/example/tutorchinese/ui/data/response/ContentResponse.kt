package com.example.tutorchinese.ui.data.response

import com.example.tutorchinese.ui.data.entities.Contents
import com.example.tutorchinese.ui.data.entities.Course
import com.example.tutorchinese.ui.data.entities.User

data class ContentResponse(
    val isSuccessful: Boolean,
    val data: List<Contents>?
)