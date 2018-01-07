package com.example.acera.theforum

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.acera.theforum.Model.Json
import com.example.acera.theforum.NetworkService.ServiceFactory
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_edit.*
import java.util.*

class EditActivity : AppCompatActivity()
{
    private var isReply = false
    private val gson = Gson()
    private var token: Json.Token? = null
    private var message: Json.Message? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)


        when (intent.getIntExtra(getString(R.string.edit_intent), 0))
        {
            resources.getInteger(R.integer.edit_new_post) ->
            {
                title = "New Post"
                titleEditText.isFocusable = true
                titleEditText.isFocusableInTouchMode = true
                titleEditText.visibility = View.VISIBLE
            }
            resources.getInteger(R.integer.edit_reply) ->
            {
                isReply = true
                title = "Reply"
                titleEditText.isFocusable = false
                titleEditText.isFocusableInTouchMode = false
                titleEditText.visibility = View.INVISIBLE
            }
            0 -> finish()
        }

        token = intent.getSerializableExtra(getString(R.string.token)) as Json.Token

    }

    fun cancelButtonClick(view: View)
    {
        val dialog = AlertDialog.Builder(this@EditActivity).create()
        dialog.setTitle("Confirm")
        dialog.setMessage("Are you sure to cancel editing? All the content will be deleted!")
        dialog.setCancelable(true)
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK")
        {
            _, _ -> finish()
        }
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel")
        {
            _, _ -> dialog.dismiss()
        }
        dialog.show()
    }

    fun sendButtonClick(view: View)
    {
        val tokenString = gson.toJson(token)

        when (intent.getIntExtra(getString(R.string.edit_intent), 0))
        {
            resources.getInteger(R.integer.edit_new_post) ->
            {
                ServiceFactory.myService
                        .newPost(Json.Post(null, titleEditText.text.toString(), editEditText.text.toString(), null, null, null), tokenString)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Observer<Json.Message>
                        {
                            override fun onNext(msg: Json.Message)
                            {
                                message = msg
                            }

                            override fun onError(e: Throwable)
                            {
                                val dialog = AlertDialog.Builder(this@EditActivity).create()
                                dialog.setTitle("Alert")
                                dialog.setMessage("Something wrong when posting!")
                                dialog.setCancelable(true)
                                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK")
                                {
                                    _, _ -> dialog.dismiss()
                                }
                                dialog.show()
                                titleEditText.isEnabled = true
                                editEditText.isEnabled = true
                            }

                            override fun onComplete() {
                                titleEditText.isEnabled = true
                                editEditText.isEnabled = true
                                if (message!!.state != "0")
                                {
                                    Toast.makeText(this@EditActivity, "Posted successfully!", Toast.LENGTH_SHORT).show()
                                    finish()
                                } else
                                {
                                    val dialog = AlertDialog.Builder(this@EditActivity).create()
                                    dialog.setTitle("Alert")
                                    dialog.setMessage("Something wrong when posting!")
                                    dialog.setCancelable(true)
                                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK")
                                    {
                                        _, _ -> dialog.dismiss()
                                    }
                                    dialog.show()
                                }
                            }

                            override fun onSubscribe(d: Disposable) {
                                titleEditText.isEnabled = false
                                editEditText.isEnabled = false
                            }
                        })
            }

            resources.getInteger(R.integer.edit_reply) ->
            {
                val pid = intent.getLongExtra("pid", 0)
                ServiceFactory.myService
                        .addComment(Json.Comment(null, editEditText.text.toString(), null, pid), tokenString)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Observer<Json.Message>
                        {
                            override fun onNext(msg: Json.Message)
                            {
                                message = msg
                            }

                            override fun onError(e: Throwable)
                            {
                                val dialog = AlertDialog.Builder(this@EditActivity).create()
                                dialog.setTitle("Alert")
                                dialog.setMessage("Something wrong when posting!")
                                dialog.setCancelable(true)
                                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK")
                                {
                                    _, _ -> dialog.dismiss()
                                }
                                dialog.show()
                                titleEditText.isEnabled = true
                                editEditText.isEnabled = true
                            }

                            override fun onComplete() {
                                titleEditText.isEnabled = true
                                editEditText.isEnabled = true
                                if (message!!.state != "0")
                                {
                                    Toast.makeText(this@EditActivity, "Posted successfully!", Toast.LENGTH_SHORT).show()
                                    finish()
                                } else
                                {
                                    val dialog = AlertDialog.Builder(this@EditActivity).create()
                                    dialog.setTitle("Alert")
                                    dialog.setMessage("Something wrong when posting!")
                                    dialog.setCancelable(true)
                                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK")
                                    {
                                        _, _ -> dialog.dismiss()
                                    }
                                    dialog.show()
                                }
                            }

                            override fun onSubscribe(d: Disposable) {
                                titleEditText.isEnabled = false
                                editEditText.isEnabled = false
                            }
                        })
            }
            0 -> finish()
        }
    }
}
