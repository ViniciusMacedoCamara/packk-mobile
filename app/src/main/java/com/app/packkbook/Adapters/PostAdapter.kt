package com.app.packkbook.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.packkbook.Models.Post
import com.app.packkbook.R

class PostAdapter(private val posts: ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    var onItemClick : ((Post) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_post, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(posts[position])
        }

        holder.textViewTitle.text = "Title: ${posts[position].title}"
        holder.textViewBody.text = "Body: ${posts[position].body}"

    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewBody: TextView = itemView.findViewById(R.id.textViewBody)

    }

    fun updateReceiptsList(newlist: Collection<Post>) {
        posts.clear()
        posts.addAll(newlist)
        notifyDataSetChanged()
    }
}