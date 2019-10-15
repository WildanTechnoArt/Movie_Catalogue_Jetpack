package com.wildan.moviecatalogue.ui.main.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.wildan.moviecatalogue.data.source.CatalogueRepository
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteTvShowEntity
import com.wildan.moviecatalogue.data.source.local.entity.TvShowEntity
import com.wildan.moviecatalogue.view.TvShowView
import com.wildan.moviecatalogue.vo.Resource

class TvShowViewModel(private val repository: CatalogueRepository?) : ViewModel(),
    TvShowView.ViewModel {

    private val mLogin = MutableLiveData<String>()

    override fun getTvShowPaged(): LiveData<Resource<PagedList<FavoriteTvShowEntity>>>? {
        return repository?.getTvShowAsPaged()
    }

    override fun getTvShow(
      view: TvShowView.View
    ): LiveData<Resource<List<TvShowEntity>>>? =
        Transformations.switchMap<String, Resource<List<TvShowEntity>>>(mLogin)
        { repository?.getAllTvShow(view) }

    override fun setUsername(username: String) {
        mLogin.value = username
    }
}