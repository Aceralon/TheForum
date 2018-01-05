package com.example.acera.theforum

import com.example.acera.theforum.Model.Json
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

/**
 * Created by Jerry on 18/1/4.
 * Tieba service
 */


interface TiebaService {

    // data -> Json.User
    @POST("register")
    fun register(@Body data: String): Call<Json.Message>

    // data -> Json.User
    @POST("sign_in")
    fun signIn(@Body data: String): Call<Json.MessageToken>

    // data -> Json.User username
    @POST("modify_user")
    fun modifyUser(@Body data: String,
                   @Header("token") token: String): Call<Json.Message>

    // data -> Json.User username
    @POST("get_user")
    fun getUser(@Body data: String,
                @Header("token") token: String): Call<Json.MessageUser>

    // data -> Json.Post
    @POST("new_post")
    fun newPost(@Body data: String,
                @Header("token") token: String): Call<Json.Message>

    // data -> Json.Post pid
    @POST("delete_post")
    fun deletePost(@Body data: String,
                   @Header("token") token: String): Call<Json.Message>

    // data -> Json.Comment
    @POST("add_comment")
    fun addComment(@Body data: String,
                   @Header("token") token: String): Call<Json.Message>

    // data -> Json.Comment cid
    @POST("delete_comment")
    fun deleteComment(@Body data: String,
                      @Header("token") token: String): Call<Json.Message>

    @POST("get_post_by_pid")
    fun getPostByPid(@Body data: String): Call<Json.MessagePost>

    // data -> Json.PostsRequest datetime [limit]
    @POST("get_posts_by_time")
    fun getPostsByTime(@Body data: String): Call<Json.MessagePosts>

    // data -> Json.Post pid
    @POST("get_comments_desc")
    fun getCommentsByPidDesc(@Body data: String): Call<Json.MessageComments>

}