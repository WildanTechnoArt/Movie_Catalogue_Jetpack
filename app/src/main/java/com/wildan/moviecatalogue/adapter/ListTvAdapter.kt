package com.wildan.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wildan.moviecatalogue.GlideApp
import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.data.source.local.entity.TvShowEntity
import com.wildan.moviecatalogue.utils.DateFormat
import com.wildan.moviecatalogue.utils.UtilsConstant.POSTER_URL
import kotlinx.android.synthetic.main.item_movie.view.*

class ListTvAdapter(private val mListener: TvShowAdapterListener?) :
    RecyclerView.Adapter<ListTvAdapter.MovieViewHolder>() {

    private val tvShowList = ArrayList<TvShowEntity>()

    fun setData(items: List<TvShowEntity>) {
        tvShowList.clear()
        tvShowList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(view: ViewGroup, p1: Int): MovieViewHolder {
        val mView = LayoutInflater.from(view.context).inflate(R.layout.item_movie, view, false)
        return MovieViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return tvShowList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val mContext = holder.itemView.context

        holder.itemView.img_poster?.let {
            mContext?.let { it1 ->
                GlideApp.with(it1)
                    .load(POSTER_URL + tvShowList[position].tvPoster)
                    .placeholder(R.drawable.ic_image_placeholder_32dp)
                    .error(R.drawable.ic_error_image_32dp)
                    .into(it)
            }
        }

        holder.itemView.movie_title?.text = tvShowList[position].tvName.toString()
        holder.itemView.movie_date?.text =
            DateFormat.getLongDate(tvShowList[position].tvDate.toString())
        holder.itemView.tv_popularity?.text =
            mContext?.resources?.getString(R.string.movie_popularity)?.let {
                String.format(
                    it,
                    tvShowList[position].tvPopularity.toString()
                )
            }
        holder.itemView.movie_rating?.text =
            mContext?.resources?.getString(R.string.movie_rating)
                ?.let { String.format(it, tvShowList[position].tvRating.toString()) }

        holder.itemView.movie_item?.setOnClickListener {
            mListener?.onTvClickListener(tvShowList[position].tvId.toString())
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}