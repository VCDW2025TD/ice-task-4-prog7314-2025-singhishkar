package com.ishka.memestream

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ishka.memestream.databinding.ItemMemeBinding
import com.ishka.memestream.model.Meme

class MemeAdapter : RecyclerView.Adapter<MemeAdapter.MemeViewHolder>() {

    private val memes = mutableListOf<Meme>()

    fun submitList(newMemes: List<Meme>) {
        memes.clear()
        memes.addAll(newMemes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        val binding = ItemMemeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        holder.bind(memes[position])
    }

    override fun getItemCount(): Int = memes.size

    inner class MemeViewHolder(private val binding: ItemMemeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meme: Meme) {
            binding.textViewTitle.text = meme.caption

            Glide.with(binding.imageView.context)
                .load(meme.imageUrl) // Loads both static and animated images
                .into(binding.imageView)
        }
    }
}
