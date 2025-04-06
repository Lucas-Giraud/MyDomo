package com.example.mydomo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class HousesPage : AppCompatActivity() {
    private var houses : ArrayList<HouseData> = ArrayList()
    private lateinit var housesAdapter : HousesAdapter
    private lateinit var token : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.houses_page)
        token = intent.getStringExtra("token").toString()
        housesAdapter = HousesAdapter(this, houses)


        val listView = findViewById<ListView>(R.id.listHouses)
        listView.adapter = housesAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
            val adapter = parent?.adapter as? HousesAdapter
            val item = adapter?.getItem(position)

            if (item != null) {
                val intent = Intent(this, SwitchPage::class.java)
                intent.putExtra("token", token)
                intent.putExtra("ID", item.houseId)
                intent.putExtra("owner", item.owner)
                startActivity(intent)
            }
        }
        getHouses()
    }

    fun getHouses(){
        Api().get<List<HouseData>>("https://polyhome.lesmoulinsdudev.com/api/houses", ::getHousesSuccess, token )
    }

    fun getHousesSuccess(responseCode: Int, loadedHouses: List<HouseData>?){
        if(responseCode == 200 && loadedHouses != null)
        {
            houses.addAll(loadedHouses)
            houses.add(HouseData("2", false))
            housesAdapter.notifyDataSetChanged()

        }
    }

    fun goBack(view: View){
        finish()
    }
}