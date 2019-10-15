package com.wildan.moviecatalogue.ui.main.detail.movie

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androidnetworking.AndroidNetworking
import com.wildan.moviecatalogue.GlideApp
import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteMovieEntity
import com.wildan.moviecatalogue.data.source.local.room.FavoriteDatabase
import com.wildan.moviecatalogue.network.ConnectivityStatus
import com.wildan.moviecatalogue.network.NetworkError
import com.wildan.moviecatalogue.utils.DateFormat
import com.wildan.moviecatalogue.utils.UtilsConstant
import com.wildan.moviecatalogue.utils.UtilsConstant.MOVIE_EXTRA
import com.wildan.moviecatalogue.view.DetailMovieView
import com.wildan.moviecatalogue.viewmodel.ViewModelFactory
import com.wildan.moviecatalogue.vo.Status
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.HttpException
import java.net.SocketTimeoutException

class DetailMovieActivity : AppCompatActivity(), DetailMovieView.View {

    private lateinit var mId: String
    private var viewModel: DetailMovieViewModel? = null
    private var genres: String = ""
    private var menuItem: Menu? = null
    private var isFavorite = false
    private lateinit var movieDatabase: FavoriteDatabase
    private val movieEntity = FavoriteMovieEntity()

    private var movieTitle: String? = null
    private var movieDate: String? = null
    private var movieRating: String? = null
    private var moviePoster: String? = null
    private var movieBackdrop: String? = null
    private var movieRuntime: String? = null
    private var movieOverview: String? = null
    private var moviePopularity: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        init()
        favoriteState()
        swipe_refresh.setOnRefreshListener {
            showDetailMovie(true)
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

        AndroidNetworking.initialize(this)
        mId = intent?.getStringExtra(MOVIE_EXTRA).toString()
        movieDatabase = FavoriteDatabase.getInstance(this)

        showDetailMovie(false)
    }

    private fun showDetailMovie(isRefresh: Boolean) {
        viewModel = obtainViewModel(this)
        viewModel?.setUsername("Movie Catalogue")
        viewModel?.getDetailMovie(isRefresh, mId, this)?.observe(this, Observer {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                        swipe_refresh.isRefreshing = true
                    }
                    Status.SUCCESS -> {
                        swipe_refresh.isRefreshing = false
                        layout_data.visibility = View.VISIBLE

                        val items = it.data

                        movieTitle = items?.movieName.toString()
                        movieDate = items?.movieDate.toString()
                        movieRating = items?.movieRating.toString()
                        moviePoster = items?.moviePoster.toString()
                        movieBackdrop = items?.movieBackdrop.toString()
                        movieOverview = items?.movieOverview.toString()
                        moviePopularity = items?.moviePopularity.toString()

                        GlideApp.with(this)
                            .load(UtilsConstant.BACKDROP_URL + items?.movieBackdrop.toString())
                            .centerCrop()
                            .placeholder(R.drawable.ic_placeholder_white_32dp)
                            .error(R.drawable.ic_error_image_white_32dp)
                            .into(img_backdrop)

                        GlideApp.with(this)
                            .load(UtilsConstant.POSTER_URL + items?.moviePoster.toString())
                            .placeholder(R.drawable.ic_image_placeholder_32dp)
                            .error(R.drawable.ic_error_image_32dp)
                            .into(img_poster)

                        movie_title.text = items?.movieName.toString()
                        movie_date.text = DateFormat.getLongDate(items?.movieDate.toString())
                        movie_rating.text = items?.movieRating.toString()
                        movie_description.text = items?.movieOverview.toString()

                        genres = items?.movieGenre.toString()

                        if (items?.movieDuration != null) {
                            movie_duration.visibility = View.VISIBLE
                            duration.visibility = View.VISIBLE
                            val movieDuration = String.format(
                                resources.getString(R.string.movie_duration),
                                items.movieDuration.toString()
                            )
                            movie_duration.text = movieDuration
                            movieRuntime = movieDuration
                        }

                        movie_genres.text = genres
                    }
                    Status.ERROR -> {
                        swipe_refresh.isRefreshing = false
                    }
                }
            }
        })
    }

    private fun addToFavorite() {
        movieEntity.movieId = mId.toInt()
        movieEntity.movieName = movieTitle
        movieEntity.movieDate = movieDate
        movieEntity.movieOverview = movieOverview
        movieEntity.moviePoster = moviePoster
        movieEntity.movieRating = movieRating
        movieEntity.movieBackdrop = movieBackdrop
        movieEntity.moviePopularity = moviePopularity

        viewModel?.insertMovie(movieEntity, movieDatabase.favoriteDao())

        Toast.makeText(this, resources.getString(R.string.add_favorite), Toast.LENGTH_SHORT).show()
    }

    private fun removeFromFavorite() {
        viewModel?.deleteMovie(mId.toInt(), movieDatabase.favoriteDao())
        Toast.makeText(this, getString(R.string.remove_favorite), Toast.LENGTH_SHORT).show()
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_star_white_24dp)
        else
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_star_border_white_24dp)
    }

    private fun favoriteState() {
        val favorite = movieDatabase.favoriteDao().getMovieById(mId)
        if (favorite.isNotEmpty()) isFavorite = true
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.onDestroy()
    }

    override fun showProgressBar() {
        swipe_refresh.isRefreshing = true
    }

    override fun hideProgressBar() {
        swipe_refresh.isRefreshing = false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun handleError(t: Throwable?) {
        if (ConnectivityStatus.isConnected(this)) {
            when (t) {
                is HttpException -> // non 200 error codes
                    NetworkError.handleError(t, this)
                is SocketTimeoutException -> // connection errors
                    Toast.makeText(
                        this,
                        resources.getString(R.string.timeout),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        } else {
            Toast.makeText(this, resources.getString(R.string.no_internet), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_detail, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.add_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        fun obtainViewModel(activity: FragmentActivity): DetailMovieViewModel? {
            val factory = ViewModelFactory.getInstance(activity.application)
            return activity.let {
                ViewModelProviders.of(it, factory).get(DetailMovieViewModel::class.java)
            }
        }
    }
}