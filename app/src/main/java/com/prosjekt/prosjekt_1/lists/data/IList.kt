package com.prosjekt.prosjekt_1.lists.data

import kotlinx.android.parcel.Parcelize
import kotlin.collections.List

data class Item (var name:String, var isDone:Boolean)
data class IList (var name:String, var items:MutableList<Item>){

    public fun getPercent():Float {
        var doneCount: Float = 0f
        this.items.forEach {
            if (it.isDone)
                doneCount += 1f
        }
        return doneCount / (this.items.size).toFloat()
    }
}

