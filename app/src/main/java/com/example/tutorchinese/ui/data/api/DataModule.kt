package com.example.tutorchinese.ui.data.api

import com.example.tutorchinese.ui.controler.Utils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class DataModule {

    companion object {
        private var retrofit: Retrofit? = null
        private var gson: Gson? = null
        private const val BASE_URL = Utils.host
        //private const val BASE_URL = "http://10.255.252.44/"

        @Synchronized
        private fun getInstance(): Retrofit? {
            if (retrofit == null) {
                if (gson == null) {
                    gson = GsonBuilder().setLenient().create()
                }
                val okHttpClient = OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson!!))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit
        }

        private var apiService: APIService? = null

        fun instance(): APIService? {
            if (apiService == null) apiService =
                getInstance()!!.create(APIService::class.java)
            return apiService
        }
    }


}