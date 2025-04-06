package com.example.mydomo

import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class DevicesPage : AppCompatActivity() {
    private lateinit var token : String
    private lateinit var houseID : String
    private lateinit var devicesAdapter : DevicesAdapter
    private lateinit var devices : DevicesData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.devices_page)
        token = intent.getStringExtra("token").toString()
        houseID = intent.getStringExtra("ID").toString()
        devices = DevicesData(ArrayList())
        devicesAdapter = DevicesAdapter(this, devices.devices)
        val listView = findViewById<ListView>(R.id.listDevices)
        listView.adapter = devicesAdapter
        getDevices()
    }

    fun getDevices(){
        Api().get<DevicesData>("https://polyhome.lesmoulinsdudev.com/api/houses/$houseID/devices", ::getDevicesSuccess, token)
    }

    fun getDevicesSuccess(responseCode : Int, loadedDevices : DevicesData?){
        if (responseCode == 200 && loadedDevices!= null ){
            println(loadedDevices)
            devices = loadedDevices
            println(devices.devices)
            runOnUiThread {
                devicesAdapter.notifyDataSetChanged()
            }
        }
    }

    fun goToSwitch(view: View)
    {
        finish();
    }
}