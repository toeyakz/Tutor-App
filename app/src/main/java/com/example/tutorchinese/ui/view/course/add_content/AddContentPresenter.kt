package com.example.tutorchinese.ui.view.course.add_content

import android.annotation.SuppressLint
import android.content.Context
import android.util.Base64
import android.util.Base64OutputStream
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
import java.io.*
import java.lang.Exception

class AddContentPresenter {


    fun fileToBase64(file: File): String {
        var inputStream: InputStream? = null //You can get an inputStream using any IO API

        inputStream = FileInputStream(file.absolutePath)
        val buffer = ByteArray(8192)
        var bytesRead: Int
        val output = ByteArrayOutputStream()
        val output64 =
            Base64OutputStream(output, Base64.DEFAULT)
        try {
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                output64.write(buffer, 0, bytesRead)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        output64.close()

        return output.toString()
    }

    @SuppressLint("CheckResult")
    fun sendDataCourseToServer(
        activity: Context?,
        edtContentNumber: String,
        edtContentName: String,
        edtContentDetail: String,
        course_id:String,
        edtLink: String,
        tempFile: File,
        res: (Boolean, String) -> Unit
    ) {

        try {

            val surname: String = tempFile.path.substring(tempFile.path.lastIndexOf(".") + 1)
            val base64 = fileToBase64(tempFile)

            val conTactArray = JSONArray()
            val root = JSONObject()
            val contact = JSONObject()

            contact.put("id_course", course_id)
            contact.put("content_chapter_number", edtContentNumber)
            contact.put("content_name", edtContentName)
            contact.put("content_info", edtContentDetail)

            contact.put("content_link", edtLink)

            //file
            contact.put("surname", surname)
            contact.put("base64", base64)
            contact.put("file_name", tempFile.name)


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