package com.example.tutorchinese.ui.view.beginner.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.tutorchinese.ui.data.api.DataModule
import com.example.tutorchinese.ui.data.entities.User
import com.example.tutorchinese.ui.data.response.LoginResponse
import com.example.tutorchinese.ui.manage.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class LoginPresenter {

    interface View {
        fun onSuccessService(user: List<User>?, type: String)
        fun onErrorService(error: String)
    }

    @SuppressLint("CheckResult")
    fun callLogin(context: Context, user: String, pass: String, view: View) {

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

    fun addDataUserToPrefs(context: Context, user: List<User>?, type: String) {
        val pf: SharedPreferences =
            context.getSharedPreferences(Constants.PREFERENCES_USER, Context.MODE_PRIVATE)
        val editor = pf.edit()
        editor.putBoolean("session", true)
        editor.putString("type", type)
        editor.putInt("U_id", user!![0].U_id!!)
        editor.putString("U_username", user[0].U_username.toString())
        editor.putString("U_password", user[0].U_password.toString())
        editor.putString("U_name", user[0].U_name.toString())
        editor.putString("U_lastname", user[0].U_lastname.toString())
        editor.putString("U_birth_day", user[0].U_birth_day.toString())
        editor.putString("U_email", user[0].U_email.toString())
        editor.putString("U_tel", user[0].U_tel.toString())
        editor.putString("U_img", user[0].U_email.toString())
        editor.apply()
    }

    fun addDataTutorTioPrefs(context: Context, user: List<User>?, type: String) {
        val pf: SharedPreferences =
            context.getSharedPreferences(Constants.PREFERENCES_USER, Context.MODE_PRIVATE)
        val editor = pf.edit()
        editor.putBoolean("session", true)
        editor.putString("type", type)
        editor.putInt("T_id", user!![0].T_id!!)
        editor.putString("T_username", user[0].T_username.toString())
        editor.putString("T_password", user[0].T_password.toString())
        editor.putString("T_name", user[0].T_name.toString())
        editor.putString("T_lastname", user[0].T_lastname.toString())
        editor.putString("T_date", user[0].T_date.toString())
        editor.putString("T_education", user[0].T_education.toString())
        editor.putString("T_experience", user[0].T_experience.toString())
        editor.putString("T_expert", user[0].T_expert.toString())
        editor.putString("T_course", user[0].T_course.toString())
        editor.putString("T_address", user[0].T_address.toString())
        editor.putString("T_email", user[0].T_email.toString())
        editor.putString("T_tel", user[0].T_tel.toString())
        editor.putString("T_img", user[0].T_img.toString())
        editor.apply()
    }

    fun addDataAdminToPrefs(context: Context, user: List<User>?, type: String) {
        val pf: SharedPreferences =
            context.getSharedPreferences(Constants.PREFERENCES_USER, Context.MODE_PRIVATE)
        val editor = pf.edit()
        editor.putBoolean("session", true)
        editor.putString("type", type)
        editor.putInt("admin_id", user!![0].admin_id!!)
        editor.putString("admin_username", user[0].admin_username.toString())
        editor.putString("admin_password", user[0].admin_password.toString())
        editor.apply()
    }

}