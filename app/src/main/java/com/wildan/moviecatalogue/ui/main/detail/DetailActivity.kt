package com.wildan.moviecatalogue.ui.main.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.wildan.moviecatalogue.GlideApp
import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.model.Movie
import com.wildan.moviecatalogue.utils.UtilsConstant.DETAIL_MOVIE
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private var getMovieData: Movie? = null
    private var data: Movie? = null
    private lateinit var viewModel: DetailViewModel

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

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    private fun showMovieData() {
        getMovieData = intent?.getParcelableExtra(DETAIL_MOVIE)
        viewModel.setMovieList(getMovieData)
        data = viewModel.getMovieShowList()

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