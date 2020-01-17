package com.benoitthore.base.helloworld

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benoitthore.base.R
import com.benoitthore.base.helloworld.data.db.NoteModel
import kotlinx.android.synthetic.main.hello_world_list_item.view.*

class HelloWorldAdapter(val onItemCLicked: (NoteModel) -> Unit) : RecyclerView.Adapter<HelloWorldListItemViewHolder>() {

    var list: List<NoteModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged() // TODO replace with diff utils
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HelloWorldListItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.hello_world_list_item, parent, false),
            onItemCLicked
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: HelloWorldListItemViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

class HelloWorldListItemViewHolder(itemView: View, val onItemCLicked: (NoteModel) -> Unit) : RecyclerView.ViewHolder(itemView) {
    fun bind(noteModel: NoteModel) {
        itemView.helloWorldListItemTextView.text = noteModel.text
        itemView.setOnClickListener { onItemCLicked(noteModel) }
    }
}