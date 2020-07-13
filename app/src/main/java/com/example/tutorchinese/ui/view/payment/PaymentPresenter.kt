package com.example.tutorchinese.ui.view.payment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import com.example.tutorchinese.ui.data.api.DataModule
import com.example.tutorchinese.ui.data.body.UploadImageEditBank
import com.example.tutorchinese.ui.data.body.UploadOrderDetailsBody
import com.example.tutorchinese.ui.data.response.ImageReturn
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.TimeUnit

class PaymentPresenter {
    @SuppressLint("CheckResult")
    fun setOrderDetails(
        U_id: String,
        Cr_id: String,
        O_date: String,
        O_time: String,
        O_price: String,
        O_bank: String,
        O_bank_num: String,
        O_image: String,
        res: (Boolean) -> Unit
    ) {
        val encodedImagePic1: String
        val uploadImage = ArrayList<UploadOrderDetailsBody.Data>()

        if (O_image != "") {
            val file = File(O_image)
            if (file.absolutePath != "") {

                val myBitmap = BitmapFactory.decodeFile(file.absolutePath)
                Log.d("ASd6asd", file.absolutePath)
                if (myBitmap != null) {
                    val byteArrayOutputStream =
                        ByteArrayOutputStream()
                    myBitmap.compress(
                        Bitmap.CompressFormat.JPEG,
                        70,
                        byteArrayOutputStream
                    )
                    val byteArrayImage =
                        byteArrayOutputStream.toByteArray()
                    encodedImagePic1 = Base64.encodeToString(
                        byteArrayImage,
                        Base64.DEFAULT
                    )

                    val uploadData = UploadOrderDetailsBody.Data(
                        U_id,
                        Cr_id,
                        file.name,
                        O_date,
                        O_time,
                        O_price,
                        O_bank,
                        O_bank_num,
                        "data:image/jpeg;base64,$encodedImagePic1"
                    )
                    uploadImage.add(uploadData)
                }

            }

        }
        DataModule.instance()!!
            .setOrderDetails(UploadOrderDetailsBody(uploadImage))
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ImageReturn>() {
                override fun onComplete() {

                }

                override fun onNext(t: ImageReturn) {
                    if (t.isSuccessful) {
                        res.invoke(true)
                        Log.d("As85das1d", t.message)
                    } else {
                        Log.d("As85das1d", t.message)
                        res.invoke(false)
                    }
                }

                @SuppressLint("DefaultLocale")
                override fun onError(e: Throwable) {
                    res.invoke(false)
                    Log.d("As85das1d", e.message)
                }
            })

    }
}