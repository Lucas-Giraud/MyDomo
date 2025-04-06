package com.example.mydomo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class AddUserPage : AppCompatActivity() {
    private lateinit var token : String
    private lateinit var usersAdapter: UsersAdapter
    private var Users : ArrayList<UserData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_user_page)
        token = intent.getStringExtra("token").toString()
        usersAdapter = UsersAdapter(this, Users)
        val listView = findViewById<ListView>(R.id.listUsers)
        listView.adapter = usersAdapter
        getUsers()
    }

    fun getUsers(){
        Api().get<List<UserData>>("https://polyhome.lesmoulinsdudev.com/api/users", ::getUsersSuccess, token)
    }

    fun getUsersSuccess(responseCode: Int, loadedUsers: List<UserData>?){
        if (responseCode == 200 && loadedUsers != null){
            Users.addAll(loadedUsers)
            runOnUiThread {
                usersAdapter.notifyDataSetChanged()
            }
        }
    }

    fun goBack(view: View){
        finish()
    }
}