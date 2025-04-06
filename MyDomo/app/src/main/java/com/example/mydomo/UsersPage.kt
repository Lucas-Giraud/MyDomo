package com.example.mydomo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class UsersPage : AppCompatActivity() {
    private lateinit var token : String
    private lateinit var houseID : String
    private lateinit var usersAdapter : UsersAdapter
    private var Users : ArrayList<UserData> = ArrayList<UserData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.users_page)
        token = intent.getStringExtra("token").toString()
        houseID = intent.getStringExtra("ID").toString()
        usersAdapter = UsersAdapter(this, Users)
        val listView = findViewById<ListView>(R.id.listUsers)
        listView.adapter = usersAdapter

        getUsers()
    }

    fun getUsers(){
        Api().get<List<UserData>>("https://polyhome.lesmoulinsdudev.com/api/houses/$houseID/users", ::getUsersSuccess, token)
    }

    fun getUsersSuccess(responseCode: Int, loadedUsers : List<UserData>?){
        if (responseCode == 200 && loadedUsers != null){
            Users.addAll(loadedUsers)
            runOnUiThread {
                usersAdapter.notifyDataSetChanged()
            }
        }
    }

    fun goToAddUserPage(view: View){
        val intent = Intent(this, AddUserPage::class.java)
        intent.putExtra("token", token)
        startActivity(intent)
    }

    fun goBack(view: View){
        finish()
    }
}