package com.example.tutorchinese.ui.view.register

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.tutorchinese.ui.data.api.DataModule
import com.example.tutorchinese.ui.data.response.RegisterResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.concurrent.TimeUnit

class RegisterPresenter {
    @SuppressLint("CheckResult")
    fun sendDataToServer(
        context: Context,
        username: String,
        password: String,
        name: String,
        lastName: String,
        email: String,
        birthDay: String,
        tel: String,
        res: (Boolean, String) -> Unit
    ) {
        try {

            val conTactArray = JSONArray()
            val root = JSONObject()
            val contact = JSONObject()

            contact.put("username", username)
            contact.put("password", password)
            contact.put("name", name)
            contact.put("last_name", lastName)
            contact.put("email", email)
            contact.put("birth_day", birthDay)
            contact.put("tel", tel)

            conTactArray.put(0, contact)
            root.put("data", conTactArray)

            Log.d("TAG", conTactArray[0].toString())

            val rootToString: String = root.toString()
            val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                rootToString
            )

            DataModule.instance()!!.register(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableObserver<RegisterResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: RegisterResponse) {
                        if (t.isSuccessful) {
                            res.invoke(true, t.message.toString())
                        } else {
                            res.invoke(false, t.message.toString())
                        }
                    }

                    @SuppressLint("DefaultLocale")
                    override fun onError(e: Throwable) {
                        Log.d("TAG", e.message.toString() + "2")

                    }
                })

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}