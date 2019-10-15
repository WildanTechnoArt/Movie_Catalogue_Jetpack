package com.wildan.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wildan.moviecatalogue.GlideApp
import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.data.source.local.entity.MovieEntity
import com.wildan.moviecatalogue.utils.DateFormat
import com.wildan.moviecatalogue.utils.UtilsConstant.POSTER_URL
import kotlinx.android.synthetic.main.item_movie.view.*

class ListMovieAdapter(private val mClickListener: MovieAdapterListener?) :
    RecyclerView.Adapter<ListMovieAdapter.MovieViewHolder>() {

    private var mMoviesList = ArrayList<MovieEntity>()

    fun setData(items: List<MovieEntity>) {
        mMoviesList.clear()
        mMoviesList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(view: ViewGroup, p1: Int): MovieViewHolder {
        val mView = LayoutInflater.from(view.context).inflate(R.layout.item_movie, view, false)
        return MovieViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return mMoviesList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val mContext = holder.itemView.context

        holder.itemView.img_poster?.let {
            mContext?.let { it1 ->
                GlideApp.with(it1)
                    .load(POSTER_URL + mMoviesList[position].moviePoster)
                    .placeholder(R.drawable.ic_image_placeholder_32dp)
                    .error(R.drawable.ic_error_image_32dp)
                    .into(it)
            }
        }

        holder.itemView.movie_title?.text = mMoviesList[position].movieName.toString()
        holder.itemView.movie_date?.text =
            DateFormat.getLongDate(mMoviesList[position].movieDate.toString())
        holder.itemView.tv_popularity?.text =
            mContext?.resources?.getString(R.string.movie_popularity)?.let {
                String.format(
                    it,
                    mMoviesList[position].moviePopularity.toString()
                )
            }
        holder.itemView.movie_rating?.text =
            mContext?.resources?.getString(R.string.movie_rating)?.let {
                String.format(
                    it,
                    mMoviesList[position].movieRating.toString()
                )
            }

        holder.itemView.movie_item?.setOnClickListener {
            mClickListener?.onMovieClickListener(mMoviesList[position].movieId.toString())
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}