package com.example.tutorchinese.ui.data.body

import java.util.*

class UploadOrderDetailsBody(var data: ArrayList<Data>) {

    class Data(
        U_id: String,
        Cr_id: String,
        O_image: String,
        O_date: String,
        O_time: String,
        O_price: String,
        O_bank: String,
        O_bank_num: String,
        img_base64: String
    ) {

        var U_id = ""
        var Cr_id = ""
        var O_image = ""
        var O_date = ""
        var O_time = ""
        var O_price = ""
        var O_bank = ""
        var O_bank_num = ""
        var img_base64 = ""

        init {
            this.U_id = U_id
            this.Cr_id = Cr_id
            this.O_image = O_image
            this.O_date = O_date
            this.O_time = O_time
            this.O_price = O_price
            this.O_bank = O_bank
            this.O_bank_num = O_bank_num
            this.img_base64 = img_base64

        }
    }

}


