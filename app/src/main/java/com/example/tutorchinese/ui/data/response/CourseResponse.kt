package com.example.tutorchinese.ui.data.response

import com.example.tutorchinese.ui.data.entities.Course
import com.example.tutorchinese.ui.data.entities.User

data class CourseResponse(
    val isSuccessful: Boolean,
    val data: List<Course>?
)