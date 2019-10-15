package com.wildan.moviecatalogue.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wildan.moviecatalogue.GlideApp
import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteTvShowEntity
import com.wildan.moviecatalogue.utils.DateFormat
import com.wildan.moviecatalogue.utils.UtilsConstant.POSTER_URL
import kotlinx.android.synthetic.main.item_movie.view.*

class TvFavoriteAdapter(private val mClickListener: TvShowAdapterListener) :
    PagedListAdapter<FavoriteTvShowEntity, TvFavoriteAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(view: ViewGroup, p1: Int): MovieViewHolder {
        val mView = LayoutInflater.from(view.context).inflate(R.layout.item_movie, view, false)
        return MovieViewHolder(mView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val mContext = holder.itemView.context

        val mListMovie = getItem(position)

        holder.itemView.img_poster?.let {
            mContext?.let { it1 ->
                GlideApp.with(it1)
                    .load(POSTER_URL + mListMovie?.tvPoster)
                    .placeholder(R.drawable.ic_image_placeholder_32dp)
                    .error(R.drawable.ic_error_image_32dp)
                    .into(it)
            }
        }

        holder.itemView.movie_title?.text = mListMovie?.tvName.toString()
        holder.itemView.movie_date?.text =
            DateFormat.getLongDate(mListMovie?.tvDate.toString())
        holder.itemView.tv_popularity?.text =
            mContext?.resources?.getString(R.string.movie_popularity)?.let {
                String.format(
                    it,
                    mListMovie?.tvPopularity.toString()
                )
            }
        holder.itemView.movie_rating?.text =
            mContext?.resources?.getString(R.string.movie_rating)?.let {
                String.format(
                    it,
                    mListMovie?.tvRating.toString()
                )
            }

        holder.itemView.movie_item?.setOnClickListener {
            mClickListener.onTvClickListener(mListMovie?.tvId.toString())
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteTvShowEntity>() {
            override fun areItemsTheSame(oldItem: FavoriteTvShowEntity, newItem: FavoriteTvShowEntity): Boolean {
                return oldItem.tvId == newItem.tvId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: FavoriteTvShowEntity, newItem: FavoriteTvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}