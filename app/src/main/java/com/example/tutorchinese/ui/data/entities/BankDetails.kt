package com.example.tutorchinese.ui.data.entities

data class BankDetails (
    var bank_id: Int?,
    var T_id: Int?,
    var bank_number: Int? = null,
    var bank_name: String? = null,
    var bank_name_account: String? = null,
    var bank_qr: String? = null
)