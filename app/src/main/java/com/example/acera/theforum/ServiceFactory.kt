package com.example.acera.theforum

import com.example.acera.theforum.Model.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Jerry on 18/1/5.
 */

object ServiceFactory
{

    val myService = getService("https://simpletieba.mtzero.org/functions/Interface.jsp")

    private fun getService(url: String): TiebaService
    {
        return ServiceFactory.createRetrofit(url).create(TiebaService::class.java)
    }

    private fun createRetrofit(baseUrl: String): Retrofit
    {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttp())
                .build()
    }

    private fun createOkHttp(): OkHttpClient
    {
        return OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()
    }
}