package com.prosjekt.prosjekt_1.lists

import android.content.Context
import com.android.volley.RequestQueue
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

    fun delItem(item: Item) {
        ItemColection.remove(item)
        onItems?.invoke(ItemColection)
    }

    companion object {
        val instance = ItemDepositoryManager()
    }

}