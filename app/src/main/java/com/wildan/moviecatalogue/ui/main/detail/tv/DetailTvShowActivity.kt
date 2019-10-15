package com.wildan.moviecatalogue.ui.main.detail.tv

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
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteTvShowEntity
import com.wildan.moviecatalogue.data.source.local.room.FavoriteDatabase
import com.wildan.moviecatalogue.network.ConnectivityStatus
import com.wildan.moviecatalogue.network.NetworkError
import com.wildan.moviecatalogue.utils.DateFormat
import com.wildan.moviecatalogue.utils.UtilsConstant
import com.wildan.moviecatalogue.utils.UtilsConstant.MOVIE_EXTRA
import com.wildan.moviecatalogue.view.DetailTvShowView
import com.wildan.moviecatalogue.viewmodel.ViewModelFactory
import com.wildan.moviecatalogue.vo.Status
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.HttpException
import java.net.SocketTimeoutException

class DetailTvShowActivity : AppCompatActivity(), DetailTvShowView.View {

    private lateinit var mId: String
    private var viewModel: DetailTvShowViewModel? = null
    private var genres: String = ""

    private lateinit var movieDatabase: FavoriteDatabase

    private var tvTitle: String? = null
    private var tvDate: String? = null
    private var tvRating: String? = null
    private var tvPoster: String? = null
    private var tvBackdrop: String? = null
    private var tvOverview: String? = null
    private var tvPopularity: String? = null
    private val tvEntity = FavoriteTvShowEntity()
    private var menuItem: Menu? = null
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        init()
        favoriteState()
        swipe_refresh.setOnRefreshListener {
            showDetailTv(true)
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
        movieDatabase = FavoriteDatabase.getInstance(this)
        mId = intent?.getStringExtra(MOVIE_EXTRA).toString()

        AndroidNetworking.initialize(this)

        showDetailTv(false)
    }

    private fun showDetailTv(isRefresh: Boolean) {
        viewModel = obtainViewModel(this)
        viewModel?.setUsername("Movie Catalogue")
        viewModel?.getDetailTvShow(isRefresh, mId, this)?.observe(this, Observer {
            if(it != null){
                when (it.status) {
                    Status.LOADING -> {
                        swipe_refresh.isRefreshing = true
                    }
                    Status.SUCCESS -> {
                        swipe_refresh.isRefreshing = false
                        layout_data.visibility = View.VISIBLE

                        val items = it.data

                        tvTitle = items?.tvName.toString()
                        tvDate = items?.tvDate.toString()
                        tvRating = items?.tvRating.toString()
                        tvPoster = items?.tvPoster.toString()
                        tvBackdrop = items?.tvBackdrop.toString()
                        tvOverview = items?.tvOverview.toString()
                        tvPopularity = items?.tvPopularity.toString()

                        GlideApp.with(this)
                            .load(UtilsConstant.BACKDROP_URL + items?.tvBackdrop.toString())
                            .centerCrop()
                            .placeholder(R.drawable.ic_placeholder_white_32dp)
                            .error(R.drawable.ic_error_image_white_32dp)
                            .into(img_backdrop)

                        GlideApp.with(this)
                            .load(UtilsConstant.POSTER_URL + items?.tvPoster.toString())
                            .placeholder(R.drawable.ic_image_placeholder_32dp)
                            .error(R.drawable.ic_error_image_32dp)
                            .into(img_poster)

                        movie_title.text = items?.tvName.toString()
                        movie_date.text = DateFormat.getLongDate(items?.tvDate.toString())
                        movie_rating.text = items?.tvRating.toString()
                        movie_description.text = items?.tvOverview.toString()

                        genres = items?.tvGenre.toString()
                        movie_genres.text = genres
                    }
                    Status.ERROR -> {
                        swipe_refresh.isRefreshing = false
                    }
                }
            }
        })
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

    private fun addToFavorite() {
        tvEntity.tvId = mId.toInt()
        tvEntity.tvName = tvTitle
        tvEntity.tvDate = tvDate
        tvEntity.tvOverview = tvOverview
        tvEntity.tvPoster = tvPoster
        tvEntity.tvRating = tvRating
        tvEntity.tvBackdrop = tvBackdrop
        tvEntity.tvPopularity = tvPopularity

        viewModel?.insertMovie(tvEntity, movieDatabase.favoriteDao())

        Toast.makeText(this, resources.getString(R.string.add_favorite), Toast.LENGTH_SHORT).show()
    }

    private fun removeFromFavorite() {
        viewModel?.deleteMovie(mId, movieDatabase.favoriteDao())
        Toast.makeText(this, getString(R.string.remove_favorite), Toast.LENGTH_SHORT).show()

    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_white_24dp)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_border_white_24dp)
    }

    private fun favoriteState() {
        val favorite = movieDatabase.favoriteDao().getTvShowById(mId)
        if (favorite.isNotEmpty()) isFavorite = true
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.onDestroy()
    }

    companion object {
        fun obtainViewModel(activity: FragmentActivity): DetailTvShowViewModel? {
            val factory = ViewModelFactory.getInstance(activity.application)
            return activity.let {
                ViewModelProviders.of(it, factory).get(DetailTvShowViewModel::class.java)
            }
        }
    }
}