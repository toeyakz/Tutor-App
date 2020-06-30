package com.example.tutorchinese.ui.view.tutor.course

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.tutorchinese.ui.data.api.DataModule
import com.example.tutorchinese.ui.data.response.CourseResponse
import com.example.tutorchinese.ui.data.response.DeleteCourseResponse
import com.example.tutorchinese.ui.data.response.RegisterResponse
import com.example.tutorchinese.ui.data.response.UpdateCourseResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
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

    @SuppressLint("CheckResult")
    fun deleteCourse(T_id: Int?, Cr_id: String?, function: (Boolean, String) -> Unit) {

        try {
            DataModule.instance()!!.deleteCourse(T_id.toString(), Cr_id!!)
                .subscribeOn(Schedulers.io())
                .timeout(20, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<DeleteCourseResponse>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: DeleteCourseResponse) {
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

    @SuppressLint("CheckResult")
    fun updateCourse(tutor_id: Int, h: HashMap<String, String>, function: (Boolean, String) -> Unit) {
        try {

            val conTactArray = JSONArray()
            val root = JSONObject()
            val contact = JSONObject()

            contact.put("id_tutor", tutor_id.toString())
            contact.put("course_id", h["Cr_id"])
            contact.put("course_name", h["Cr_name"])
            contact.put("course_price", h["Cr_price"])
            contact.put("course_detail", h["Cr_info"])

            conTactArray.put(0, contact)
            root.put("data", conTactArray)

            Log.d("HomePresenter", root.toString())

            val rootToString: String = root.toString()
            val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                rootToString
            )

            DataModule.instance()!!.updateCourse(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableObserver<UpdateCourseResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: UpdateCourseResponse) {
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

}