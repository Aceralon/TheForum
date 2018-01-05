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
    fun register(@Body data: Json.User): Observable<Json.Message>

    // data -> Json.User
    @POST("sign_in")
    fun signIn(@Body data: Json.User): Observable<Json.MessageToken>

    // data -> Json.User username
    @POST("modify_user")
    fun modifyUser(@Body data: Json.User,
                   @Header("token") token: String): Observable<Json.Message>

    // data -> Json.User username
    @POST("get_user")
    fun getUser(@Body data: Json.User,
                @Header("token") token: String): Observable<Json.MessageUser>

    // data -> Json.Post
    @POST("new_post")
    fun newPost(@Body data: Json.Post,
                @Header("token") token: String): Observable<Json.Message>

    // data -> Json.Post pid
    @POST("delete_post")
    fun deletePost(@Body data: Json.Post,
                   @Header("token") token: String): Observable<Json.Message>

    // data -> Json.Comment
    @POST("add_comment")
    fun addComment(@Body data: Json.Comment,
                   @Header("token") token: String): Observable<Json.Message>

    // data -> Json.Comment cid
    @POST("delete_comment")
    fun deleteComment(@Body data: Json.Comment,
                      @Header("token") token: String): Observable<Json.Message>

    @POST("get_post_by_pid")
    fun getPostByPid(@Body data: Json.Post): Observable<Json.MessagePost>

    // data -> Json.PostsRequest datetime [limit]
    @POST("get_posts_by_time")
    fun getPostsByTime(@Body data: Json.PostsRequest): Observable<Json.MessagePosts>

    // data -> Json.Post pid
    @POST("get_comments_desc")
    fun getCommentsByPidDesc(@Body data: Json.Post): Observable<Json.MessageComments>

}