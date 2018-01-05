package com.example.acera.theforum.Model

import java.io.Serializable

/**
 * Created by acera on 2018/1/1.
 *
 */
class Json
{
    data class Message(val state: String, val message: String, val data: Any?)

    data class User(val uid: Long?, val username: String?, val password: String?, val role: Int?, val avatar: String?)

    data class Post(val username: String?, val p_title: String?, val p_content: String?, val p_datetime: String?, val p_floor: Long?) : Serializable

    data class Posts(val posts: ArrayList<Post>?)

    data class Comment(val username: String?, val c_content: String?, val c_datetime: String?) : Serializable

    data class Comments(val comments: ArrayList<Comment>?)

    data class Token(val username: String?, val expiration: Long?, val token: String?)

    data class PostsRequest(val datetime: String, val limit: Int)

    data class MessageUser(val state: String, val message: String, val data: User?)

    data class MessagePost(val state: String, val message: String, val data: Post?)

    data class MessagePosts(val state: String, val message: String, val data: Posts?)

    data class MessageComment(val state: String, val message: String, val data: Comment?)

    data class MessageComments(val state: String, val message: String, val data: Comments?)

    data class MessageToken(val state: String, val message: String, val data: Token?)

}