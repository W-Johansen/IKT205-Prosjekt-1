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

class ItemDepositoryManager {

    private lateinit var ItemColection: MutableList<Item>
    private lateinit var queue: RequestQueue

    var onItems: ((List<Item>) -> Unit)? = null
    var onItemUpdate: ((item:Item) -> Unit)? = null


    fun load(iList:IList, context: Context) {
        ItemColection = iList.items

        onItems?.invoke(ItemColection)
    }

    fun updateItem(item: Item) {
        onItemUpdate?.invoke(item)
    }

    fun addItem(item: Item) {
        ItemColection.add(item)
        onItems?.invoke(ItemColection)
    }

    companion object {
        val instance = ItemDepositoryManager()
    }

}