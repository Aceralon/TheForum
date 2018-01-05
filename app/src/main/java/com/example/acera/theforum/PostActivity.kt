package com.example.acera.theforum

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        setSupportActionBar(postToolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            gotoReply()
        }

        //todo load designated content
    }

    private fun gotoReply()
    {
        val myIntent = Intent(this, ReplyActivity::class.java)
        startActivityForResult(myIntent, resources.getInteger(R.integer.request_reply_post))
    }

}
