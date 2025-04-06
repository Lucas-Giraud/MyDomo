package com.example.mydomo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.mydomo.data.UserData
import com.example.mydomo.R

class UsersAdapter (
    private val context: Context,
    private val dataSource : ArrayList<UserData>
) : BaseAdapter() {
    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): UserData {
        return dataSource[position]
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.user, parent, false)
        rowView.findViewById<TextView>(R.id.lblUserLogin).text = this.getItem(position).login
        return rowView
    }

}