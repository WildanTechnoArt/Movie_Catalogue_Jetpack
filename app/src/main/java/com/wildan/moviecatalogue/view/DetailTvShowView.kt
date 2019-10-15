package com.wildan.moviecatalogue.view

import androidx.lifecycle.LiveData
import com.wildan.moviecatalogue.data.source.local.entity.DetailTvShowEntity
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteTvShowEntity
import com.wildan.moviecatalogue.data.source.local.room.FavoriteDao
import com.wildan.moviecatalogue.vo.Resource

class DetailTvShowView {

    interface View {
        fun showProgressBar()
        fun hideProgressBar()
        fun handleError(t: Throwable?)
    }

    interface ViewModel {
        fun getDetailTvShow(isRefresh: Boolean, tvId: String, view: View): LiveData<Resource<DetailTvShowEntity>>?
        fun setUsername(username: String)
        fun insertMovie(tvShow: FavoriteTvShowEntity, tvDao: FavoriteDao)
        fun deleteMovie(tvId: String, tvDao: FavoriteDao)
        fun onDestroy()
    }
}