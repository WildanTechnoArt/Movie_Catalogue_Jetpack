package com.wildan.moviecatalogue.ui.main.movie

import androidx.lifecycle.ViewModel
import com.wildan.moviecatalogue.model.Movie
import com.wildan.moviecatalogue.utils.MovieDummy

class MovieViewModel : ViewModel() {

    fun getMovieList(): ArrayList<Movie>? {
        return MovieDummy.listMovie()
    }
}
