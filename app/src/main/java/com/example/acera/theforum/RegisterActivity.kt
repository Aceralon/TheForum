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
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity()
{

    var msg: Json.Message? = null
    var userName = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        title = "Register"

        registerButton.setMode(ActionProcessButton.Mode.ENDLESS)
        registerButton.setColorScheme(
                getColor(R.color.material_light_blue_700),
                getColor(R.color.material_red_700),
                getColor(R.color.material_orange_700),
                getColor(R.color.material_light_green_700))
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
//            registerButton.progress = 1
//            registerButton.isEnabled = false
//            registerUserLayout.isEnabled = false
//            registerPasswordLayout.isEnabled = false
//            registerConfirmLayout.isEnabled = false
//            registerButton.progress = 100
//            try
//            {
//                Thread.sleep(2000)
//            } catch (e: InterruptedException)
//            {
//                e.printStackTrace()
//            }
            userName = userStr
            registerAction(userStr, passwordStr)
        }
    }

    private fun registerAction(username: String, password: String)
    {
        ServiceFactory.myService
                .register(Json.User(null, username, password, null, null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Json.Message>
                {
                    override fun onNext(t: Json.Message)
                    {
                        msg = t
                    }

                    override fun onError(e: Throwable)
                    {
                        registerFailed("Register failed!")
                        e.printStackTrace()
                    }

                    override fun onComplete()
                    {
                        if (msg!!.state == "1")
                        {
                            registerFailed(msg!!.message)
                        } else
                        {
                            registerButton.progress = 100
//                            Toast.makeText(this@RegisterActivity, "Register successfully!", Toast.LENGTH_SHORT).show()
                            toLoginActivity()
                        }
                    }

                    override fun onSubscribe(d: Disposable)
                    {
                        registerButton.progress = 1
                        registerButton.isEnabled = false
                        registerUserLayout.isEnabled = false
                        registerPasswordLayout.isEnabled = false
                        registerConfirmLayout.isEnabled = false
                    }
                })
    }

    private fun registerFailed(message: String)
    {
        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
        registerButton.progress = -1
        registerButton.isEnabled = true
        registerUserLayout.isEnabled = true
        registerPasswordLayout.isEnabled = true
        registerConfirmLayout.isEnabled = true
    }

    private fun toLoginActivity()
    {
        val myIntent = Intent(this, LoginActivity::class.java)
        myIntent.putExtra(getString(R.string.userName), userName)
        startActivity(myIntent)
        Handler().postDelayed({ finish() }, 50)
    }
}
