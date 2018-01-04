package com.example.acera.theforum

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.dd.processbutton.iml.ActionProcessButton
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setMode(ActionProcessButton.Mode.ENDLESS)
        loginButton.setColorScheme(
                getColor(R.color.material_light_blue_700),
                getColor(R.color.material_red_700),
                getColor(R.color.material_orange_700),
                getColor(R.color.material_light_green_700))
    }


    fun loginButtonClick(view: View)
    {
        loginUserLayout.error = null
        loginPasswordLayout.error = null

        val userStr = loginUser.text.toString()
        val passwordStr = loginPassword.text.toString()

        var focusView: View? = null
        var cancel = false


        if (passwordStr.isEmpty())
        {
            loginPasswordLayout.error = "Password cannot be empty!"
            focusView = loginPassword
            cancel = true
        }

        if (userStr.isEmpty())
        {
            loginUserLayout.error = "Username cannot be empty!"
            focusView = loginUser
            cancel = true
        }

        if (cancel)
        {
            focusView!!.requestFocus()
        } else
        {
            loginButton.progress = 1
            loginButton.isEnabled = false
            loginUserLayout.isEnabled = false
            loginPasswordLayout.isEnabled = false
            //TODO("Login action")
            loginButton.progress = 100
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
