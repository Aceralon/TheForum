package com.example.acera.theforum

import com.example.acera.theforum.Model.Json
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

/**
 * Created by Jerry on 18/1/4.
 */


interface TiebaService {
    //    companion object {
//        val baseUrl = "https://simpletieba.mtzero.org/functions/Interface.jsp"
//
//    }
    @POST("?intent=register")
    fun register(@Body data: String): Call<Json.Message>

    @POST("?intent=sign_in")
    fun signIn(@Body data: String): Call<Json.MessageToken>
}