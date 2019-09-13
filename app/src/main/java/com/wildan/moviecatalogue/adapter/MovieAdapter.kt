package com.wildan.moviecatalogue.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wildan.moviecatalogue.GlideApp
import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.model.Movie
import com.wildan.moviecatalogue.ui.main.detail.DetailActivity
import com.wildan.moviecatalogue.utils.UtilsConstant.DETAIL_MOVIE
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var mMoviesList = ArrayList<Movie>()

    fun setData(items: ArrayList<Movie>) {
        mMoviesList.clear()
        mMoviesList.addAll(items)
        notifyDataSetChanged()
    }

    fun getMovieList(): ArrayList<Movie> {
        return  mMoviesList
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
                    .load(mMoviesList[position].poster)
                    .placeholder(R.drawable.ic_image_placeholder_32dp)
                    .error(R.drawable.ic_error_image_32dp)
                    .into(it)
            }
        }

        holder.itemView.tv_title?.text = mMoviesList[position].title.toString()
        holder.itemView.tv_genres?.text = mMoviesList[position].genres.toString()
        holder.itemView.tv_rating?.text =
            mContext?.resources?.getString(R.string.rating)?.let {
                String.format(
                    it,
                    mMoviesList[position].rating.toString()
                )
            }

        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, DetailActivity::class.java)
            intent.putExtra(DETAIL_MOVIE, position.let { it1 -> mMoviesList[it1] })
            mContext.startActivity(intent)
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}