package com.example.acera.theforum

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity()
{
    private var isReply = false

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

    }
}
