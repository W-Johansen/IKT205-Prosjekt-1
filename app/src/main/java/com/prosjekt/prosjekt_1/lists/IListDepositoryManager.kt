package com.prosjekt.prosjekt_1.lists

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prosjekt.prosjekt_1.MainActivity
import kotlin.collections.List
import kotlin.collections.listOf
import com.prosjekt.prosjekt_1.lists.data.IList
import com.prosjekt.prosjekt_1.lists.data.Item
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class IListDepositoryManager {

    private var IListCollection: MutableList<IList> = mutableListOf(IList("New List", mutableListOf(Item("New Item", false))))
    private lateinit var queue: RequestQueue

    var onILists: ((List<IList>) -> Unit)? = null
    var onIListUpdate: ((iList:IList) -> Unit)? = null

    fun saveLists(lists:MutableList<IList>, path:File?, fileName:String){
        // Object -> JSON
        val outputJson: String = Gson().toJson(lists)

        if (path != null){
            val file = File(path, fileName)
            file.delete()
            FileOutputStream(file, true).bufferedWriter().use { writer ->
                writer.write(outputJson)
            }
        }else {
            // Could not get external path
        }
    }

    fun load(path:File?, fileName:String) {

        lateinit var inputJson:String

        if (path != null){
            val file = File(path, fileName)
            if (file.canRead()){
                inputJson = file.inputStream().bufferedReader().use(BufferedReader::readText)
                // JSON -> Object
                val listType = object : TypeToken<MutableList<IList>>() {}.type
                IListCollection = Gson().fromJson<MutableList<IList>>(inputJson, listType)
            }

        } else {
            // Could not get external path
        }

        onILists?.invoke(IListCollection)
    }

    // TODO: IMPLEMENT
    fun upload(){

    }

    // TODO: IMPLEMENT
    fun download(){

    }

    fun getLists():MutableList<IList>{
        return IListCollection
    }

    fun updateIList(iList: IList) {
        onIListUpdate?.invoke(iList)
    }

    fun addIList(iList: IList) {
        IListCollection.add(iList)
        onILists?.invoke(IListCollection)
    }

    fun delIList(iList: IList) {
        IListCollection.remove(iList)
        onILists?.invoke(IListCollection)
    }

    companion object {
        val instance = IListDepositoryManager()
    }

}