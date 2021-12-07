package com.demo.listdarktheme.rest.api

import com.demo.listdarktheme.rest.model.Recipe
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("recipes/list")
    suspend fun getApiResult(@Query("from") from: Int?, @Query("size") size: Int?): Recipe
}