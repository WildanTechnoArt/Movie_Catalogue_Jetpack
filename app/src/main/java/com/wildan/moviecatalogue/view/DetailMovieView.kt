package com.wildan.moviecatalogue.view

import androidx.lifecycle.LiveData
import com.wildan.moviecatalogue.data.source.local.entity.DetailMovieEntity
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteMovieEntity
import com.wildan.moviecatalogue.data.source.local.room.FavoriteDao
import com.wildan.moviecatalogue.vo.Resource

class DetailMovieView {

    interface View {
        fun showProgressBar()
        fun hideProgressBar()
        fun handleError(t: Throwable?)
    }

    interface ViewModel {
        fun getDetailMovie(isRefresh: Boolean, movieId: String, view: View): LiveData<Resource<DetailMovieEntity>>?
        fun setUsername(username: String)
        fun insertMovie(movie: FavoriteMovieEntity, movieDao: FavoriteDao)
        fun deleteMovie(movieId: Int, movieDao: FavoriteDao)
        fun onDestroy()
    }
}