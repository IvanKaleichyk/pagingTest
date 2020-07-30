package com.koleychik.pagingtest2.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.koleychik.pagingtest2.R
import com.koleychik.pagingtest2.model.Model
import kotlinx.android.synthetic.main.rv_item.view.*

class MainViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)) {

    val name : TextView = itemView.findViewById(R.id.name)
    val id : TextView = itemView.findViewById(R.id.id)
    lateinit var model : Model

    public fun bind(model : Model){
        this.model = model
        name.text = model.name
        id.text = model.id.toString()
    }
}