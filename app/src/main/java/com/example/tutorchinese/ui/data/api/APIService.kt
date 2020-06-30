package com.example.tutorchinese.ui.data.api

import com.example.tutorchinese.ui.data.response.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {
    /*@FormUrlEncoded
    @POST("services/toey_test.php?func=getInvoice")
    fun getInvoice(
        @Field("vehicle_id") vehicle_id: String
    ): Observable<InvoiceResponse>*/

    /*@FormUrlEncoded
    @POST("api/login")
    fun login(
        @Field("user") username: String,
        @Field("pass") password: String,
        @Field("serial") serial: String
    ): Observable<UserResponse>


    @FormUrlEncoded
    @POST("api/login")
    fun logout(
        @Field("user") username: String,
        @Field("pass") password: String,
        @Field("serial") serial: String
    ): Observable<UserResponse>*/

    @POST("tutor/service.php?func=updateCourse")
    fun updateCourse(@Body body: RequestBody): Observable<UpdateCourseResponse>

    @FormUrlEncoded
    @POST("tutor/service.php?func=deleteCourse")
    fun deleteCourse(
        @Field("tutor_id") tutor_id: String,
        @Field("course_id") course_id: String
    ): Observable<DeleteCourseResponse>

    @POST("tutor/service.php?func=addCourse")
    fun addCourse(@Body body: RequestBody): Observable<AddCourseResponse>

    @FormUrlEncoded
    @POST("tutor/service.php?func=getCourse")
    fun getCourse(
        @Field("tutor_id") tutor_id: String
    ): Observable<CourseResponse>

    @FormUrlEncoded
    @POST("tutor/service.php?func=login")
    fun login(
        @Field("user") username: String,
        @Field("pass") password: String
    ): Observable<LoginResponse>

    @POST("tutor/service.php?func=register")
    fun register(@Body body: RequestBody): Observable<RegisterResponse>
}