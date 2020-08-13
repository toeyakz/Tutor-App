package com.example.tutorchinese.ui.view.course.add_course

import android.annotation.SuppressLint
import android.content.Context
import android.util.Base64
import android.util.Base64OutputStream
import android.util.Log
import com.example.tutorchinese.ui.controler.UploadRequestBody
import com.example.tutorchinese.ui.controler.Utils
import com.example.tutorchinese.ui.data.api.DataModule
import com.example.tutorchinese.ui.data.response.AddCourseResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.*


class AddCoursePresenter {




    @SuppressLint("CheckResult")
    fun sendDataCourseToServer(
        activity: Context?,
        edtCourseName: String,
        edtCoursePrice: String,
        edtCourseDetail: String,
        tutor_id: String,
        tutor_username: String,
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
            val json: String = Utils().getGson()!!.toJson(body)
            Log.d("a9a20as8da", json)

            /* val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "json")

             val bodyFile = UploadRequestBody(tempFile, "uploaded_file")
             val multipart = MultipartBody.Part.createFormData(
                 "uploaded_file",
                 tempFile.name,
                 bodyFile
             )
             val json: String = Utils().getGson()!!.toJson(multipart)
             Log.d("a9a20as8da", json)*/


            //  Log.d("d7s2dfg9sf", base64)

            DataModule.instance()!!.addCourse(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableObserver<AddCourseResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: AddCourseResponse) {
                        Log.d("d7s2dfg9sf", t.isSuccessful.toString() + " mess: " + t.message)
                        if (t.isSuccessful) {
                            res.invoke(true, t.message.toString())
                        } else {
                            res.invoke(false, t.message.toString())
                        }
                    }

                    @SuppressLint("DefaultLocale")
                    override fun onError(e: Throwable) {
                        Log.d("d7s2dfg9sf", e.message.toString() + "2")

                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("d7s2dfg9sf", e.message.toString() + "2")
        }
    }
}