package com.wildan.moviecatalogue.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.wildan.moviecatalogue.GlideApp
import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.model.Movie
import com.wildan.moviecatalogue.utils.UtilsConstant.MOVIE_POSITION
import com.wildan.moviecatalogue.utils.UtilsConstant.MOVIE_TYPE
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private var data: Movie? = null
    private lateinit var viewModel: DetailViewModel
    private var position: Int? = null
    private var movieType: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        init()
        showMovieData()
    }

    private fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        position = intent.getIntExtra(MOVIE_POSITION, 0)
        movieType = intent.getBooleanExtra(MOVIE_TYPE, false)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    private fun showMovieData() {
        viewModel.setMoviePosition(position)

        data = if (movieType == true) {
            viewModel.getMovieShowList()
        } else {
            viewModel.getTvShowList()
        }

        GlideApp.with(this)
            .load(data?.poster)
            .into(img_poster)
        tv_title.text = data?.title.toString()
        tv_date.text = data?.date.toString()
        tv_rating.text = "Rating: ${data?.rating.toString()}"
        tv_genres.text = data?.genres.toString()
        tv_description.text = data?.description.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}