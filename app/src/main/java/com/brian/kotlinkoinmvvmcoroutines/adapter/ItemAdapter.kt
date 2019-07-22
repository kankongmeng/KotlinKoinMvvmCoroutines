package com.brian.kotlinkoinmvvmcoroutines.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brian.kotlinkoinmvvmcoroutines.R
import com.brian.kotlinkoinmvvmcoroutines.model.Item
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item.view.*

class ItemAdapter(private val listener: ClickListener) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private var itemList: MutableList<Item> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            LayoutInflater.from(
                parent.context).inflate(R.layout.item, parent, false), listener)

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // Verify if position exists in list
        if (position != RecyclerView.NO_POSITION) {
            val item: Item = itemList[position]
            holder.bind(item)
        }
    }

    fun updateData(newItemsList: List<Item>) {
        itemList.clear()
        itemList.addAll(newItemsList)
        notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View, listener: ClickListener) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var item: Item

        private val textViewTitle = itemView.textViewTitle
        private val textViewDesc = itemView.textViewDesc
        private val imageView = itemView.imageView

        init {
            itemView.setOnClickListener { listener.itemClick(item) }
        }

        fun bind(item: Item) {
            this.item = item;
            textViewTitle.text = item.id
            textViewDesc.text = item.name
            // Load images using Glide library
            Glide.with(itemView.context)
                .load(item.imageUrl)
                .fitCenter()
                .thumbnail()
                .into(imageView)
        }
    }

    interface ClickListener {
        fun itemClick(item: Item)
    }
}
