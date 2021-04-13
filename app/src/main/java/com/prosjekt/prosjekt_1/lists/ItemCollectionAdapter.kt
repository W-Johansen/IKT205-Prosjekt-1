package com.prosjekt.prosjekt_1.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prosjekt.prosjekt_1.databinding.ItemLayoutBinding
import com.prosjekt.prosjekt_1.lists.data.Item
import kotlinx.android.synthetic.main.item_layout.view.*

class ItemCollectionAdapter(private var Items:List<Item>, private val onItemClicked:(Item) -> Unit) : RecyclerView.Adapter<ItemCollectionAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item, onItemClicked:(Item) -> Unit) {
            binding.itemName.text = item.name
            binding.itemCheckBox.isChecked = item.isDone

            binding.card.setOnClickListener {
                // TODO: kanskje ikke trengs, vurder
                onItemClicked(item)
            }
            binding.itemCheckBox.setOnClickListener{
                print("test")
                item.isDone = it.itemCheckBox.isChecked
            }

        }
    }

    override fun getItemCount(): Int = Items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Items[position]
        holder.bind(item, onItemClicked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    public fun updateCollection(newItems:List<Item>){
        Items = newItems
        notifyDataSetChanged()
    }


}
