package com.demo.listdarktheme.rest.api

import com.demo.listdarktheme.BuildConfig
import com.demo.listdarktheme.utils.AppConstants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    private const val BASE_URL = "https://tasty.p.rapidapi.com/"

    private val interceptor = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .addHeader("x-rapidapi-host", "tasty.p.rapidapi.com")
            .addHeader("x-rapidapi-key", AppConstants.API_KEY)
            .build()
        chain.proceed(request)
    }

    private fun getRestApiService(): Retrofit {
        return Retrofit.Builder()
            .client(getApiClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    private fun getApiClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return OkHttpClient().newBuilder().addInterceptor(logging).addInterceptor(interceptor)
            .addInterceptor(
                OkHttpProfilerInterceptor()
            ).readTimeout(30, TimeUnit.SECONDS).connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    val apiInterface: RestApi = getRestApiService().create(RestApi::class.java)
}