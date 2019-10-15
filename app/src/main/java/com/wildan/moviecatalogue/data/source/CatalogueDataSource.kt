package com.wildan.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.wildan.moviecatalogue.data.source.local.entity.*
import com.wildan.moviecatalogue.view.DetailMovieView
import com.wildan.moviecatalogue.view.DetailTvShowView
import com.wildan.moviecatalogue.view.MovieView
import com.wildan.moviecatalogue.view.TvShowView
import com.wildan.moviecatalogue.vo.Resource

interface CatalogueDataSource {
    fun getAllMovie(view: MovieView.View): LiveData<Resource<List<MovieEntity>>>

    fun getMovieAsPaged(): LiveData<Resource<PagedList<FavoriteMovieEntity>>>

    fun getAllTvShow(view: TvShowView.View): LiveData<Resource<List<TvShowEntity>>>

    fun getDetailMovie(
        movieId: String,
        view: DetailMovieView.View
    ): LiveData<Resource<DetailMovieEntity>>

    fun getDetailTvShow(
        tvId: String,
        view: DetailTvShowView.View
    ): LiveData<Resource<DetailTvShowEntity>>

    fun getTvShowAsPaged(): LiveData<Resource<PagedList<FavoriteTvShowEntity>>>

    fun onDestroy()
}