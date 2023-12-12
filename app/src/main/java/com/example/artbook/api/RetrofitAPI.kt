package com.example.artbook.api

import com.example.artbook.model.ImageResponse
import com.example.artbook.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("/api/")
    suspend fun searchImage(
        @Query("q") searchQuery:String,
        @Query("key") searchKey : String = API_KEY
    ) : Response<ImageResponse>


}