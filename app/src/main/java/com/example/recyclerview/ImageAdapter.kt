package com.example.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ImageAdapter(private var imageList: List<String>, private var userList: List<String>, private var likeList: List<String>, private var tagList: List<String>): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView
        val user: TextView
        val likes: TextView
        val tags: TextView

        init {
            // Find our RecyclerView item's ImageView for future use
            image = view.findViewById(R.id.images_1)
            user = view.findViewById(R.id.image_user)
            likes = view.findViewById(R.id.image_likes)
            tags = view.findViewById(R.id.image_tags)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.images, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = imageList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(imageList[position])
            .centerCrop()
            .into(holder.image)

        holder.user.text = "User: " + userList[position]
        holder.likes.text = "Likes: " + likeList[position]
        holder.tags.text = "Tags: " + tagList[position]
    }
}