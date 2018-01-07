package com.example.acera.theforum

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.acera.theforum.Adapter.RecyclerAdapter
import com.example.acera.theforum.Adapter.ViewHolder
import com.example.acera.theforum.Model.Json
import com.example.acera.theforum.NetworkService.ServiceFactory
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
    private val layoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        post = intent.getSerializableExtra(getString(R.string.goto_post_detail)) as Json.Post
        token = intent.getSerializableExtra(getString(R.string.token)) as Json.Token?
        postDetailToolbar.title = post!!.p_title


        postContent.text = post!!.p_content
        postTime.text = post!!.p_datetime!!.substring(0, 9)
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

    override fun onNewIntent(intent: Intent?)
    {
        super.onNewIntent(intent)
        if (intent!!.getBooleanExtra("success", false))
        {
            loadComment()
        }
    }

    private fun initRecyclerView()
    {
        recyclerAdapter = object : RecyclerAdapter<Json.Comment>(this, R.layout.post_reply_item, comments)
        {
            override fun convert(holder: ViewHolder, t: Json.Comment)
            {
                holder.getView<TextView>(R.id.commentItemContent).text = t.c_content
                holder.getView<TextView>(R.id.commentItemTime).text = t.c_datetime!!.substring(0, 10)
                holder.getView<TextView>(R.id.commentItemSender).text = t.username
            }

        }

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

}
