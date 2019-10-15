package com.wildan.moviecatalogue.ui.main.detail.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wildan.moviecatalogue.data.source.CatalogueRepository
import com.wildan.moviecatalogue.data.source.local.entity.DetailTvShowEntity
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteTvShowEntity
import com.wildan.moviecatalogue.data.source.local.room.FavoriteDao
import com.wildan.moviecatalogue.view.DetailTvShowView
import com.wildan.moviecatalogue.vo.Resource
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailTvShowViewModel(private val repository: CatalogueRepository?) : ViewModel(),
    DetailTvShowView.ViewModel {

    private var tvShow: LiveData<Resource<DetailTvShowEntity>>? = null
    private val mLogin = MutableLiveData<String>()

    override fun setUsername(username: String) {
        mLogin.value = username
    }

    override fun getDetailTvShow(
        isRefresh: Boolean,
        tvId: String,
        view: DetailTvShowView.View
    ): LiveData<Resource<DetailTvShowEntity>>? {
        if (tvShow == null || isRefresh) {
            tvShow = Transformations.switchMap<String, Resource<DetailTvShowEntity>>(mLogin)
            { repository?.getDetailTvShow(tvId, view) }
        }
        return tvShow
    }

    private val compositeDisposable = CompositeDisposable()

    override fun insertMovie(tvShow: FavoriteTvShowEntity, tvDao: FavoriteDao) {
        compositeDisposable.add(Observable.fromCallable { tvDao.insertTvShow(tvShow) }
            .subscribeOn(Schedulers.computation())
            .subscribe())
    }

    override fun deleteMovie(tvId: String, tvDao: FavoriteDao) {
        compositeDisposable.add(Observable.fromCallable { tvDao.deleteTvShow(tvId) }
            .subscribeOn(Schedulers.computation())
            .subscribe())
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}