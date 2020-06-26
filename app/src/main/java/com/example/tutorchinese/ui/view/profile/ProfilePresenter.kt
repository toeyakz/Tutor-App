package com.example.tutorchinese.ui.view.profile

import android.R
import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import com.example.tutorchinese.ui.manage.Constants

class ProfilePresenter {
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
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

}