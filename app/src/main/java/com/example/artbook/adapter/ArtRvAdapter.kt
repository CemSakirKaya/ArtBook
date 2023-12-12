package com.example.artbook.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.artbook.R
import com.example.artbook.roomdb.Art
import javax.inject.Inject

class ArtRvAdapter @Inject constructor(
    private val glide:RequestManager
) : RecyclerView.Adapter<ArtRvAdapter.viewHolder>() {

        class viewHolder(view:View):RecyclerView.ViewHolder(view)


    private val diffUtil = object : DiffUtil.ItemCallback<Art>(){
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return newItem ==oldItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return newItem ==oldItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)


    var arts :List<Art> get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.art_row,parent,false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return arts.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.artRowImageView)
        val nameText = holder.itemView.findViewById<TextView>(R.id.artRowArtistNameText)
        val artistNameText = holder.itemView.findViewById<TextView>(R.id.artRowArtistNameText)
        val yearText = holder.itemView.findViewById<TextView>(R.id.artRowYearText)

        val art = arts.get(position)

        holder.itemView.apply {
            nameText.text = "Name : ${art.name} "
            artistNameText.text = "Artist Name : ${art.artistName} "
            yearText.text = "Year : ${art.year} "

            glide.load(art.imageUrl).into(imageView)

        }
    }

}