package com.example.artbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.artbook.R
import com.example.artbook.model.Image
import javax.inject.Inject

class ImageRvAdapter @Inject constructor(private val glide:RequestManager) : RecyclerView.Adapter<ArtRvAdapter.viewHolder>() {


    private var onItemClickListener : ( (String) -> Unit  )? = null

    class viewholder(view:View) : RecyclerView.ViewHolder(view)


    private val  diffUtil = object :DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return newItem==oldItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return newItem==oldItem
        }

    }

    val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var images : List<String> get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtRvAdapter.viewHolder {
       val layout = LayoutInflater.from(parent.context).inflate(R.layout.image_row,parent,false)
        return ArtRvAdapter.viewHolder(layout)

    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun setOnItemClickListener(listener: (String) -> Unit){
            onItemClickListener = listener
    }


    override fun onBindViewHolder(holder: ArtRvAdapter.viewHolder, position: Int) {
        val imageview =  holder.itemView.findViewById<ImageView>(R.id.imageView)
        val url = images.get(position)

        holder.itemView.apply {
            glide.load(url).into(imageview)
            setOnClickListener{
                onItemClickListener?.let {
                    it(url)
                }
            }
        }


    }

}