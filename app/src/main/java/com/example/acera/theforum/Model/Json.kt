package com.example.acera.theforum.Model

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.util.Log
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
        private val formatSearch = arrayOf(
                """\[b\](.*?)\[/b\]""",
                """\[i\](.*?)\[/i\]""",
                """\[u\](.*?)\[/u\]""",
                """\[img\](.*?)\[/img\]""",
                """\[emo\](.*?)\[/emo\]""")

        fun getCurrentTime(): String
        {
            formatter.timeZone = TimeZone.getTimeZone("GMT+08")
            return formatter.format(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1))
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


        fun bbcodeToHTML(bbcode: String): String
        {
            var html = bbcode
            val formatReplace = arrayOf(
                    "<strong>$1</strong>",
                    "<em>$1</em>",
                    "<span style='text-decoration: underline;'>$1</span>",
                    "<img src='uploads/files/$1' alt='image' class='content-image'>",
                    "<span class='emoticons emo-$1'></span>"
            )
            for (i in formatSearch.indices)
            {
                html = html.replace(Regex(formatSearch[i]), formatReplace[i])
            }
            return html
        }

        fun bbcodeToHtmlNoImage(bbcode: String): String
        {
            var html = bbcode
            val formatReplace = arrayOf(
                    "<strong>$1</strong>",
                    "<em>$1</em>",
                    "<span style='text-decoration: underline;'>$1</span>",
                    "[图片]",
                    "<span class='emoticons emo-$1'></span>"
            )
            for (i in formatSearch.indices)
            {
                html = html.replace(Regex(formatSearch[i]), formatReplace[i])
            }
            return html
        }
    }
}
//TODO: Text transaltion

//function bbcode_translate(str) {
//    let format_search = [
//    /\[b\](.*?)\[\/b\]/ig,
//    /\[i\](.*?)\[\/i\]/ig,
//    /\[u\](.*?)\[\/u\]/ig,
//    /\[img\](.*?)\[\/img\]/ig,
//    /\[emo\](.*?)\[\/emo\]/ig
//    ];
//    let format_replace = [
//            "<strong>$1</strong>",
//            "<em>$1</em>",
//            "<span style='text-decoration: underline;'>$1</span>",
//            "<img src='uploads/files/$1' alt='image' class='content-image'>",
//            "<span class='emoticons emo-$1'></span>"
//            ];
//    for (let i = 0; i < format_search.length; i++) {
//        str = str.replace(format_search[i], format_replace[i]);
//    }
//    return str;
//}
//
//function bbcode_translate_without_img(str) {
//    let format_search = [
//    /\[b\](.*?)\[\/b\]/ig,
//    /\[i\](.*?)\[\/i\]/ig,
//    /\[u\](.*?)\[\/u\]/ig,
//    /\[img\](.*?)\[\/img\]/ig,
//    /\[emo\](.*?)\[\/emo\]/ig
//    ];
//    let format_replace = [
//            "<strong>$1</strong>",
//            "<em>$1</em>",
//            "<span style='text-decoration: underline;'>$1</span>",
//            "[图片]",
//            "<span class='emoticons emo-$1'></span>"
//            ];
//    for (let i = 0; i < format_search.length; i++) {
//        str = str.replace(format_search[i], format_replace[i]);
//    }
//    return str;
//}