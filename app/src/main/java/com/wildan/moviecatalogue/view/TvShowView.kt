package com.wildan.moviecatalogue.view

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteTvShowEntity
import com.wildan.moviecatalogue.data.source.local.entity.TvShowEntity
import com.wildan.moviecatalogue.data.source.remote.response.tv.TvShowResponse
import com.wildan.moviecatalogue.vo.Resource

class TvShowView {

    interface View {
        fun getTvShowData(tv: TvShowResponse)
        fun showProgressBar()
        fun hideProgressBar()
        fun handleError(t: Throwable?)
    }

    interface ViewModel {
        fun getTvShow(view: View): LiveData<Resource<List<TvShowEntity>>>?
        fun setUsername(username: String)
        fun getTvShowPaged(): LiveData<Resource<PagedList<FavoriteTvShowEntity>>>?
    }
}