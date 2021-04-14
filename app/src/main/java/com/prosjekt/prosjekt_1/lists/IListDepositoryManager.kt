package com.prosjekt.prosjekt_1.lists

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.storage.FirebaseStorage
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

    private val TAG:String = "Prosjekt_1:IListDepositoryManager"

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
            Log.d(TAG, "File saved")
        }else {
            // Could not get external path
            Log.e(TAG, "Could not get external path")
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
                Log.d(TAG, "File loaded")
            }

        } else {
            Log.d(TAG, "Could not get external path")
        }

        onILists?.invoke(IListCollection)
    }


    fun upload(path:File?, fileName:String){
        val file:Uri = File(path, fileName).toUri()

        Log.d(TAG, "Uploading file ${file}")

        val ref = FirebaseStorage.getInstance().reference.child("lists/${file.lastPathSegment}")
        var uploadTask = ref.putFile(file)

        uploadTask.addOnSuccessListener {
            Log.d(TAG, "Uploaded file ${it.toString()}")

        }.addOnFailureListener{
            Log.e(TAG, "Error saving tile to Firebase")
        }
    }

    fun download(path:File?, fileName:String){
        val file:Uri = File(path, fileName).toUri()

        Log.d(TAG, "Downloading file ${file}")

        val ref = FirebaseStorage.getInstance().reference.child("lists/${file.lastPathSegment}")

        ref.getFile(file)
            .addOnSuccessListener {
                Log.d(TAG, "Downloaded file ${it.toString()}")
                this.load(path, fileName)
            }
            .addOnFailureListener {
                Log.e(TAG, "Error downloading file from Firebase")
            }
            .addOnProgressListener {
                // it.bytesTransferred
                // it.totalByteCount
            }
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