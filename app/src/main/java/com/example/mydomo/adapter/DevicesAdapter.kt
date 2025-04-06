package com.example.mydomo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import com.example.mydomo.Api
import com.example.mydomo.data.DeviceData
import com.example.mydomo.page.OpeningPage
import com.example.mydomo.R

class DevicesAdapter (
    private val context : Context,
    private val dataSource : ArrayList<DeviceData>,
    private val token : String,
    private val houseID : String
) : BaseAdapter() {
    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): DeviceData {
        return dataSource[position]
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView: View?
        val item = this.getItem(position)
        var etage = ""
        val splitted = item.id.split(" ")[1]
        if(splitted.startsWith("1"))
            etage = "du rez-de-chaussé " + splitted.split(".")[1]
        else
            etage = "de l'étage "+ splitted.split(".")[1]

        when(item.type){
            "light" -> {
                rowView = inflater.inflate(R.layout.light, parent, false)
                val text = "Lumière " + etage
                rowView.findViewById<Switch>(R.id.switchLight).text = text
                rowView.findViewById<Switch>(R.id.switchLight).isChecked = item.power == 1
                rowView.findViewById<Switch>(R.id.switchLight).setOnCheckedChangeListener { buttonView, isChecked ->
                    if(isChecked){
                        sendCommand(item.id, "TURN ON")
                    }
                    else{
                        sendCommand(item.id, "TURN OFF")
                    }
                }
            }
            "rolling shutter" -> {
                rowView = inflater.inflate(R.layout.openings, parent, false)
                val text = "Volet " + etage
                rowView.findViewById<TextView>(R.id.lblDeviceName).text = text
                rowView.findViewById<Button>(R.id.btnConsult).setOnClickListener {
                    val intent = Intent(context, OpeningPage::class.java)
                    intent.putExtra("token", token)
                    intent.putExtra("houseID", houseID)
                    intent.putExtra("deviceID", item.id)
                    intent.putExtra("deviceName", text)
                    intent.putExtra("progress", item.opening.toFloat())
                    context.startActivity(intent)
                }
            }
            else -> {
                rowView = inflater.inflate(R.layout.openings, parent, false)
                rowView.findViewById<TextView>(R.id.lblDeviceName).text = "Porte du garage"
                rowView.findViewById<Button>(R.id.btnConsult).setOnClickListener {
                    val intent = Intent(context, OpeningPage::class.java)
                    intent.putExtra("token", token)
                    intent.putExtra("houseID", houseID)
                    intent.putExtra("deviceID", item.id)
                    intent.putExtra("deviceName", "Porte du garage")
                    intent.putExtra("progress", item.opening.toFloat())
                    context.startActivity(intent)
                }
            }
        }

        return rowView
    }

    private fun sendCommand(deviceId: String, command: String) {
        val url = "https://polyhome.lesmoulinsdudev.com/api/houses/$houseID/devices/$deviceId/command"
        val data = mapOf("command" to command)
        Api().post(url, data, { ResponseCode -> }, token)
    }
}