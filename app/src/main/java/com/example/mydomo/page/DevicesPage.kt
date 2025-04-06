package com.example.mydomo.page

import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.mydomo.Api
import com.example.mydomo.data.DeviceData
import com.example.mydomo.data.DevicesData
import com.example.mydomo.adapter.DevicesAdapter
import com.example.mydomo.R

class DevicesPage : AppCompatActivity() {
    private lateinit var token : String
    private lateinit var houseID : String
    private lateinit var devicesAdapter : DevicesAdapter
    private var devices : ArrayList<DeviceData> = ArrayList<DeviceData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.devices_page)
        token = intent.getStringExtra("token").toString()
        houseID = intent.getStringExtra("houseID").toString()

        getDevices()
    }

    fun getDevices(){
        Api().get<DevicesData>("https://polyhome.lesmoulinsdudev.com/api/houses/$houseID/devices", ::getDevicesSuccess, token)
    }

    fun getDevicesSuccess(responseCode : Int, loadedDevices : DevicesData?){
        if (responseCode == 200 && loadedDevices!= null ){
            devices = loadedDevices.devices
            runOnUiThread {
                devicesAdapter = DevicesAdapter(this, devices, token, houseID)
                val listView = findViewById<ListView>(R.id.listDevices)
                listView.adapter = devicesAdapter
            }
        }
    }

    fun goToSwitch(view: View)
    {
        finish();
    }

    override fun onResume() {
        super.onResume()
        getDevices()
    }
}