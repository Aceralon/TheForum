package com.example.acera.theforum.Model

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import java.io.Serializable
import java.util.*

/**
 * Created by acera on 2018/1/1.
 *
 */
class Json : Serializable
{
    data class Message(val state: String, val message: String, val data: Any?)

    data class User(val uid: Long?, val username: String?, val password: String?, val role: Int?, val avatar: String?)

    data class Post(val username: String?, val p_title: String?, val p_content: String?, val p_datetime: String?, val p_floor: Long?, val pid: Long?) : Serializable

    data class Posts(val posts: List<Post>?)

    data class Comment(val username: String?, val c_content: String?, val c_datetime: String?, val pid: Long?) : Serializable

    data class Comments(val comments: List<Comment>?)

    data class Token(val username: String?, val expiration: Long?, val token: String?) : Serializable

    data class PostsRequest(val datetime: String, val limit: Int)

    data class MessageUser(val state: String, val message: String, val data: User?)

    data class MessagePost(val state: String, val message: String, val data: Post?)

    data class MessagePosts(val state: String, val message: String, val data: Posts?)

    data class MessageComments(val state: String, val message: String, val data: Comments?)

    data class MessageToken(val state: String, val message: String, val data: Token?)

    companion object
    {
        private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        fun getCurrentTime(): String
        {
            formatter.timeZone = TimeZone.getTimeZone("GMT+08")
            return formatter.format(Date(System.currentTimeMillis()))
        }

        fun timeToLong(datetime: String): Long
        {
            formatter.timeZone = TimeZone.getTimeZone("GMT+08")
            return formatter.parse(datetime).time
        }

        fun timeToDate(datetime: String): Date
        {
            formatter.timeZone = TimeZone.getTimeZone("GMT+08")
            return formatter.parse(datetime)
        }
    }
}