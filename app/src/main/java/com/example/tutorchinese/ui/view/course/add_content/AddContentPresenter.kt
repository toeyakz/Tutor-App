package com.example.tutorchinese.ui.view.course.add_content

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.tutorchinese.ui.data.api.DataModule
import com.example.tutorchinese.ui.data.response.AddContentResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class AddContentPresenter {

    @SuppressLint("CheckResult")
    fun sendDataCourseToServer(
        activity: Context?,
        edtContentNumber: String,
        edtContentName: String,
        edtContentDetail: String,
        course_id:String,
        res: (Boolean, String) -> Unit
    ) {

        try {

            val conTactArray = JSONArray()
            val root = JSONObject()
            val contact = JSONObject()

            contact.put("id_course", course_id)
            contact.put("content_chapter_number", edtContentNumber)
            contact.put("content_name", edtContentName)
            contact.put("content_info", edtContentDetail)


            conTactArray.put(0, contact)
            root.put("data", conTactArray)

            Log.d("AddContentPresenter", root.toString())

            val rootToString: String = root.toString()
            val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                rootToString
            )


            DataModule.instance()!!.addContent(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableObserver<AddContentResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: AddContentResponse) {
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
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}