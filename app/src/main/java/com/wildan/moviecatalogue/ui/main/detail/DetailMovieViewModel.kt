package com.wildan.moviecatalogue.ui.main.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rx2androidnetworking.Rx2AndroidNetworking
import com.wildan.moviecatalogue.model.movie.DetailMovieResponse
import com.wildan.moviecatalogue.utils.UtilsConstant.BASE_URL
import com.wildan.moviecatalogue.view.DetailMovieView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailMovieViewModel : ViewModel(), DetailMovieView.ViewModel {

    private val movie = MutableLiveData<DetailMovieResponse>()
    private val compositeDisposable = CompositeDisposable()

    fun getMovieDetail(): LiveData<DetailMovieResponse> {
        return movie
    }

    override fun setDetailMovie(apiKey: String, movieId: String, view: DetailMovieView.View) {
        view.showProgressBar()

        Rx2AndroidNetworking.get(BASE_URL + "movie/{movie_id}")
            .addPathParameter("movie_id", movieId)
            .addQueryParameter("api_key", apiKey)
            .build()
            .getObjectObservable(DetailMovieResponse::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<DetailMovieResponse> {
                override fun onComplete() {
                    view.onSuccess()
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: DetailMovieResponse) {
                    movie.postValue(t)
                }

                override fun onError(e: Throwable) {
                    view.hideProgressBar()
                    view.handleError(e)
                }

            })

    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}