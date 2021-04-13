package com.prosjekt.prosjekt_1.lists

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.prosjekt.prosjekt_1.EXTRA_ILIST_INFO
import com.prosjekt.prosjekt_1.IListHolder
import com.prosjekt.prosjekt_1.MainActivity
import com.prosjekt.prosjekt_1.R
import com.prosjekt.prosjekt_1.databinding.ActivityIlistDetailsBinding
import com.prosjekt.prosjekt_1.lists.data.IList
import com.prosjekt.prosjekt_1.lists.data.Item
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.ilist_layout.view.*
import kotlin.math.roundToInt

class ItemHolder{
    companion object{
        var PickedItem:Item? = null
    }
}

class IListDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIlistDetailsBinding
    private lateinit var iList: IList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIlistDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.itemListing.layoutManager = LinearLayoutManager(this)
        binding.itemListing.adapter = ItemCollectionAdapter(emptyList<Item>(), this::onItemChecked)

        ItemDepositoryManager.instance.onItems = {
            (binding.itemListing.adapter as ItemCollectionAdapter).updateCollection(it)
        }

        val receivediList = IListHolder.PickedIList

        if(receivediList != null){
            iList = receivediList
            Log.i("Details view", receivediList.toString())
        } else{

            setResult(RESULT_CANCELED, Intent(EXTRA_ILIST_INFO).apply {
                // leg til info vi vil sende tilbake til Main
            })

            finish()
        }

        ItemDepositoryManager.instance.load(iList, this)

        binding.headerTitle.text = iList.name
        binding.listProgress.progress = (iList.getPercent() * 100).roundToInt()

    }

    private fun addItem(name:String) {

        val item = Item(name, false)
        ItemDepositoryManager.instance.addItem(item)

    }
    private fun onItemChecked(): Unit {

        binding.listProgress.progress = (iList.getPercent() * 100).roundToInt()

        /*val intent =Intent(this, BookDetailsActivity::class.java).apply {
            putExtra(EXTRA_BOOK_INFO, book)
        }*/

        //ItemHolder.PickedItem = item

        //val intent = Intent(this, IListDetailsActivity::class.java)

        //startActivity(intent)
        //startActivityForResult(intent, REQUEST_BOOK_DETAILS)
    }
}
