package com.example.mydomo.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.mydomo.Api
import com.example.mydomo.data.RegisterData
import com.example.mydomo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        if(responseCode == 409){
            runOnUiThread{
                Toast.makeText(this, "Le nom d'utilisateur est déjà utilisé", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun goToLogin(view: View)
    {
        finish();
    }
}