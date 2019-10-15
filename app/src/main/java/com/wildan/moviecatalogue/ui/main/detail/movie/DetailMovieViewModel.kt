package com.wildan.moviecatalogue.ui.main.detail.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wildan.moviecatalogue.data.source.CatalogueRepository
import com.wildan.moviecatalogue.data.source.local.entity.DetailMovieEntity
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteMovieEntity
import com.wildan.moviecatalogue.data.source.local.room.FavoriteDao
import com.wildan.moviecatalogue.view.DetailMovieView
import com.wildan.moviecatalogue.vo.Resource
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailMovieViewModel(private val repository: CatalogueRepository?) : ViewModel(),
    DetailMovieView.ViewModel {

    private var movie: LiveData<Resource<DetailMovieEntity>>? = null
    private val mLogin = MutableLiveData<String>()

    override fun setUsername(username: String) {
        mLogin.value = username
    }

    override fun getDetailMovie(
        isRefresh: Boolean,
        movieId: String,
        view: DetailMovieView.View
    ): LiveData<Resource<DetailMovieEntity>>? {
        if (movie == null || isRefresh) {
            movie = Transformations.switchMap<String, Resource<DetailMovieEntity>>(mLogin)
            { repository?.getDetailMovie(movieId, view) }
        }
        return movie
    }

    private val compositeDisposable = CompositeDisposable()

    override fun insertMovie(movie: FavoriteMovieEntity, movieDao: FavoriteDao) {
        compositeDisposable.add(Observable.fromCallable { movieDao.insertMovie(movie) }
            .subscribeOn(Schedulers.computation())
            .subscribe())
    }

    override fun deleteMovie(movieId: Int, movieDao: FavoriteDao) {
        compositeDisposable.add(Observable.fromCallable { movieDao.deleteMovie(movieId) }
            .subscribeOn(Schedulers.computation())
            .subscribe())
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}