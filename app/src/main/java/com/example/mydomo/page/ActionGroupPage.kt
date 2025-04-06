package com.example.mydomo.page

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mydomo.Api
import com.example.mydomo.R

class ActionGroupPage : AppCompatActivity() {
    private lateinit var token : String
    private lateinit var houseID : String
    private lateinit var group : String
    private lateinit var devices : Array<String>
    private lateinit var launcher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.action_group_page)
        token = intent.getStringExtra("token").toString()
        group = intent.getStringExtra("group").toString()
        initializeValues()
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            finish()
        }
    }

    fun initializeValues(){
        val splitted = group.split(";")
        houseID = splitted[1]
        devices = splitted[3].split(",").toTypedArray()
        findViewById<TextView>(R.id.lblActionGroup).text = "Actions du groupe ${splitted[2]}"
    }

    fun openOrLightOn(view: View){
        for(device in devices){
            var command: String
            if(device.split(" ")[0].equals("Light")){
                command = "TURN ON"
            }
            else{
                command = "OPEN"
            }
            val data = mapOf("command" to command)
            Api().post("https://polyhome.lesmoulinsdudev.com/api/houses/$houseID/devices/$device/command", data, {responseCode -> }, token)
        }
    }

    fun Stop(view: View){
        for(device in devices){

            if(!device.split(" ")[0].equals("Light")){
                val command = "STOP"
                val data = mapOf("command" to command)
                Api().post("https://polyhome.lesmoulinsdudev.com/api/houses/$houseID/devices/$device/command", data, {responseCode -> }, token)
            }
        }
    }

    fun closeOrLightOff(view: View){
        for(device in devices){
            var command: String
            if(device.split(" ")[0].equals("Light")){
                command = "TURN OFF"
            }
            else{
                command = "CLOSE"
            }
            val data = mapOf("command" to command)
            Api().post("https://polyhome.lesmoulinsdudev.com/api/houses/$houseID/devices/$device/command", data, {responseCode -> }, token)
        }
    }

    fun goToUpdateGroupPage(view: View){
        val intent = Intent(this, UpdateGroupPage::class.java)
        intent.putExtra("token", token)
        intent.putExtra("group", group)
        launcher.launch(intent)
    }

    fun goBack(view: View){
        finish()
    }


}
