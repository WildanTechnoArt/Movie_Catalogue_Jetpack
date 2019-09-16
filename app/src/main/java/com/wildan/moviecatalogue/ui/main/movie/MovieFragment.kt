package com.wildan.moviecatalogue.ui.main.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidnetworking.AndroidNetworking
import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.adapter.ListMovieAdapter
import com.wildan.moviecatalogue.adapter.MovieAdapterListener
import com.wildan.moviecatalogue.model.movie.MovieResponse
import com.wildan.moviecatalogue.model.movie.MovieResult
import com.wildan.moviecatalogue.network.ConnectivityStatus
import com.wildan.moviecatalogue.network.NetworkError
import com.wildan.moviecatalogue.ui.main.detail.DetailMovieActivity
import com.wildan.moviecatalogue.utils.UtilsConstant.API_KEY
import com.wildan.moviecatalogue.utils.UtilsConstant.MOVIE_EXTRA
import com.wildan.moviecatalogue.utils.UtilsConstant.STATE_SAVED
import com.wildan.moviecatalogue.view.MovieView
import retrofit2.HttpException
import java.net.SocketTimeoutException
import kotlin.properties.Delegates

class MovieFragment : Fragment(), MovieView.View, MovieAdapterListener {

    private lateinit var rvMovie: RecyclerView
    private lateinit var movieViewModel: MovieViewModel
    private var adapter by Delegates.notNull<ListMovieAdapter>()
    private var page: Int = 1
    private var totalPage: Int? = null
    private var isLoading = false
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvMovie = view.findViewById(R.id.rv_movie)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)

        prepare()
        scrollListener()

        if (savedInstanceState == null) {
            showListMovie()
        }

        swipeRefresh.setOnRefreshListener {
            showListMovie()
        }

    }

    override fun showProgressBar() {
        isLoading = true
        swipeRefresh.isRefreshing = true
    }

    override fun hideProgressBar() {
        isLoading = false
        swipeRefresh.isRefreshing = false
    }

    override fun getMovieData(movie: MovieResponse) {
        totalPage = movie.totalPages
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_SAVED, "saved")
    }

    override fun handleError(t: Throwable?) {
        if (ConnectivityStatus.isConnected(context)) {
            when (t) {
                is HttpException -> // non 200 error codes
                    NetworkError.handleError(t, context)
                is SocketTimeoutException -> // connection errors
                    Toast.makeText(
                        context,
                        resources.getString(R.string.timeout),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        } else {
            Toast.makeText(context, resources.getString(R.string.no_internet), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onMovieClickListener(movieId: String) {
        val intent = Intent(context, DetailMovieActivity::class.java)
        intent.putExtra(MOVIE_EXTRA, movieId)
        startActivity(intent)
    }

    private fun prepare() {
        AndroidNetworking.initialize(context)

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        movieViewModel.getMovies().observe(this, getMovie)

        adapter = ListMovieAdapter(this)
        adapter.notifyDataSetChanged()

        rvMovie.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(context)
        rvMovie.layoutManager = mLayoutManager

        rvMovie.adapter = adapter
    }

    private fun scrollListener() {
        rvMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val countItem = linearLayoutManager.itemCount
                val lastVisiblePosition =
                    linearLayoutManager.findLastCompletelyVisibleItemPosition()
                val isLastPosition = countItem.minus(1) == lastVisiblePosition
                if (!isLoading && isLastPosition && page != totalPage) {
                    page = page.plus(1)
                    showListMovie()
                }
            }
        })
    }

    private fun showListMovie() {
        movieViewModel.setMovie(API_KEY, page = page, view = this)
    }

    private val getMovie = Observer<ArrayList<MovieResult>> { movieItems ->
        if (movieItems != null) {
            if (page == 1) {
                adapter.setData(movieItems)
            } else {
                adapter.refreshAdapter(movieItems)
            }
        }
    }
}