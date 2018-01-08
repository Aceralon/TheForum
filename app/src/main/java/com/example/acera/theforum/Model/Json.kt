package com.example.acera.theforum.Model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.text.Editable
import android.text.Html
import android.text.Spanned
import android.util.Log
import org.xml.sax.XMLReader
import java.io.Serializable
import java.net.URL
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

    data class Comment(val username: String?, val c_content: String?, val c_datetime: String?, val pid: Long?, val cid: Long?, val c_floor: Long?, val r_floor: Long?) : Serializable

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


        private fun bbcodeToHtml(bbcode: String): String
        {
            var html = bbcode
            val formatReplace = arrayOf(
                    "<strong>$1</strong>",
                    "<em>$1</em>",
                    "<span style='text-decoration: underline;'>$1</span>",
                    "<br><img src='uploads/files/$1' alt='image' class='content-image'>",
                    "<span class='emoticons emo-$1'></span>"
            )
            for (i in formatSearch.indices)
            {
                html = html.replace(Regex(formatSearch[i]), formatReplace[i])
            }
            return html
        }

        private fun bbcodeToHtmlNoImage(bbcode: String): String
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

        private val imgGetter = object : Html.ImageGetter
        {
            override fun getDrawable(source: String?): Drawable
            {
                val des = "https://simpletieba.mtzero.org/" + source
                Log.i("Load pic", des)
                var drawable: Drawable? = null
                val url = URL(des)

                val thread = object : Thread()
                {
                    override fun run()
                    {
                        try
                        {
                            val a = url.openStream()
                            drawable = Drawable.createFromStream(a, "")

                        } catch (e: Exception)
                        {
                            e.printStackTrace()
                        }
                    }
                }
                thread.start()
                thread.join()
                drawable!!.setBounds(0, 0, drawable!!.intrinsicWidth, drawable!!.intrinsicHeight)
//                drawable = zoomDrawable(drawable!!, 500, 500 * drawable!!.intrinsicWidth / drawable!!.intrinsicHeight)
                return drawable!!
            }

        }

        private fun zoomDrawable(drawable: Drawable, w: Int, h: Int): Drawable
        {
            val width = drawable.intrinsicWidth
            val height = drawable.intrinsicHeight
            val oldbmp = drawableToBitmap(drawable)
            val matrix = Matrix()
            val scaleWidth = w.toFloat() / width
            val scaleHeight = h.toFloat() / height
            matrix.postScale(scaleWidth, scaleHeight)
            val newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                    matrix, true)

            return BitmapDrawable(newbmp)
        }

        private fun drawableToBitmap(drawable: Drawable): Bitmap
        {
            val width = drawable.intrinsicWidth
            val height = drawable.intrinsicHeight
            val config = if (drawable.opacity != PixelFormat.OPAQUE)
                Bitmap.Config.ARGB_8888
            else
                Bitmap.Config.RGB_565
            val bitmap = Bitmap.createBitmap(width, height, config)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, width, height)
            drawable.draw(canvas)
            return bitmap
        }

        private val tagHanddler = object : Html.TagHandler
        {
            override fun handleTag(opening: Boolean, tag: String?, output: Editable?, xmlReader: XMLReader?)
            {

            }

        }

        fun bbcodeToSpanned(bbcode: String, image: Boolean): Spanned
        {
            return if (image)
                Html.fromHtml(bbcodeToHtml(bbcode), Html.FROM_HTML_MODE_COMPACT, imgGetter, tagHanddler)
            else
                Html.fromHtml(bbcodeToHtmlNoImage(bbcode), Html.FROM_HTML_MODE_COMPACT, imgGetter, tagHanddler)
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