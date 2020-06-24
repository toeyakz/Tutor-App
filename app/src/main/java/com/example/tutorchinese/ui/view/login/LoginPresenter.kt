package com.example.tutorchinese.ui.view.login

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.tutorchinese.ui.data.api.DataModule
import com.example.tutorchinese.ui.data.entities.User
import com.example.tutorchinese.ui.data.response.LoginResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class LoginPresenter {

    interface View{
         fun onSuccessService(user: List<User>?, type: String)
        fun onErrorService(error: String)
    }

    @SuppressLint("CheckResult")
    fun callLogin(context: Context, user: String, pass: String, view : View) {

        DataModule.instance()!!.login(user, pass)
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<LoginResponse>() {
                override fun onComplete() {
                }

                override fun onNext(t: LoginResponse) {
                    if (t.isSuccessful) {

                        view.onSuccessService(t.data, t.type)
                       /* userToSheared(context, t.data!!)
                        view?.onSuccessService(t.data)*/

                    } else {
                        when (t.message) {
                            "รหัสผ่านไม่ถูกต้อง" -> {
                                view.onErrorService("รหัสผ่านไม่ถูกต้อง")
                            }
                            "ไม่พบชื่อผู้ใช้" -> {
                                view.onErrorService("ไม่พบชื่อผู้ใช้")
                            }

                        }

                    }


                }

                override fun onError(e: Throwable) {
                    view.onErrorService(e.message.toString())
                    Log.d("s8ssa9c2d5asd", e.message)
                }
            })
    }

}