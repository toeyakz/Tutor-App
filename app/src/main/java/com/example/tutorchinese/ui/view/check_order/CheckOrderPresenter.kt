package com.example.tutorchinese.ui.view.check_order

import android.annotation.SuppressLint
import android.util.Log
import com.example.tutorchinese.ui.data.api.DataModule
import com.example.tutorchinese.ui.data.response.BankDetailsResponse
import com.example.tutorchinese.ui.data.response.CartByTutorResponse
import com.example.tutorchinese.ui.data.response.CourseResponse
import com.example.tutorchinese.ui.data.response.UpdateCourseResponse
import com.example.tutorchinese.ui.view.course.course_main.HomePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class CheckOrderPresenter {

    interface Response {
        interface CartByTutor {
            fun value(c: CartByTutorResponse?)
            fun error(c: String?)
        }
    }

    @SuppressLint("CheckResult")
    fun getBankDetail(tutor_id: String, response: Response.CartByTutor) {
        DataModule.instance()!!.getCartByTutor(tutor_id)
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<CartByTutorResponse>() {
                override fun onComplete() {
                }

                override fun onNext(t: CartByTutorResponse) {
                    response.value(t)
                }

                override fun onError(e: Throwable) {
                    response.error(e.message)
                }
            })
    }

    @SuppressLint("CheckResult")
    fun updateCourse(
        order_id: String,
        function: (Boolean, String) -> Unit
    ) {
        try {

            val conTactArray = JSONArray()
            val root = JSONObject()
            val contact = JSONObject()

            contact.put("order_id", order_id)

            conTactArray.put(0, contact)
            root.put("data", conTactArray)

            Log.d("HomePresenter", root.toString())

            val rootToString: String = root.toString()
            val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                rootToString
            )

            DataModule.instance()!!.updateOrders(body)
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