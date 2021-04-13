package com.prosjekt.prosjekt_1.lists

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View.FOCUSABLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import com.prosjekt.prosjekt_1.EXTRA_ILIST_INFO
import com.prosjekt.prosjekt_1.IListHolder
import com.prosjekt.prosjekt_1.R
import com.prosjekt.prosjekt_1.lists.data.IList
import com.prosjekt.prosjekt_1.databinding.ActivityIlistDetailsBinding
import com.prosjekt.prosjekt_1.lists.data.Item
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
        binding.itemListing.adapter = ItemCollectionAdapter(emptyList<Item>(), this::onItemClicked)

        ItemDepositoryManager.instance.onItems = {
            (binding.itemListing.adapter as ItemCollectionAdapter).updateCollection(it)
        }

        //val receivedBook = intent.getParcelableExtra<Book>(EXTRA_BOOK_INFO)
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
        binding.listProgress.progress = (iList.doneProcent * 100).roundToInt()

    }

    private fun addItem(name:String) {

        val item = Item(name, false)
        ItemDepositoryManager.instance.addItem(item)

    }
    private fun onItemClicked(item:Item): Unit {

        /*val intent =Intent(this, BookDetailsActivity::class.java).apply {
            putExtra(EXTRA_BOOK_INFO, book)
        }*/

        //ItemHolder.PickedItem = item

        //val intent = Intent(this, IListDetailsActivity::class.java)

        //startActivity(intent)
        //startActivityForResult(intent, REQUEST_BOOK_DETAILS)
    }
}
