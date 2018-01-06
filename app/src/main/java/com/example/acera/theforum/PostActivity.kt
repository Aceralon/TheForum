package com.example.acera.theforum

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.acera.theforum.Model.Json
import kotlinx.android.synthetic.main.activity_post.*


class PostActivity : AppCompatActivity()
{
    private var post: Json.Post? = null
    private var token: Json.Token? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        post = intent.getSerializableExtra(getString(R.string.goto_post_detail)) as Json.Post
        token = intent.getSerializableExtra(getString(R.string.if_logged_in)) as Json.Token?
        postDetailToolbar.title = post!!.p_title


        postContent.text = post!!.p_content
        postTime.text = post!!.p_datetime
        postSender.text = post!!.username
        postReply.text = post!!.p_floor.toString()

        //TODO load comment


        if (token == null)
        {
            fab.visibility = View.GONE
        } else
        {
            fab.setOnClickListener { view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                val myIntent = Intent(this, EditActivity::class.java)
                myIntent.putExtra(getString(R.string.edit_intent), resources.getInteger(R.integer.edit_reply))
                myIntent.putExtra(getString(R.string.token), token)
                startActivity(myIntent)
            }
        }
    }

}
