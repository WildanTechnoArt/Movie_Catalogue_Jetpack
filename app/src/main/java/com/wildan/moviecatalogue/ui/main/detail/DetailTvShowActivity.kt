package com.wildan.moviecatalogue.ui.main.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androidnetworking.AndroidNetworking
import com.wildan.moviecatalogue.GlideApp
import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.model.tv.DetailTvShowResponse
import com.wildan.moviecatalogue.network.ConnectivityStatus
import com.wildan.moviecatalogue.network.NetworkError
import com.wildan.moviecatalogue.utils.UtilsConstant.API_KEY
import com.wildan.moviecatalogue.utils.UtilsConstant.BACKDROP_URL
import com.wildan.moviecatalogue.utils.UtilsConstant.MOVIE_EXTRA
import com.wildan.moviecatalogue.utils.UtilsConstant.POSTER_URL
import com.wildan.moviecatalogue.view.DetailTvShowView
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.HttpException
import java.net.SocketTimeoutException

class DetailTvShowActivity : AppCompatActivity(), DetailTvShowView.View {

    private lateinit var mId: String
    private lateinit var viewModel: DetailTvShowViewModel
    private var genres: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        init()
        swipe_refresh.setOnRefreshListener {
            viewModel.setDetailTvShow(API_KEY, mId, this)
        }
    }

    private fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        AndroidNetworking.initialize(applicationContext)
        mId = intent?.getStringExtra(MOVIE_EXTRA).toString()

        AndroidNetworking.initialize(this)

        viewModel = ViewModelProviders.of(this).get(DetailTvShowViewModel::class.java)
        viewModel.setDetailTvShow(API_KEY, mId, this)
        viewModel.getTvShowDetail().observe(this, getDetailTvShow)
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

    private val getDetailTvShow = Observer<DetailTvShowResponse> { items ->
        if (items != null) {
            GlideApp.with(this)
                .load(BACKDROP_URL + items.backdropPath.toString())
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder_white_32dp)
                .error(R.drawable.ic_error_image_white_32dp)
                .into(img_backdrop)

            GlideApp.with(this)
                .load(POSTER_URL + items.posterPath.toString())
                .placeholder(R.drawable.ic_image_placeholder_32dp)
                .error(R.drawable.ic_error_image_32dp)
                .into(img_poster)

            movie_title.text = items.name.toString()
            movie_date.text = items.firstAirDate.toString()
            movie_rating.text = items.voteAverage.toString()
            tv_description.text = items.overview.toString()

            genres = ""
            for (genre in items.genreList) {
                genres += genre.name.toString() + ", "
            }

            val genreResult = genres.substring(0, genres.length.minus(2))
            movie_genres.text = genreResult
        }
    }
}