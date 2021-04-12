package com.example.samplemvvmwithretrofit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.samplemvvmwithretrofit.R
import com.example.samplemvvmwithretrofit.databinding.NewsItemBinding
import com.example.samplemvvmwithretrofit.model.ItemNewsModel

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ViewHolder>()  {
    private val differCallback = object : DiffUtil.ItemCallback<ItemNewsModel>() {
        override fun areItemsTheSame(oldItem: ItemNewsModel, newItem: ItemNewsModel): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ItemNewsModel, newItem: ItemNewsModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    class ViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(view)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.binding.txtTitle.text = data.title;
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}