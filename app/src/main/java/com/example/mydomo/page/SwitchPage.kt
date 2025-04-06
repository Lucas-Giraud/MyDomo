package com.example.mydomo.page

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mydomo.R

class SwitchPage : AppCompatActivity() {
    private lateinit var token : String
    private lateinit var houseID : String
    private lateinit var login : String
    private var owner : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.switch_page)
        token = intent.getStringExtra("token").toString()
        owner = intent.getBooleanExtra("owner", false)
        houseID = intent.getStringExtra("houseID").toString()
        login = intent.getStringExtra("login").toString()

        if(owner)
            findViewById<Button>(R.id.btnUsers).visibility = View.VISIBLE
        else
            findViewById<Button>(R.id.btnUsers).visibility = View.GONE

    }

    fun goToHouseUsersPage(view: View){
        val intent = Intent(this, HouseUsersPage::class.java)
        intent.putExtra("token", token)
        intent.putExtra("houseID", houseID)
        intent.putExtra("login", login)
        startActivity(intent)
    }

    fun goToDevicesPage(view: View){
        val intent = Intent(this, DevicesPage::class.java)
        intent.putExtra("token", token)
        intent.putExtra("houseID", houseID)
        startActivity(intent)
    }

    fun goToGroupsPage(view: View){
        val intent = Intent(this, GroupsPage::class.java)
        intent.putExtra("token", token)
        intent.putExtra("houseID", houseID)
        intent.putExtra("login", login)
        startActivity(intent)
    }

    fun goBack(view: View){
        finish()
    }
}