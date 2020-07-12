package com.example.tutorchinese.ui.data.body

import java.util.*

class UploadImageEditBank(var data: ArrayList<Data>) {

    class Data(
        T_id: String,
        bank_id: String,
        bankName: String,
        bankNumber: String,
        accountName: String,
        name_image: String,
        img_base64: String
    ) {

        var T_id = ""
        var bank_id = ""
        var bankName = ""
        var bankNumber = ""
        var accountName = ""
        var name_image = ""
        var img_base64 = ""

        init {
            this.T_id = T_id
            this.bank_id = bank_id
            this.bankName = bankName
            this.bankNumber = bankNumber
            this.accountName = accountName
            this.name_image = name_image
            this.img_base64 = img_base64

        }
    }

}


