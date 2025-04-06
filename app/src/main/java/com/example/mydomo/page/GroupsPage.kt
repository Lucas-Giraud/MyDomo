package com.example.mydomo.page

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.mydomo.adapter.GroupAdapter
import com.example.mydomo.data.GroupStorage
import com.example.mydomo.R
import com.example.mydomo.adapter.DevicesGroupAdapter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class GroupsPage : AppCompatActivity() {
    private lateinit var token : String
    private lateinit var houseID : String
    private lateinit var login : String
    private var groups : ArrayList<String> = ArrayList()
    private val mainScope = MainScope()
    private val groupStorage = GroupStorage(this)
    private lateinit var groupAdapter : GroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.groups_page)
        token = intent.getStringExtra("token").toString()
        houseID = intent.getStringExtra("houseID").toString()
        login = intent.getStringExtra("login").toString()

        groupAdapter = GroupAdapter(this, groups)
        val listView = findViewById<ListView>(R.id.listGroups)
        listView.adapter = groupAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
            val adapter = parent?.adapter as? GroupAdapter
            val item = adapter?.getItem(position)

            if(item != null){
                val intent = Intent(this, ActionGroupPage::class.java)
                intent.putExtra("token", token)
                intent.putExtra("houseID", houseID)
                intent.putExtra("group", item)
                startActivity(intent)
            }
        }
    }

    fun loadGroupList(){
        mainScope.launch {
            groups.clear()
            groups.addAll(groupStorage.read().filter{it.isNotEmpty() && it.split(";")[0].equals(login) && it.split(";")[1].equals(houseID)} )
            groupAdapter.notifyDataSetChanged()
        }
    }

    fun goCreateGroupPage(view: View){
        val intent = Intent(this, CreateGroupPage::class.java)
        intent.putExtra("token", token)
        intent.putExtra("houseID", houseID)
        intent.putExtra("login",login)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        loadGroupList()
    }

    fun goBack(view: View){
        finish()
    }
}