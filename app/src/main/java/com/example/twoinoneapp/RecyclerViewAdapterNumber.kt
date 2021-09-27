package com.example.twoinoneapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.numbergame_item.view.*

class RecyclerViewAdapterNumber (private val answers:List<String>): RecyclerView.Adapter<RecyclerViewAdapterNumber.ItemViewHolder>(){
    class ItemViewHolder (itemView : View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.numbergame_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val answer=answers[position]

        holder.itemView.apply {
            tvGussies.text=answer
            if(answer.startsWith("Sorry"))
                tvGussies.setTextColor(Color.RED)
            else
                tvGussies.setTextColor(Color.GREEN)
        }
    }

    override fun getItemCount(): Int {
        return answers.size
    }
}