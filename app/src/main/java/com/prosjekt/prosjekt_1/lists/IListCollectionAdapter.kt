package com.prosjekt.prosjekt_1.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.List
import com.prosjekt.prosjekt_1.lists.data.IList
import com.prosjekt.prosjekt_1.databinding.IlistLayoutBinding
import kotlin.math.roundToInt


class IListCollectionAdapter(private var Lists:List<IList>, private val onIListClicked:(IList) -> Unit) : RecyclerView.Adapter<IListCollectionAdapter.ViewHolder>(){

    class ViewHolder(val binding:IlistLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(ilist: IList, onIListClicked:(IList) -> Unit) {
            binding.listName.text = ilist.name

            var doneCount:Float = 0f
            ilist.items.forEach{
                if (it.isDone)
                    doneCount += 1f
            }
            ilist.doneProcent = doneCount / (ilist.items.size).toFloat()

            binding.listProgress.progress = (ilist.doneProcent * 100).roundToInt()

            binding.card.setOnClickListener {
                onIListClicked(ilist)
            }
        }
    }

    override fun getItemCount(): Int = Lists.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val iList = Lists[position]
        holder.bind(iList, onIListClicked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(IlistLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    public fun updateCollection(newILists:List<IList>){
        Lists = newILists
        notifyDataSetChanged()
    }


}