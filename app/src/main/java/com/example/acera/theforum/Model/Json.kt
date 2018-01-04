package com.example.acera.theforum.Model

import java.io.Serializable

/**
 * Created by acera on 2018/1/1.
 *
 */
class Json
{
    data class Message(val state: String, val message: String, val data: Any)

    data class User(val uid: Long, val username: String, val password: String, val role: Int, val avatar: String)

    data class Post(val username: String, val p_title: String, val p_content: String, val p_datetime: String, val p_floor: Long) : Serializable

    data class Posts(val posts: List<Post>)

    data class Comment(val username: String, val c_content: String, val c_datetime: String) : Serializable

    data class Comments(val comments: List<Comment>)
}