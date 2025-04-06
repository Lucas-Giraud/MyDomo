package com.example.mydomo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.view.View
import android.widget.EditText

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun registerNewAccount(view: View)
    {
        val intent = Intent(this, RegisterPage::class.java);
        startActivity(intent);
    }

    fun loginSuccess(responseCode : Int, td:TokenData?)
    {
        if(responseCode == 200)
        {
            val intent = Intent(this, HousesPage::class.java)
            intent.putExtra("token", td?.token)
            startActivity(intent)
        }
    }

    fun login(view: View)
    {
        val ld = LoginData(
            findViewById<EditText>(R.id.txtLogin).text.toString(),
            findViewById<EditText>(R.id.txtPwd).text.toString()
        )
        Api().post<LoginData, TokenData>("https://polyhome.lesmoulinsdudev.com/api/users/auth", ld, ::loginSuccess)
    }
}