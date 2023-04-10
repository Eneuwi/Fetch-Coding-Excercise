package com.example.fetchcodingexercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val homeFeed: ArrayList<JsonItem?>):RecyclerView.Adapter<MyAdapter.ItemHolder>() {

    override fun getItemCount(): Int {
        return homeFeed.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = homeFeed.get(position)

        holder.itemView.findViewById<TextView>(R.id.nameText)?.text = item?.name
        holder.itemView.findViewById<TextView>(R.id.listId)?.text = item?.listId.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val layoutInflator = LayoutInflater.from(parent.context)
        val cell = layoutInflator.inflate(R.layout.item_view, parent, false)
        return ItemHolder(cell)
    }

    class ItemHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }
}

