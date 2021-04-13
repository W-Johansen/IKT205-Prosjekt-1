package com.prosjekt.prosjekt_1.lists

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.List
import com.prosjekt.prosjekt_1.lists.data.IList
import com.prosjekt.prosjekt_1.databinding.IlistLayoutBinding
import kotlin.math.roundToInt


class IListCollectionAdapter(private var Lists:List<IList>, private val onIListClicked:(IList) -> Unit, private val onIListDelete:(IList) -> Unit) : RecyclerView.Adapter<IListCollectionAdapter.ViewHolder>(){

    class ViewHolder(val binding:IlistLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(ilist: IList, onIListClicked:(IList) -> Unit, onIListDelete: (IList) -> Unit) {
            binding.listName.setText(ilist.name)

            binding.listProgress.progress = (ilist.getPercent() * 100).roundToInt()

            binding.card.setOnClickListener {
                onIListClicked(ilist)
            }

            binding.listName.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    ilist.name = v.text.toString()

                    val ipm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    ipm.hideSoftInputFromWindow(v?.windowToken, 0)

                    true
                } else {
                    false
                }
            }

            binding.delListBtn.setOnClickListener{
                onIListDelete(ilist)
            }
        }
    }

    override fun getItemCount(): Int = Lists.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val iList = Lists[position]
        holder.bind(iList, onIListClicked, onIListDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(IlistLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    public fun updateCollection(newILists:List<IList>){
        Lists = newILists
        notifyDataSetChanged()
    }

    public fun refresh(){
        notifyDataSetChanged()
    }
}