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
    @POST("Interface.jsp?intent=register")
    fun register(@Body data: Json.User): Observable<Json.Message>

    // data -> Json.User
    @POST("Interface.jsp?intent=sign_in")
    fun signIn(@Body data: Json.User): Observable<Json.MessageToken>

    // data -> Json.User username
    @POST("Interface.jsp?intent=modify_user")
    fun modifyUser(@Body data: Json.User,
                   @Header("token") token: String): Observable<Json.Message>

    // data -> Json.User username
    @POST("Interface.jsp?intent=get_user")
    fun getUser(@Body data: Json.User,
                @Header("token") token: String): Observable<Json.MessageUser>

    // data -> Json.Post
    @POST("Interface.jsp?intent=new_post")
    fun newPost(@Body data: Json.Post,
                @Header("token") token: String): Observable<Json.Message>

    // data -> Json.Post pid
    @POST("Interface.jsp?intent=delete_post")
    fun deletePost(@Body data: Json.Post,
                   @Header("token") token: String): Observable<Json.Message>

    // data -> Json.Comment
    @POST("Interface.jsp?intent=add_comment")
    fun addComment(@Body data: Json.Comment,
                   @Header("token") token: String): Observable<Json.Message>

    // data -> Json.Comment cid
    @POST("Interface.jsp?intent=delete_comment")
    fun deleteComment(@Body data: Json.Comment,
                      @Header("token") token: String): Observable<Json.Message>

    @POST("Interface.jsp?intent=get_post_by_pid")
    fun getPostByPid(@Body data: Json.Post): Observable<Json.MessagePost>

    // data -> Json.PostsRequest datetime [limit]
    @POST("Interface.jsp?intent=get_posts_by_time")
    fun getPostsByTime(@Body data: Json.PostsRequest): Observable<Json.MessagePosts>

    // data -> Json.Post pid
    @POST("Interface.jsp?intent=get_comments_desc")
    fun getCommentsByPidDesc(@Body data: Json.Post): Observable<Json.MessageComments>

}