package com.cakabara.librarybooks_api

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cakabara.librarybooks_api.databinding.ItemBukuBinding

class BukuAdapter(private val onItemClick: (Int) -> Unit) : ListAdapter<Book, BukuAdapter.BookViewHolder>(BookDiffCallback()) {

    inner class BookViewHolder(private val binding: ItemBukuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.tvJudul.text = book.title
            binding.tvGenre.text = book.genre
            binding.tvPengarang.text = book.author
            binding.tvTahunTerbit.text = book.year_publish.toString()
            binding.tvDeskripsi.text = book.description

            Glide.with(binding.ivCover.context)
                .load(book.image_url)
                .into(binding.ivCover)

            binding.root.setOnClickListener {
                onItemClick(book.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBukuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
}
