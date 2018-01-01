package com.example.acera.theforum.Model

/**
 * Created by acera on 2018/1/1.
 *
 */
class Json
{
    data class Message(val state: String, val message: String, val data: Any)

    data class User(val uid: Long, val username: String, val password: String, val role: Int, val avatar: String)

    data class Post(val pid: Long, val uid: Long, val username: String)

    data class Posts(val posts: List<Post>)

    data class Comment(val username: String, val c_content: String)
}