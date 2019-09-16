package com.wildan.moviecatalogue.ui.main.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androidnetworking.AndroidNetworking
import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.model.movie.DetailMovieResponse
import com.wildan.moviecatalogue.network.ConnectivityStatus
import com.wildan.moviecatalogue.network.NetworkError
import com.wildan.moviecatalogue.utils.UtilsConstant.API_KEY
import com.wildan.moviecatalogue.utils.UtilsConstant.MOVIE_EXTRA
import com.wildan.moviecatalogue.view.DetailMovieView
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.HttpException
import java.net.SocketTimeoutException

class DetailMovieActivity : AppCompatActivity(), DetailMovieView.View {

    private lateinit var mId: String
    private lateinit var viewModel: DetailMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        init()
        swipe_refresh.setOnRefreshListener {
            viewModel.setDetailMovie(API_KEY, mId, this)
        }
    }

    private fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        AndroidNetworking.initialize(applicationContext)
        mId = intent?.getStringExtra(MOVIE_EXTRA).toString()

        AndroidNetworking.initialize(this)

        viewModel = ViewModelProviders.of(this).get(DetailMovieViewModel::class.java)
        viewModel.setDetailMovie(API_KEY, mId, this)
        viewModel.getMovieDetail().observe(this, getDetailMovie)
    }

    override fun showProgressBar() {
        swipe_refresh.isRefreshing = true
    }

    override fun hideProgressBar() {
        swipe_refresh.isRefreshing = false
    }

    override fun onSuccess() {
        swipe_refresh.isRefreshing = false
        layout_data.visibility = View.VISIBLE
    }

    override fun handleError(t: Throwable?) {
        if (ConnectivityStatus.isConnected(this)) {
            when (t) {
                is HttpException -> // non 200 error codes
                    NetworkError.handleError(t, this)
                is SocketTimeoutException -> // connection errors
                    Toast.makeText(this, resources.getString(R.string.timeout), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, resources.getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private val getDetailMovie = Observer<DetailMovieResponse> { movieItems ->
        if (movieItems != null) {
            tv_title.text = movieItems.title.toString()
            tv_date.text = movieItems.releaseDate.toString()
            tv_rating.text = movieItems.voteAverage.toString()
            tv_duration.text = movieItems.runtime.toString()
            tv_overview.text = movieItems.overview.toString()
        }
    }
}