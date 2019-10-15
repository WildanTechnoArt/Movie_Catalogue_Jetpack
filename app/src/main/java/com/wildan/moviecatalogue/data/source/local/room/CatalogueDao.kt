package com.wildan.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wildan.moviecatalogue.data.source.local.entity.DetailMovieEntity
import com.wildan.moviecatalogue.data.source.local.entity.DetailTvShowEntity
import com.wildan.moviecatalogue.data.source.local.entity.MovieEntity
import com.wildan.moviecatalogue.data.source.local.entity.TvShowEntity

@Dao
interface CatalogueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieEntity: List<MovieEntity>): LongArray

    @Query("SELECT * FROM tMovie")
    fun getAllMovie(): LiveData<List<MovieEntity>>

    @Transaction
    @Query("SELECT * FROM tDetailMovie WHERE movieId = :movieId")
    fun getMovieById(movieId: String): LiveData<DetailMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(TvShowEntity: List<TvShowEntity>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailMovie(DetailMovieEntity: DetailMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailTv(DetailTvShowEntity: DetailTvShowEntity)

    @Query("SELECT * FROM tTvShow")
    fun getAllTvShow(): LiveData<List<TvShowEntity>>

    @Transaction
    @Query("SELECT * FROM tDetailTvShow WHERE tvId = :tvShowId")
    fun getTvShowById(tvShowId: String): LiveData<DetailTvShowEntity>
}