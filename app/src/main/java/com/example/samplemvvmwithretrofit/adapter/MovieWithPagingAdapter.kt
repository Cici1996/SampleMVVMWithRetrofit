package com.example.samplemvvmwithretrofit.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samplemvvmwithretrofit.R
import com.example.samplemvvmwithretrofit.databinding.MovieItemBinding
import com.example.samplemvvmwithretrofit.model.BaseMovieItemResponseOther
import com.example.samplemvvmwithretrofit.utils.Constants

class MovieWithPagingAdapter: RecyclerView.Adapter<MovieWithPagingAdapter.ViewHolder>()  {
    private lateinit var context: Context
    private val differCallback = object : DiffUtil.ItemCallback<BaseMovieItemResponseOther>() {
        override fun areItemsTheSame(oldItem: BaseMovieItemResponseOther, newItem: BaseMovieItemResponseOther): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
                oldItem: BaseMovieItemResponseOther,
                newItem: BaseMovieItemResponseOther
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    class ViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(view, parent, false)
        context = parent.context;
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.binding.txtTitle.text = data.title;
        holder.binding.txtrate.text = data.rate.toString();
        val urlImage:String = Constants.BASE_URL_TMDB_IMAGE +data.backdrop_path;
        Glide
                .with(context)
                .load(urlImage)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_broken_image)
                .into(holder.binding.imgPoster);
    }

    override fun getItemCount(): Int {
        if(differ.currentList.size >= 40)
            Log.i("Heyyy","ItMe")
        return differ.currentList.size
    }
}