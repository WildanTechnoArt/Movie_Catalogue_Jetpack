package com.wildan.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.wildan.moviecatalogue.data.source.local.entity.*
import com.wildan.moviecatalogue.data.source.local.room.CatalogueDao
import com.wildan.moviecatalogue.data.source.local.room.FavoriteDao

open class LocalRepository constructor(private val catalogueDao: CatalogueDao,
                                       private val favoriteDao: FavoriteDao) {
    fun getMovies(): LiveData<List<MovieEntity>> {
        return catalogueDao.getAllMovie()
    }

    fun getMovieById(movieId: String): LiveData<DetailMovieEntity> {
        return catalogueDao.getMovieById(movieId)
    }

    fun insertMovies(movies: List<MovieEntity>) {
        catalogueDao.insertMovie(movies)
    }

    fun getMovieAsPaged(): DataSource.Factory<Int, FavoriteMovieEntity> {
        return favoriteDao.getMovieAsPaged()
    }

    fun getTvShows(): LiveData<List<TvShowEntity>> {
        return catalogueDao.getAllTvShow()
    }

    fun getTvShowById(tvShowId: String): LiveData<DetailTvShowEntity> {
        return catalogueDao.getTvShowById(tvShowId)
    }

    fun insertTvShows(tvShows: List<TvShowEntity>) {
        catalogueDao.insertTvShow(tvShows)
    }

    fun insertDetailMovie(movie: DetailMovieEntity) {
        catalogueDao.insertDetailMovie(movie)
    }

    fun insertDetailTv(tv: DetailTvShowEntity) {
        catalogueDao.insertDetailTv(tv)
    }

    fun getTvShowAsPaged(): DataSource.Factory<Int, FavoriteTvShowEntity> {
        return favoriteDao.getTvShowAsPaged()
    }

    companion object {

        private var INSTANCE: LocalRepository? = null

        fun getInstance(catalogueDao: CatalogueDao, favoriteDao: FavoriteDao): LocalRepository {
            if (INSTANCE == null) {
                INSTANCE =
                    LocalRepository(catalogueDao, favoriteDao)
            }
            return INSTANCE as LocalRepository
        }
    }
}