package com.example.acera.theforum

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.acera.theforum.Adapter.RecyclerAdapter
import com.example.acera.theforum.Adapter.ViewHolder
import com.example.acera.theforum.Model.Json
import com.example.acera.theforum.Model.Json.Companion.bbcodeToSpanned
import com.example.acera.theforum.NetworkService.ServiceFactory
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_post.*
import java.util.*


class PostActivity : AppCompatActivity()
{
    private var post: Json.Post? = null
    private var token: Json.Token? = null
    private val comments = LinkedList<Json.Comment>()
    private var recyclerAdapter: RecyclerAdapter<Json.Comment>? = null
    private val layoutManager = WrapContentLinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        post = intent.getSerializableExtra(getString(R.string.goto_post_detail)) as Json.Post
        token = intent.getSerializableExtra(getString(R.string.token)) as Json.Token?
        postDetailToolbar.title = bbcodeToSpanned(post!!.p_title!!, false)


        postContent.text = bbcodeToSpanned(post!!.p_content!!, true)
        postTime.text = post!!.p_datetime!!.substring(0, 10)
        postSender.text = post!!.username
        postReply.text = post!!.p_floor.toString()

        initRecyclerView()
        loadComment()

        if (token == null)
        {
            fab.visibility = View.GONE
        } else
        {
            fab.setOnClickListener {
                val myIntent = Intent(this, EditActivity::class.java)
                myIntent.putExtra(getString(R.string.edit_intent), resources.getInteger(R.integer.edit_reply))
                myIntent.putExtra(getString(R.string.token), token)
                myIntent.putExtra("pid", post!!.pid)
                startActivityForResult(myIntent, resources.getInteger(R.integer.edit_reply))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        if (requestCode == resources.getInteger(R.integer.edit_reply))
        {
            loadComment()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

//    override fun onNewIntent(intent: Intent?)
//    {
//        super.onNewIntent(intent)
//        if (intent!!.getBooleanExtra("success", false))
//        {
//            loadComment()
//        }
//    }

    private fun initRecyclerView()
    {
        recyclerAdapter = object : RecyclerAdapter<Json.Comment>(this, R.layout.post_comment_item, comments)
        {
            override fun convert(holder: ViewHolder, t: Json.Comment)
            {
                holder.getView<TextView>(R.id.commentItemContent).text = Json.bbcodeToSpanned(t.c_content!!, true)
                holder.getView<TextView>(R.id.commentItemTime).text = t.c_datetime!!.substring(0, 10)
                holder.getView<TextView>(R.id.commentItemSender).text = t.username
            }

        }

        recyclerAdapter!!.setOnItemClickListener(object : RecyclerAdapter.OnItemClickListener
        {
            override fun onClick(view: View, position: Int)
            {

            }

            override fun onLongClick(view: View, position: Int)
            {
                if (token != null && token!!.username == "admin")
                {
                    val gson = Gson()
                    var message: Json.Message? = null
                    val dialog = AlertDialog.Builder(this@PostActivity).create()
                    dialog.setTitle("Confirm")
                    dialog.setMessage("Are you sure to delete this comment?")
                    dialog.setCancelable(true)
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK")
                    { _, _ ->
                        ServiceFactory.myService
                                .deleteComment(Json.Comment(null, null, null, null, comments[position].cid, null, null), gson.toJson(token))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : Observer<Json.Message>
                                {
                                    override fun onNext(t: Json.Message)
                                    {
                                        message = t
                                    }

                                    override fun onError(e: Throwable)
                                    {
                                        val dialog1 = AlertDialog.Builder(this@PostActivity).create()
                                        e.printStackTrace()
                                        dialog1.setTitle("Alert")
                                        dialog1.setMessage("Error!")
                                        dialog1.setCancelable(true)
                                        dialog1.setButton(DialogInterface.BUTTON_POSITIVE, "OK")
                                        { _, _ ->
                                            dialog1.dismiss()
                                        }
                                        dialog1.show()
                                    }

                                    override fun onComplete()
                                    {
                                        Toast.makeText(this@PostActivity, "Deleted!", Toast.LENGTH_SHORT).show()
                                        comments.removeAt(position)
                                        recyclerAdapter!!.notifyItemRemoved(position)
                                    }

                                    override fun onSubscribe(d: Disposable)
                                    {
                                        // nothing to do
                                    }
                                })
                    }
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel")
                    { _, _ ->
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
        })

        postCommentView.adapter = recyclerAdapter
        postCommentView.layoutManager = layoutManager
        postCommentView.itemAnimator = DefaultItemAnimator()
    }

    private fun loadComment()
    {
        ServiceFactory.myService
                .getCommentsByPidDesc(post!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Json.MessageComments>
                {
                    override fun onComplete()
                    {
                        postProgressBar.visibility = View.GONE
                    }

                    override fun onNext(t: Json.MessageComments)
                    {
                        if (t.state == "1")
                        {
                            Toast.makeText(this@PostActivity, t.message, Toast.LENGTH_SHORT).show()
                            onComplete()
                        } else
                        {
                            comments.clear()
                            comments.addAll(0, t.data!!.comments!!)
                            recyclerAdapter!!.notifyItemRangeInserted(0, comments.size)
                        }

                    }

                    override fun onError(e: Throwable)
                    {
                        e.printStackTrace()
                    }

                    override fun onSubscribe(d: Disposable)
                    {
                        postProgressBar.visibility = View.VISIBLE
                    }

                })
    }

    inner class WrapContentLinearLayoutManager : LinearLayoutManager
    {
        constructor(context: Context) : super(context)
        {
        }

        constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)
        {
        }

        constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
        {
        }

        override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State)
        {
            try
            {
                super.onLayoutChildren(recycler, state)
            } catch (e: IndexOutOfBoundsException)
            {
                e.printStackTrace()
            }

        }
    }
}


