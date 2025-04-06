package com.example.mydomo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class HousesAdapter (
    private val context: Context,
    private val dataSource : ArrayList<HouseData>
) : BaseAdapter() {
    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): HouseData {
        return dataSource[position]
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.house, parent, false)

        rowView.findViewById<TextView>(R.id.lblHouseID).text = this.getItem(position).houseId
        if (this.getItem(position).owner)
            rowView.findViewById<TextView>(R.id.lblProperty).text = "Propriétaire"
        else
            rowView.findViewById<TextView>(R.id.lblProperty).text = "Invité"

        return rowView
    }
}