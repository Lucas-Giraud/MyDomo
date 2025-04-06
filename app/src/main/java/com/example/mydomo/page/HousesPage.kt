package com.example.mydomo.page

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.mydomo.Api
import com.example.mydomo.data.HouseData
import com.example.mydomo.adapter.HousesAdapter
import com.example.mydomo.R


class HousesPage : AppCompatActivity() {
    private var houses : ArrayList<HouseData> = ArrayList()
    private lateinit var housesAdapter : HousesAdapter
    private lateinit var token : String
    private lateinit var login : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.houses_page)
        token = intent.getStringExtra("token").toString()
        login = intent.getStringExtra("login").toString()
        housesAdapter = HousesAdapter(this, houses)


        val listView = findViewById<ListView>(R.id.listHouses)
        listView.adapter = housesAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
            val adapter = parent?.adapter as? HousesAdapter
            val item = adapter?.getItem(position)

            if (item != null) {
                val intent = Intent(this, SwitchPage::class.java)
                intent.putExtra("token", token)
                intent.putExtra("houseID", item.houseId)
                intent.putExtra("owner", item.owner)
                intent.putExtra("login", login)
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
            runOnUiThread{
                housesAdapter.notifyDataSetChanged()
            }
        }
    }

    fun goBack(view: View){
        finish()
    }
}