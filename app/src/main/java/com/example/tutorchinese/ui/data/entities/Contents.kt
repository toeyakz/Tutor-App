package com.example.tutorchinese.ui.data.entities

data class Contents (
    val Co_id: Int?,
    var Cr_id: Int?,
    var Co_chapter_number: Int?,
    var Co_name: String? = null,
    var Co_info: String? = null,
    var Co_file: String? = null,
    var Co_link: String? = null
)