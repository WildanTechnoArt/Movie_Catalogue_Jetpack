package com.wildan.moviecatalogue.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tDetailMovie")
data class DetailMovieEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    var movieId: Int? = 0,

    @ColumnInfo(name = "movie_title")
    var movieName: String? = null,

    @ColumnInfo(name = "movie_date")
    var movieDate: String? = null,

    @ColumnInfo(name = "movie_rating")
    var movieRating: String? = null,

    @ColumnInfo(name = "movie_poster")
    var moviePoster: String? = null,

    @ColumnInfo(name = "movie_popularity")
    var moviePopularity: String? = null,

    @ColumnInfo(name = "movie_overview")
    var movieOverview: String? = null,

    @ColumnInfo(name = "movie_backdrop")
    var movieBackdrop: String? = null,

    @ColumnInfo(name = "movie_genre")
    var movieGenre: String? = null,

    @ColumnInfo(name = "movie_duration")
    var movieDuration: String? = null
)