package com.wildan.moviecatalogue.view

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteMovieEntity
import com.wildan.moviecatalogue.data.source.local.entity.MovieEntity
import com.wildan.moviecatalogue.data.source.remote.response.movie.MovieResponse
import com.wildan.moviecatalogue.vo.Resource

class MovieView {

    interface View {
        fun getMovieData(movie: MovieResponse)
        fun showProgressBar()
        fun hideProgressBar()
        fun handleError(t: Throwable?)
    }

    interface ViewModel {
        fun getMovie(view: View): LiveData<Resource<List<MovieEntity>>>?
        fun setUsername(username: String)
        fun getMoviesPaged(): LiveData<Resource<PagedList<FavoriteMovieEntity>>>?
    }
}