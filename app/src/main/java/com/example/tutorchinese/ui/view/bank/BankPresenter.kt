package com.example.tutorchinese.ui.view.bank

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import com.example.tutorchinese.ui.controler.Utils
import com.example.tutorchinese.ui.data.api.DataModule
import com.example.tutorchinese.ui.data.body.UploadImageBank
import com.example.tutorchinese.ui.data.response.BankDetailsResponse
import com.example.tutorchinese.ui.data.response.ImageReturn
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.math.log

class BankPresenter {
    interface Response {
        fun value(c: BankDetailsResponse)
        fun error(c: String?)
    }

    @SuppressLint("CheckResult")
    fun getBankDetail(tutor_id: String, response: Response) {
        DataModule.instance()!!.getBankDetail(tutor_id)
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<BankDetailsResponse>() {
                override fun onComplete() {
                }

                override fun onNext(t: BankDetailsResponse) {
                    response.value(t)
                }

                override fun onError(e: Throwable) {
                    response.error(e.message!!)
                }
            })
    }

    @SuppressLint("CheckResult")
    fun upLoadBankDetails(
        T_id:String,
        file: File,
        bankName: String,
        bankNumber: String,
        accountName: String
    ) {
        val encodedImagePic1: String
        val uploadImage = ArrayList<UploadImageBank.Data>()

        if (file.absolutePath != "") {
            val myBitmap = BitmapFactory.decodeFile(file.absolutePath)

            if (myBitmap != null) {
                Log.d("ASd6asd", myBitmap.toString())
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

                val uploadData = UploadImageBank.Data(
                    T_id,
                    bankName,
                    bankNumber,
                    accountName,
                    file.name,
                    "data:image/jpeg;base64,$encodedImagePic1"
                )
                uploadImage.add(uploadData)
            }
      /*      val json: String = Utils().getGson()!!.toJson(uploadImage)
            Log.d("a9a20as8da", json)*/

            DataModule.instance()!!
                .upLoadImage(UploadImageBank(uploadImage))
                .subscribeOn(Schedulers.io())
                .timeout(20, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ImageReturn>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: ImageReturn) {
                       if(t.isSuccessful){
                           Log.d("As85das1d", t.message)
                       }
                    }

                    @SuppressLint("DefaultLocale")
                    override fun onError(e: Throwable) {

                    }
                })


        }


    }
}