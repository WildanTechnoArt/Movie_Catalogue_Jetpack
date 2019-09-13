package com.wildan.moviecatalogue.ui.main.detail

import androidx.lifecycle.ViewModel
import com.wildan.moviecatalogue.model.Movie

class DetailViewModel : ViewModel() {

    private var movieList: Movie? = Movie()

    fun getMovieShowList(): Movie? {
        return movieList
    }

    fun setMovieList(movieList: Movie?){
        this.movieList = movieList
    }
}
