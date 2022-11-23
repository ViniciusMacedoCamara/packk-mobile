package com.app.packkbook.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.packkbook.Models.Comment
import com.app.packkbook.R

class CommentAdapter(private val comments: ArrayList<Comment>) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    var onItemClick : ((Comment) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_comment, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(comments[position])
        }

        holder.textViewEmail.text = "Email: ${comments[position].email}"
        holder.textViewName.text = "Name: ${comments[position].name}"
        holder.textViewBody.text = "Body: ${comments[position].body}"

    }

    override fun getItemCount(): Int {
        return comments.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewEmail: TextView = itemView.findViewById(R.id.textViewEmail)
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewBody: TextView = itemView.findViewById(R.id.textViewBody)

    }

    fun updateReceiptsList(newlist: Collection<Comment>) {
        comments.clear()
        comments.addAll(newlist)
        notifyDataSetChanged()
    }
}