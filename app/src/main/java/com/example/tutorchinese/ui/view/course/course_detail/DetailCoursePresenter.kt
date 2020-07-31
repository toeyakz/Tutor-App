package com.example.tutorchinese.ui.view.course.course_detail

import android.annotation.SuppressLint
import android.util.Log
import com.example.tutorchinese.ui.data.api.DataModule
import com.example.tutorchinese.ui.data.response.*
import com.example.tutorchinese.ui.view.course.course_main.HomePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit


class DetailCoursePresenter {
    interface Response {

        interface BankDetail {
            fun value(c: BankDetailsResponse)
            fun error(c: String?)
        }

        fun value(c: ContentResponse)
        fun error(c: String?)


    }

    @SuppressLint("CheckResult")
    fun contentData(courseId: String, response: Response) {
        DataModule.instance()!!.getContent(courseId)
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ContentResponse>() {
                override fun onComplete() {
                }

                override fun onNext(t: ContentResponse) {
                    response.value(t)
                }

                override fun onError(e: Throwable) {
                    response.error(e.message!!)
                }
            })
    }

    @SuppressLint("CheckResult")
    fun getBankDetail(tutor_id: String, response: Response.BankDetail) {
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
                    response.error(e.message)
                }
            })
    }


    @SuppressLint("CheckResult")
    fun updateCourse(course_id: String, h: HashMap<String, String>, function: (Boolean, String) -> Unit) {
        try {

            val conTactArray = JSONArray()
            val root = JSONObject()
            val contact = JSONObject()
            Log.d("asd4as", course_id+"/"+  h["Co_id"]+"/"+h["Co_info"]+"/"+h["Co_name"]+"/"+h["Co_chapter_number"])

            contact.put("course_id", course_id)
            contact.put("content_id", h["Co_id"])
            contact.put("content_detail", h["Co_info"])
            contact.put("content_name", h["Co_name"])
            contact.put("content_number", h["Co_chapter_number"])

            conTactArray.put(0, contact)
            root.put("data", conTactArray)

            Log.d("DetailCoursePresenter", root.toString())

            val rootToString: String = root.toString()
            val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                rootToString
            )

            DataModule.instance()!!.updateContent(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableObserver<UpdateContentResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: UpdateContentResponse) {
                        Log.d("d7s2dfg9sf", t.isSuccessful.toString())
                        if (t.isSuccessful) {
                            function.invoke(true, t.message.toString())
                        } else {
                            function.invoke(false, t.message.toString())
                        }
                    }

                    @SuppressLint("DefaultLocale")
                    override fun onError(e: Throwable) {
                        function.invoke(false, e.message.toString())
                        Log.d("as98a6sasc", e.message.toString() + "2")

                    }
                })



        } catch (e: Exception) {
            function.invoke(false, e.message.toString())
            // function.invoke(false, e.message!!)
            e.printStackTrace()
        }
    }

    @SuppressLint("CheckResult")
    fun deleteCourse(content_id: String, function: (Boolean, String) -> Unit) {
        try {
            DataModule.instance()!!.deleteContent(content_id)
                .subscribeOn(Schedulers.io())
                .timeout(20, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<DeleteContentResponse>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: DeleteContentResponse) {
                        function.invoke(true, t.message!!)
                    }

                    override fun onError(e: Throwable) {
                        function.invoke(false, e.message!!)
                    }
                })
        } catch (e: Exception) {
            function.invoke(false, e.message!!)
            e.printStackTrace()
        }
    }
}