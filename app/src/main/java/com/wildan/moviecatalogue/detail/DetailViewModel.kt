package com.wildan.moviecatalogue.detail

import androidx.lifecycle.ViewModel
import com.wildan.moviecatalogue.model.Movie
import com.wildan.moviecatalogue.utils.MovieDummy
import com.wildan.moviecatalogue.utils.TvShowDummy

class DetailViewModel : ViewModel() {

    private var position: Int? = null

    fun getMovieShowList(): Movie? {
        return position?.let { MovieDummy.listMovie()[it] }
    }

    fun getTvShowList(): Movie? {
        return position?.let { TvShowDummy.listTvShow()[it] }
    }

    fun setMoviePosition(position: Int?){
        this.position = position
    }
}