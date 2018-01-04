package com.example.acera.theforum

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun registerButtonClick(view: View)
    {
        registerUserLayout.error = null
        registerPasswordLayout.error = null
        registerConfirmLayout.error = null

        val userStr = registerUser.text.toString()
        val passwordStr = registerPassword.text.toString()
        val confirmStr = registerConfirm.text.toString()

        var focusView: View? = null
        var cancel = false


        if (passwordStr.isEmpty())
        {
            registerPasswordLayout.error = "Password cannot be empty!"
            focusView = registerPassword
            cancel = true
        } else if (confirmStr.isEmpty())
        {
            registerConfirmLayout.error = "Please re-enter password"
            focusView = registerConfirm
            cancel = true
        }

        if (userStr.isEmpty())
        {
            registerUserLayout.error = "Username cannot be empty!"
            focusView = registerUser
            cancel = true
        }

        if (!cancel && (passwordStr != confirmStr))
        {
            registerConfirmLayout.error = "Password mismatch!"
            focusView = registerConfirm
            cancel = true
        }

        if (cancel)
        {
            focusView!!.requestFocus()
        } else
        {
            registerButton.progress = 1
            registerButton.isEnabled = false
            registerUserLayout.isEnabled = false
            registerPasswordLayout.isEnabled = false
            //TODO("register action")
            registerButton.progress = 100
            try
            {
                Thread.sleep(2000)
            } catch (e: InterruptedException)
            {
                e.printStackTrace()
            }
            finish()

        }
    }
}
