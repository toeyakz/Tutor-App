package com.example.tutorchinese.ui.view.tutor.add_course

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.example.tutorchinese.ui.data.api.DataModule
import com.example.tutorchinese.ui.data.response.AddCourseResponse
import com.example.tutorchinese.ui.data.response.RegisterResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class AddCoursePresenter {

    @SuppressLint("CheckResult")
    fun sendDataCourseToServer(
        activity: Context?,
        edtCourseName: String,
        edtCoursePrice: String,
        edtCourseDetail: String,
        tutor_id:String,
        tutor_username:String,
        res: (Boolean, String) -> Unit
    ) {

        try {

            val conTactArray = JSONArray()
            val root = JSONObject()
            val contact = JSONObject()

            contact.put("username", tutor_username)
            contact.put("id_tutor", tutor_id)
            contact.put("course_detail", edtCourseDetail)
            contact.put("course_price", edtCoursePrice)
            contact.put("course_name", edtCourseName)


            conTactArray.put(0, contact)
            root.put("data", conTactArray)

            Log.d("AddCoursePresenter", root.toString())

            val rootToString: String = root.toString()
            val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                rootToString
            )


            DataModule.instance()!!.addCourse(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableObserver<AddCourseResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: AddCourseResponse) {
                        Log.d("d7s2dfg9sf", t.isSuccessful.toString())
                        if (t.isSuccessful) {
                            res.invoke(true, t.message.toString())
                        } else {
                            res.invoke(false, t.message.toString())
                        }
                    }

                    @SuppressLint("DefaultLocale")
                    override fun onError(e: Throwable) {
                        Log.d("as98a6sasc", e.message.toString() + "2")

                    }
                })
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}