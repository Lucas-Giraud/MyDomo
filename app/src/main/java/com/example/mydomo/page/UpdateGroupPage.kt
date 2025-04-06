package com.example.mydomo.page

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.ExpandedMenuView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mydomo.Api
import com.example.mydomo.R
import com.example.mydomo.adapter.DevicesGroupAdapter
import com.example.mydomo.adapter.GroupAdapter
import com.example.mydomo.adapter.UpdateGroupAdapter
import com.example.mydomo.data.DeviceData
import com.example.mydomo.data.DevicesData
import com.example.mydomo.data.GroupStorage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class UpdateGroupPage : AppCompatActivity() {
    private lateinit var token : String
    private lateinit var houseID : String
    private lateinit var login : String
    private lateinit var groupDevices : ArrayList<String>
    private lateinit var devices : ArrayList<DeviceData>
    private val groupStorage = GroupStorage(this)
    private val mainScope = MainScope()
    private var groups : ArrayList<String> = ArrayList()
    private lateinit var updateGroupAdapter : UpdateGroupAdapter
    private lateinit var listView: ListView
    private lateinit var oldGroup : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_group_page)
        token = intent.getStringExtra("token").toString()
        oldGroup = intent.getStringExtra("group").toString()
        val splitted = oldGroup.split(";")
        houseID = splitted[1]
        login = splitted[0]
        groupDevices = ArrayList(splitted[3].split(","))
        findViewById<TextView>(R.id.txtUpdateGroupName).text = splitted[2]

        mainScope.launch {
            groups.clear()
            groups.addAll(groupStorage.read().filter{it.isNotEmpty() && it.split(";")[1].equals(houseID)} )
            groups.remove(oldGroup)
        }

        listView = findViewById(R.id.listUpdateGroupDevices)
        listView.setOnItemClickListener { parent, view, position, id ->
            val adapter = parent?.adapter as? UpdateGroupAdapter
            val item = adapter?.getItem(position)
            val txtView = findViewById<TextView>(R.id.lblAdded)
            if(item != null) {
                if (groupDevices.contains(item.id)) {
                    groupDevices.remove(item.id)
                    txtView.text = "Retirer"
                    txtView.setBackgroundColor(Color.RED)
                } else {
                    groupDevices.add(item.id)
                    txtView.text = "Ajouter"
                    txtView.setBackgroundColor(Color.parseColor("#673AB7"))
                }
                runOnUiThread {
                    updateGroupAdapter.notifyDataSetChanged()
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
                updateGroupAdapter = UpdateGroupAdapter(this, devices, groupDevices)
                listView.adapter = updateGroupAdapter
            }
        }

    }

    fun validate(view: View){
        val groupName = findViewById<EditText>(R.id.txtUpdateGroupName).text.toString()
        if(groupName.isNotEmpty() && groupDevices.isNotEmpty()) {
            mainScope.launch {
                groupStorage.write(login, houseID, groupDevices, groupName, groups, false)
            }
            finish()
        }
        else{
            runOnUiThread{
                Toast.makeText(this, "Veuillez entrer un nom et séléctionner au moins 1 équipement", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun deleteGroup(view: View){
        mainScope.launch {
            groupStorage.write("", "", ArrayList(), "", groups, true)
        }
        finish()
    }
}