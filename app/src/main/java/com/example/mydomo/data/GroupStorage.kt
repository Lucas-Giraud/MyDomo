package com.example.mydomo.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull

private val Context.groupStore by preferencesDataStore(name = "groups")

class GroupStorage {
    private var context : Context
    private var groupsKey = stringPreferencesKey("groups")

    constructor(c: Context)
    {
        context = c
    }

    suspend fun write(login : String, houseID : String,group: ArrayList<String>, groupName : String, groups : ArrayList<String>, delete : Boolean)
    {
        if(!delete){
            val data = "$login;$houseID;$groupName;"+ group.joinToString(",")?: ""
            groups.add(data)
        }
        this.context.groupStore.edit {
            preferences -> preferences[groupsKey] = groups.joinToString("/")
        }
    }

    suspend fun read() : ArrayList<String>
    {
        val data = context.groupStore.data.firstOrNull()?.get(groupsKey)
        if (data != null) {
            return ArrayList(data.split("/"))
        } else {
            return ArrayList()
        }
    }
}