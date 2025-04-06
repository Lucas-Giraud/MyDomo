package com.example.mydomo.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.mydomo.R
import com.example.mydomo.data.DeviceData

class UpdateGroupAdapter  (
    private val context: Context,
    private val dataSource : ArrayList<DeviceData>,
    private val devices : ArrayList<String>
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
        val rowView = inflater.inflate(R.layout.group_device, parent, false)
        val item = this.getItem(position)
        var etage = ""
        val itemSplitted = item.id.split(" ")[1]
        if(itemSplitted.startsWith("1"))
            etage = "du rez-de-chaussé " + itemSplitted.split(".")[1]
        else
            etage = "de l'étage "+ itemSplitted.split(".")[1]

        when(item.type){
            "light" -> {
                val text = "Lumière " + etage
                rowView.findViewById<TextView>(R.id.lblGroupDeviceName).text = text

            }
            "rolling shutter" -> {
                val text = "Volet " + etage
                rowView.findViewById<TextView>(R.id.lblGroupDeviceName).text = text
            }
            else -> {
                rowView.findViewById<TextView>(R.id.lblGroupDeviceName).text = "Porte du garage"
            }
        }

        val txtView = rowView.findViewById<TextView>(R.id.lblAdded)
        if(devices.contains(item.id) ){
            txtView.text = "Retirer"
            txtView.setBackgroundColor(Color.RED)
        }
        else{
            txtView.text = "Ajouter"
            txtView.setBackgroundColor(Color.parseColor("#673AB7"))
        }
        txtView.setTextColor(Color.WHITE)

        return rowView
    }
}