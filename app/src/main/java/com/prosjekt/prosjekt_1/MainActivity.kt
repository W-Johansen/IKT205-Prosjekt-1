package com.prosjekt.prosjekt_1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.prosjekt.prosjekt_1.databinding.ActivityMainBinding
import com.prosjekt.prosjekt_1.lists.IListCollectionAdapter
import com.prosjekt.prosjekt_1.lists.IListDepositoryManager
import com.prosjekt.prosjekt_1.lists.IListDetailsActivity
import com.prosjekt.prosjekt_1.lists.data.IList
import com.prosjekt.prosjekt_1.lists.data.Item
import kotlinx.android.synthetic.main.activity_main.view.*

const val EXTRA_ILIST_INFO: String = "com.prosjekt.prosjekt_1.lists.info"
const val REQUEST_LIST_DETAILS:Int = 1

class IListHolder{
    companion object{
        var PickedIList:IList? = null
    }
}

class MainActivity : AppCompatActivity() {

    private val TAG:String = "Prosjekt_1:MainActivity"

    private lateinit var binding:ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var userId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        signInAnonymously()

        binding.listListing.layoutManager = LinearLayoutManager(this)
        binding.listListing.adapter = IListCollectionAdapter(emptyList<IList>(), this::onIListClicked, this::onIListDelete)

        IListDepositoryManager.instance.onILists = {
            (binding.listListing.adapter as IListCollectionAdapter).updateCollection(it)
        }

        // TODO: Fix loading before signInAnonymously() completes
        //IListDepositoryManager.instance.load(getExternalFilesDir(null), "${userId}.json")

        binding.newListBtn.setOnClickListener {
            addIList("example")

            val ipm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            ipm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        }

        binding.syncMenu.setOnClickListener {

            val popup = PopupMenu(this@MainActivity, binding.syncMenu)
            //Inflating the Popup using xml file
            popup.menuInflater.inflate(R.menu.sync_menu, popup.menu)


            popup.setOnMenuItemClickListener{
                when(it.itemId){
                    R.id.save ->{
                        // TODO: Add toast to successes or failures
                        IListDepositoryManager.instance.saveLists(IListDepositoryManager.instance.getLists(), getExternalFilesDir(null), "${userId}.json")
                    }
                    R.id.Upload -> {
                        IListDepositoryManager.instance.saveLists(IListDepositoryManager.instance.getLists(), getExternalFilesDir(null), "${userId}.json")
                        IListDepositoryManager.instance.upload(getExternalFilesDir(null), "${userId}.json")
                    }
                    R.id.Download -> {
                        IListDepositoryManager.instance.download(getExternalFilesDir(null), "${userId}.json")
                    }
                }
                true
            }
            popup.show()
        }
    }

    private fun signInAnonymously(){
        auth.signInAnonymously().addOnSuccessListener {
            Log.d(TAG, "Login success ${it.user.toString()}")
            userId = it.user.uid.toString()
        }.addOnFailureListener {
            Log.e(TAG, "login failed", it)
        }
    }

    private fun addIList(name:String) {

        val iList = IList(name, mutableListOf(Item("Item 1", false)))
        IListDepositoryManager.instance.addIList(iList)

    }

    private fun delIList(iList:IList) {
        IListDepositoryManager.instance.delIList(iList)
    }

    private fun onIListClicked(iList:IList): Unit {

        /*val intent =Intent(this, BookDetailsActivity::class.java).apply {
            putExtra(EXTRA_BOOK_INFO, book)
        }*/

        IListHolder.PickedIList = iList

        val intent = Intent(this, IListDetailsActivity::class.java)

        //startActivity(intent)
        startActivityForResult(intent, REQUEST_LIST_DETAILS)
    }

    private fun onIListDelete(iList:IList): Unit {
        delIList(iList)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_LIST_DETAILS){
            (binding.root.listListing.adapter as IListCollectionAdapter).refresh()
        }

    }
}