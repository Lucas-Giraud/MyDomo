package com.example.mydomo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class RegisterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)
    }

    fun register(view: View)
    {
        val rd = RegisterData(
            findViewById<EditText>(R.id.txtLoginRegister).text.toString(),
            findViewById<EditText>(R.id.txtPwdRegister).text.toString()
        )
        Api().post<RegisterData>("https://polyhome.lesmoulinsdudev.com/api/users/register", rd, ::registerSuccess)
    }

    fun registerSuccess(responseCode: Int)
    {
        if (responseCode == 200)
        {
            finish()
        }
    }

    fun goToLogin(view: View)
    {
        finish();
    }
}