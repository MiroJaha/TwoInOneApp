package com.example.twoinoneapp

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.guessphrase_item.view.*


class RecyclerViewAdapterPhrase (private val enters:List<String>): RecyclerView.Adapter<RecyclerViewAdapterPhrase.ItemViewHolder>() {
    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.guessphrase_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val entry = enters[position]

        holder.itemView.apply {
            tvResult.text = entry
            if(entry.endsWith("Correct"))
                tvResult.setTextColor(Color.GREEN)
            else
                tvResult.setTextColor(Color.RED)
        }
    }

    override fun getItemCount()=enters.size

}