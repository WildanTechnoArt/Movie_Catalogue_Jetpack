package com.wildan.moviecatalogue.ui.main.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.adapter.MovieAdapterListener
import com.wildan.moviecatalogue.adapter.MovieFavoriteAdapter
import com.wildan.moviecatalogue.ui.main.detail.movie.DetailMovieActivity
import com.wildan.moviecatalogue.ui.main.movie.MovieViewModel
import com.wildan.moviecatalogue.utils.UtilsConstant.MOVIE_EXTRA
import com.wildan.moviecatalogue.viewmodel.ViewModelFactory
import com.wildan.moviecatalogue.vo.Status
import kotlin.properties.Delegates

class MovieFavoriteFragment : Fragment(), MovieAdapterListener {

    private lateinit var rvMovie: RecyclerView
    private lateinit var tvNoData: TextView
    private var adapter by Delegates.notNull<MovieFavoriteAdapter>()

    private val movieViewModel by lazy {
        val viewModelFactory = activity?.application?.let { ViewModelFactory.getInstance(it) }
        ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMovie = view.findViewById(R.id.rv_favorite) as RecyclerView
        tvNoData = view.findViewById(R.id.tv_no_data) as TextView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = MovieFavoriteAdapter(this)

        showDetailMovie()

        rvMovie.layoutManager = LinearLayoutManager(context)
        rvMovie.setHasFixedSize(true)
        rvMovie.adapter = adapter
    }

    override fun onMovieClickListener(movieId: String) {
        val intent = Intent(context, DetailMovieActivity::class.java)
        intent.putExtra(MOVIE_EXTRA, movieId)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        showDetailMovie()
    }

    private fun showDetailMovie() {
        movieViewModel.getMoviesPaged()?.observe(this, Observer {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        adapter.submitList(it.data)
                        adapter.notifyDataSetChanged()
                        if (adapter.itemCount > 0) {
                            rvMovie.visibility = View.VISIBLE
                            tvNoData.visibility = View.GONE
                        } else {
                            rvMovie.visibility = View.GONE
                            tvNoData.visibility = View.VISIBLE
                        }
                    }
                    Status.ERROR -> {}
                }
            }
        })
    }
}
