package com.example.acera.theforum.NetworkService

import com.example.acera.theforum.Model.Json
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by Jerry on 18/1/4.
 * Tieba service
 */


interface TiebaService
{
    // data -> Json.User
    @POST("register")
    fun register(@Body data: String): Observable<Json.Message>

    // data -> Json.User
    @POST("sign_in")
    fun signIn(@Body data: String): Observable<Json.MessageToken>

    // data -> Json.User username
    @POST("modify_user")
    fun modifyUser(@Body data: String,
                   @Header("token") token: String): Observable<Json.Message>

    // data -> Json.User username
    @POST("get_user")
    fun getUser(@Body data: String,
                @Header("token") token: String): Observable<Json.MessageUser>

    // data -> Json.Post
    @POST("new_post")
    fun newPost(@Body data: String,
                @Header("token") token: String): Observable<Json.Message>

    // data -> Json.Post pid
    @POST("delete_post")
    fun deletePost(@Body data: String,
                   @Header("token") token: String): Observable<Json.Message>

    // data -> Json.Comment
    @POST("add_comment")
    fun addComment(@Body data: String,
                   @Header("token") token: String): Observable<Json.Message>

    // data -> Json.Comment cid
    @POST("delete_comment")
    fun deleteComment(@Body data: String,
                      @Header("token") token: String): Observable<Json.Message>

    @POST("get_post_by_pid")
    fun getPostByPid(@Body data: String): Observable<Json.MessagePost>

    // data -> Json.PostsRequest datetime [limit]
    @POST("get_posts_by_time")
    fun getPostsByTime(@Body data: String): Observable<Json.MessagePosts>

    // data -> Json.Post pid
    @POST("get_comments_desc")
    fun getCommentsByPidDesc(@Body data: String): Observable<Json.MessageComments>

}