package com.example.acera.theforum

import android.os.Bundle
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

    fun loginAction(username: String, userPassword: String)
    {
        ServiceFactory.myService
                .signIn(Json.User(null, username, userPassword, null, null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Json.MessageToken>
                {
                    override fun onNext(t: Json.MessageToken)
                    {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onError(e: Throwable)
                    {
                        Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                        loginButton.progress = -1
                        loginButton.isEnabled = true
                        loginUserLayout.isEnabled = true
                        loginPasswordLayout.isEnabled = true
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onComplete()
                    {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        if (true)
                        {
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

                    override fun onSubscribe(d: Disposable)
                    {
                        loginButton.progress = 1
                        loginButton.isEnabled = false
                        loginUserLayout.isEnabled = false
                        loginPasswordLayout.isEnabled = false
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })

    }
}
