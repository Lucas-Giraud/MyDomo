package com.example.mydomo.page

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.mydomo.Api
import com.example.mydomo.data.HouseUserData
import com.example.mydomo.adapter.HouseUsersAdapter
import com.example.mydomo.R

class HouseUsersPage : AppCompatActivity() {
    private lateinit var token : String
    private lateinit var houseID : String
    private lateinit var login : String
    private lateinit var houseUsersAdapter : HouseUsersAdapter
    private var Users : ArrayList<HouseUserData> = ArrayList<HouseUserData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.house_users_page)
        token = intent.getStringExtra("token").toString()
        houseID = intent.getStringExtra("houseID").toString()
        login = intent.getStringExtra("login").toString()
        houseUsersAdapter = HouseUsersAdapter(this, Users)

        val listView = findViewById<ListView>(R.id.listUsers)
        listView.adapter = houseUsersAdapter
        listView.setOnItemClickListener{ parent, view, position, id ->
            val adapter = parent?.adapter as? HouseUsersAdapter
            val item = adapter?.getItem(position)

            if(item !=null && item.owner.toInt() != 1){
                val intent = Intent(this, DeleteUserPage::class.java)
                intent.putExtra("token", token)
                intent.putExtra("userLogin", item.userLogin)
                intent.putExtra("houseID", houseID)
                startActivity(intent)
            }

        }

        getUsers()
    }

    fun getUsers(){
        Api().get<List<HouseUserData>>("https://polyhome.lesmoulinsdudev.com/api/houses/$houseID/users", ::getUsersSuccess, token)
    }

    fun getUsersSuccess(responseCode: Int, loadedUsers : List<HouseUserData>?){
        if (responseCode == 200 && loadedUsers != null){
            Users.clear()
            Users.addAll(loadedUsers)
            runOnUiThread {
                houseUsersAdapter.notifyDataSetChanged()
            }
        }
    }

    fun goToAddUserPage(view: View){
        val intent = Intent(this, UsersPage::class.java)
        intent.putExtra("token", token)
        intent.putExtra("login", login)
        intent.putExtra("houseID", houseID)
        startActivity(intent)
    }

    fun goBack(view: View){
        finish()
    }

    override fun onResume() {
        super.onResume()
        getUsers()
    }

}