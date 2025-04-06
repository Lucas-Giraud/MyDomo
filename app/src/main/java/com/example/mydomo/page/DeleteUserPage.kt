package com.example.mydomo.page

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mydomo.Api
import com.example.mydomo.R
import com.example.mydomo.data.UserToSendData

class DeleteUserPage : AppCompatActivity() {
    private lateinit var token : String
    private lateinit var houseID : String
    private lateinit var userLogin : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_user_page)

        token = intent.getStringExtra("token").toString()
        houseID = intent.getStringExtra("houseID").toString()
        userLogin = intent.getStringExtra("userLogin").toString()
    }

    fun Oui(view: View){
        Api().delete<UserToSendData>("https://polyhome.lesmoulinsdudev.com/api/houses/$houseID/users", UserToSendData(userLogin), ::DeleteUserSuccess, token)
    }

    fun DeleteUserSuccess(responseCode : Int){
        if(responseCode == 200){
            finish()
        }
    }

    fun Non(view: View){
        finish()
    }
}