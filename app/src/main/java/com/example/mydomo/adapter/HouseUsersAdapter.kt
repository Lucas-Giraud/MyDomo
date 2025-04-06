package com.example.mydomo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.mydomo.data.HouseUserData
import com.example.mydomo.R

class HouseUsersAdapter (
    private val context: Context,
    private val dataSource : ArrayList<HouseUserData>
) : BaseAdapter() {
    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): HouseUserData {
        return dataSource[position]
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.house_user, parent, false)

        val user = this.getItem(position)
        rowView.findViewById<TextView>(R.id.lblLogin).text = user.userLogin
        if(user.owner.toInt() == 1) {
            rowView.findViewById<TextView>(R.id.lblOwner).text = "Propriétaire"
        } else {
            rowView.findViewById<TextView>(R.id.lblOwner).text = "Invité"
        }

        return rowView
    }
}