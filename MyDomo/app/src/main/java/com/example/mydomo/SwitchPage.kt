package com.example.mydomo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SwitchPage : AppCompatActivity() {
    private lateinit var token : String
    private lateinit var houseID : String
    private var owner : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.switch_page)
        token = intent.getStringExtra("token").toString()
        owner = intent.getBooleanExtra("owner", false)
        houseID = intent.getStringExtra("ID").toString()

        if(owner)
            findViewById<Button>(R.id.btnUsers).visibility = View.VISIBLE
        else
            findViewById<Button>(R.id.btnUsers).visibility = View.GONE

    }

    fun goToUsersPage(view: View){
        val intent = Intent(this, UsersPage::class.java)
        intent.putExtra("token", token)
        intent.putExtra("ID", houseID)
        startActivity(intent)
    }

    fun goToDevicesPage(view: View){
        val intent = Intent(this, DevicesPage::class.java)
        intent.putExtra("token", token)
        intent.putExtra("ID", houseID)
        startActivity(intent)
    }

    fun goBack(view: View){
        finish()
    }
}