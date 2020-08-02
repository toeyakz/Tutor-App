package com.example.tutorchinese.ui.view.profile

import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import com.example.tutorchinese.ui.controler.Constants
import com.example.tutorchinese.ui.data.api.DataModule
import com.example.tutorchinese.ui.data.response.BankDetailsResponse
import com.example.tutorchinese.ui.data.response.CountNotiResponse
import com.example.tutorchinese.ui.view.course.course_main.HomePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ProfilePresenter {
    interface Response{
        interface CountNoti {
            fun value(c: CountNotiResponse)
            fun error(c: String?)
        }
    }


    fun logout(activity: Context, res: (Boolean) -> Unit) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("ออกจากระบบ")
            .setMessage("คุณณต้องการออกจากระบบหรือไม่?")
            .setPositiveButton(
                "ออกจากระบบ"
            ) { _, _ ->
                val preferences: SharedPreferences = activity.getSharedPreferences(
                    Constants.PREFERENCES_USER,
                    Context.MODE_PRIVATE
                )
                preferences.edit().putBoolean(Constants.SESSION, false).apply()
                preferences.edit().clear().apply()

                res.invoke(true)
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                res.invoke(false)
            }
            .show()
    }


    @SuppressLint("CheckResult")
    fun getCountNoti(tutor_id: String, response: Response.CountNoti) {
        DataModule.instance()!!.getCountNoti(tutor_id)
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<CountNotiResponse>() {
                override fun onComplete() {
                }

                override fun onNext(t: CountNotiResponse) {
                    response.value(t)
                }

                override fun onError(e: Throwable) {
                    response.error(e.message)
                }
            })
    }

}