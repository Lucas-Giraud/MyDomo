package com.example.mydomo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

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

        rowView.findViewById<TextView>(R.id.lblPrenom).text = this.getItem(position).userLogin
        if(this.getItem(position).owner != null)
            rowView.findViewById<TextView>(R.id.lblOwner).text = this.getItem(position).owner.toString()
        else
            rowView.findViewById<TextView>(R.id.lblOwner).text = ""
        return rowView
    }
}