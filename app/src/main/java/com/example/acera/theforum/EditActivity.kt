package com.example.acera.theforum

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

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
            }
            resources.getInteger(R.integer.edit_reply) ->
            {
                isReply = true
                title = "Reply"
            }
            0 -> finish()
        }

    }
}
