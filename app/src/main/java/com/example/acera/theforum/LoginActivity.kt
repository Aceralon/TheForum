package com.example.acera.theforum

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.dd.processbutton.iml.ActionProcessButton
import com.example.acera.theforum.Model.Json
import com.example.acera.theforum.NetworkService.ServiceFactory
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity()
{
    var token: Json.MessageToken? = null

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
            loginAction(userStr, passwordStr)
        }
    }

    private fun loginAction(username: String, userPassword: String)
    {
        ServiceFactory.myService
                .signIn(Json.User(null, username, userPassword, null, null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Json.MessageToken>
                {
                    override fun onNext(t: Json.MessageToken)
                    {
                        token = t
                    }

                    override fun onError(e: Throwable)
                    {
                        loginFailed("Login Failed")
                        e.printStackTrace()
                    }

                    override fun onComplete()
                    {
                        if (token!!.state == "1")//login failed
                        {
                            loginFailed(token!!.message)
                        } else
                        {
                            loginButton.progress = 100
                            Handler().postDelayed({ exitActivity(true) }, 1500)
                        }
                    }

                    override fun onSubscribe(d: Disposable)
                    {
                        loginButton.progress = 1
                        loginButton.isEnabled = false
                        loginUserLayout.isEnabled = false
                        loginPasswordLayout.isEnabled = false
                    }

                })
    }

    override fun onBackPressed()
    {
        exitActivity(false)
    }

    private fun exitActivity(success: Boolean)
    {
        if (success)
        {
            val dataIntent = Intent()
            dataIntent.putExtra(getString(R.string.token), token!!.data!!)
            setResult(resources.getInteger(R.integer.login_sucess))

        } else
        {
            setResult(resources.getInteger(R.integer.login_failed))
        }
        finish()
    }

    private fun loginFailed(message: String)
    {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
        loginButton.progress = -1
        loginButton.isEnabled = true
        loginUserLayout.isEnabled = true
        loginPasswordLayout.isEnabled = true
    }

}
