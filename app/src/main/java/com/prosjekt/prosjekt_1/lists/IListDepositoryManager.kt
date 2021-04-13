package com.prosjekt.prosjekt_1.lists

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlin.collections.List
import kotlin.collections.listOf
import com.prosjekt.prosjekt_1.lists.data.IList
import com.prosjekt.prosjekt_1.lists.data.Item

class IListDepositoryManager {

    private lateinit var IListCollection: MutableList<IList>
    private lateinit var queue: RequestQueue

    var onILists: ((List<IList>) -> Unit)? = null
    var onIListUpdate: ((iList:IList) -> Unit)? = null


    fun load(url: String, context: Context) {

        /*  queue = Volley.newRequestQueue(context)

          val request = JsonArrayRequest(Request.Method.GET, url, null,
              {

                  // JSON -> transport formatet
                  // Gson -> Manipulering og serialisering av json

                  Log.d("BookDepositoryManager", it.toString(3))
              },
              {
                  Log.e("BookDepositoryManager", it.toString())
              })

          queue.add(request)*/


        IListCollection = mutableListOf(
            IList("Liste 1", mutableListOf(Item("Test1-1", true), Item("Test1-2", false), Item("Test1-3", false))),
            IList("Liste 2", mutableListOf(Item("Test2-1", false), Item("Test2-2", false))),
            IList("Liste 3", mutableListOf(Item("Test3-1", false)))
        )

        onILists?.invoke(IListCollection)
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