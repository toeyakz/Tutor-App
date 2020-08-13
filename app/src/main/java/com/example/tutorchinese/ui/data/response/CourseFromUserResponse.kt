package com.example.tutorchinese.ui.data.response

import com.example.tutorchinese.ui.data.entities.*

data class CourseFromUserResponse(
    val isSuccessful: Boolean,
    val data: List<CourseFromUser>?
)

data class TutorOnlyResponse(
    val isSuccessful: Boolean,
    val data: List<Tutor>?
)

data class UserOnlyResponse(
    val isSuccessful: Boolean,
    val data: List<UserOnly>?
)