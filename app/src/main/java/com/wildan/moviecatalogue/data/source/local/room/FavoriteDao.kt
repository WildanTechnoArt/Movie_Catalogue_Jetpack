package com.wildan.moviecatalogue.data.source.local.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteMovieEntity
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteTvShowEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieEntity: FavoriteMovieEntity): Long

    @Query("SELECT * FROM tFavoriteMovie")
    fun getMovieAsPaged(): DataSource.Factory<Int, FavoriteMovieEntity>

    @Query("SELECT * FROM tFavoriteMovie WHERE movieId = :movieId")
    fun getMovieById(movieId: String): List<FavoriteMovieEntity>

    @Query("DELETE FROM tFavoriteMovie WHERE movieId = :movieId")
    fun deleteMovie(movieId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShowEntity: FavoriteTvShowEntity): Long

    @Query("SELECT * FROM tFavoriteTvShow")
    fun getTvShowAsPaged(): DataSource.Factory<Int, FavoriteTvShowEntity>

    @Query("SELECT * FROM tFavoriteTvShow WHERE tvId = :tvShowId")
    fun getTvShowById(tvShowId: String): List<FavoriteTvShowEntity>

    @Query("DELETE FROM tFavoriteTvShow WHERE tvId = :tvShowId")
    fun deleteTvShow(tvShowId: String)
}