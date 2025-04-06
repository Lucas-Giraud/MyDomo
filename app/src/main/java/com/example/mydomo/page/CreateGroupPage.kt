package com.example.mydomo.page

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mydomo.Api
import com.example.mydomo.data.GroupStorage
import com.example.mydomo.R
import com.example.mydomo.adapter.DevicesGroupAdapter
import com.example.mydomo.data.DeviceData
import com.example.mydomo.data.DevicesData
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class CreateGroupPage : AppCompatActivity() {
    private lateinit var token : String
    private lateinit var houseID : String
    private lateinit var login : String
    private lateinit var devicesGroupAdapter : DevicesGroupAdapter
    private lateinit var listView: ListView
    private var devices : ArrayList<DeviceData> = ArrayList()
    private var group : ArrayList<String> = ArrayList()
    private val mainScope = MainScope()
    private val groupStorage = GroupStorage(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_group_page)
        token = intent.getStringExtra("token").toString()
        houseID = intent.getStringExtra("houseID").toString()
        login = intent.getStringExtra("login").toString()

        listView = findViewById(R.id.listGroupDevices)
        listView.setOnItemClickListener { parent, view, position, id ->
            val adapter = parent?.adapter as? DevicesGroupAdapter
            val item = adapter?.getItem(position)
            if(item != null) {
                val label = view.findViewById<TextView>(R.id.lblAdded)
                if (label.text.equals("Ajouter")) {
                    group.add(item.id)
                    label.text = "Retirer"
                    label.setBackgroundColor(Color.RED)
                } else {
                    group.remove(item.id)
                    label.text = "Ajouter"
                    label.setBackgroundColor(Color.parseColor("#673AB7"))
                }

                runOnUiThread{
                    devicesGroupAdapter.notifyDataSetChanged()
                }
            }
        }

        getDevices()
    }

    fun getDevices(){
        Api().get<DevicesData>("https://polyhome.lesmoulinsdudev.com/api/houses/$houseID/devices", ::getDevicesSuccess, token)
    }

    fun getDevicesSuccess(responseCode : Int, loadedDevices : DevicesData?){
        if (responseCode == 200 && loadedDevices!= null ){
            devices = loadedDevices.devices
            runOnUiThread {
                devicesGroupAdapter = DevicesGroupAdapter(this, devices, group)
                listView.adapter = devicesGroupAdapter
            }
        }

    }

    fun validate(view: View){
        val groupName = findViewById<EditText>(R.id.txtGroupName).text.toString()
        if(groupName.isNotEmpty() && group.isNotEmpty()) {
            mainScope.launch {
                groupStorage.write(login, houseID, group, groupName, groupStorage.read(), false)
            }
            finish()
        }
        else{
            runOnUiThread{
                Toast.makeText(this, "Veuillez entrer un nom et séléctionner au moins 1 équipement", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun goBack(view: View){
        finish()
    }
}