package com.fneis.myevents.repository.api

import com.fneis.myevents.model.data.CheckIn
import com.fneis.myevents.model.data.Event
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventApi {

    @GET("events")
    suspend fun fetchUrl(): Response<List<Event>>

    @POST("checkin")
    suspend fun sendChekIn(@Body checkIn: CheckIn): Response<ResponseBody>
}