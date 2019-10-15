package com.wildan.moviecatalogue.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tDetailTvShow")
data class DetailTvShowEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tvId")
    var tvId: Int? = 0,

    @ColumnInfo(name = "tv_title")
    var tvName: String? = null,

    @ColumnInfo(name = "tv_date")
    var tvDate: String? = null,

    @ColumnInfo(name = "tv_rating")
    var tvRating: String? = null,

    @ColumnInfo(name = "tv_poster")
    var tvPoster: String? = null,

    @ColumnInfo(name = "tv_popularity")
    var tvPopularity: String? = null,

    @ColumnInfo(name = "tv_overview")
    var tvOverview: String? = null,

    @ColumnInfo(name = "tv_backdrop")
    var tvBackdrop: String? = null,

    @ColumnInfo(name = "tv_genre")
    var tvGenre: String? = null
)