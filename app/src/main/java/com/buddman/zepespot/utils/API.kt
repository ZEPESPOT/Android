package com.buddman.zepespot.utils

import com.buddman.zepespot.models.Course
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface API {

    @POST("/course/sequence/list")
    @FormUrlEncoded
    fun getCourse(@Field("course_number") courseNumber : Int) : Observable<ArrayList<Course>>
}