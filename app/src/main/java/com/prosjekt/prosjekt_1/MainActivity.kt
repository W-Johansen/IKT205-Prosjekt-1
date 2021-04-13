package com.prosjekt.prosjekt_1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.prosjekt.prosjekt_1.databinding.ActivityMainBinding
import com.prosjekt.prosjekt_1.lists.IListDepositoryManager
import com.prosjekt.prosjekt_1.lists.IListCollectionAdapter
import com.prosjekt.prosjekt_1.lists.IListDetailsActivity
import com.prosjekt.prosjekt_1.lists.data.IList
import com.prosjekt.prosjekt_1.lists.data.Item

const val EXTRA_ILIST_INFO: String = "com.prosjekt.prosjekt_1.lists.info"
const val REQUEST_LIST_DETAILS:Int = 564567

class IListHolder{
    companion object{
        var PickedIList:IList? = null
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listListing.layoutManager = LinearLayoutManager(this)
        binding.listListing.adapter = IListCollectionAdapter(emptyList<IList>(), this::onIListClicked)

        IListDepositoryManager.instance.onILists = {
            (binding.listListing.adapter as IListCollectionAdapter).updateCollection(it)
        }

        IListDepositoryManager.instance.load(getString(R.string.ilist_listing_url),this)

        binding.newListBtn.setOnClickListener {
            addIList("example")

            val ipm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            ipm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        }
    }

    private fun addIList(name:String) {

        val iList = IList(name, mutableListOf(Item("Item 1", false)))
        IListDepositoryManager.instance.addIList(iList)

    }
    private fun onIListClicked(iList:IList): Unit {

        /*val intent =Intent(this, BookDetailsActivity::class.java).apply {
            putExtra(EXTRA_BOOK_INFO, book)
        }*/

        IListHolder.PickedIList = iList

        val intent = Intent(this, IListDetailsActivity::class.java)

        startActivity(intent)
        //startActivityForResult(intent, REQUEST_BOOK_DETAILS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_LIST_DETAILS){

        }

    }
}