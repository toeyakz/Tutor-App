package com.example.tutorchinese.ui.view.home

import android.annotation.SuppressLint
import com.example.tutorchinese.ui.data.api.DataModule
import com.example.tutorchinese.ui.data.response.CourseResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class HomePresenter {
    interface Response {
        fun value(c: CourseResponse?)
        fun error(c: String?)
    }

    @SuppressLint("CheckResult")
    fun courseData(id_: Int, res: Response) {

        DataModule.instance()!!.getCourse(id_.toString())
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<CourseResponse>() {
                override fun onComplete() {
                }

                override fun onNext(t: CourseResponse) {
                    res.value(t)
                }

                override fun onError(e: Throwable) {
                    res.error(e.message!!)
                }
            })
    }
}