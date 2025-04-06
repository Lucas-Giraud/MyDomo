package com.example.mydomo.page

import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.mydomo.Api
import com.example.mydomo.data.UserData
import com.example.mydomo.R
import com.example.mydomo.data.UserToSendData
import com.example.mydomo.adapter.UsersAdapter

class UsersPage : AppCompatActivity() {
    private lateinit var token : String
    private lateinit var login : String
    private lateinit var houseID : String
    private lateinit var usersAdapter: UsersAdapter
    private var Users : ArrayList<UserData> = ArrayList()
    private lateinit var userToAdd : UserToSendData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.users_page)
        token = intent.getStringExtra("token").toString()
        login = intent.getStringExtra("login").toString()
        houseID = intent.getStringExtra("houseID").toString()

        usersAdapter = UsersAdapter(this, Users)
        val listView = findViewById<ListView>(R.id.listUsers)
        listView.adapter = usersAdapter
        listView.setOnItemClickListener{ parent, view, position, id ->
            val adapter = parent?.adapter as? UsersAdapter
            val item = adapter?.getItem(position)

            if(item !=null){
                userToAdd = UserToSendData(item.login)
                AddUser()
            }

        }

        getUsers()
    }

    fun getUsers(){
        Api().get<List<UserData>>("https://polyhome.lesmoulinsdudev.com/api/users", ::getUsersSuccess, token)
    }

    fun getUsersSuccess(responseCode: Int, loadedUsers: List<UserData>?){
        if (responseCode == 200 && loadedUsers != null){
            Users.addAll(loadedUsers.filter { it.login != login })
            runOnUiThread {
                usersAdapter.notifyDataSetChanged()
            }
        }
    }

    fun AddUser(){
        Api().post<UserToSendData>("https://polyhome.lesmoulinsdudev.com/api/houses/$houseID/users", UserToSendData(userToAdd.userLogin), ::AddUserSuccess, token)
    }

    fun AddUserSuccess(responseCode: Int){
        if (responseCode==200){
            Users.remove(UserData(userToAdd.userLogin))
            runOnUiThread{
                usersAdapter.notifyDataSetChanged()
            }
        }
    }

    fun goBack(view: View){
        finish()
    }
}