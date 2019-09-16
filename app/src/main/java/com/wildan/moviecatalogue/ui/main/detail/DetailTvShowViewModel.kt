package com.wildan.moviecatalogue.ui.main.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rx2androidnetworking.Rx2AndroidNetworking
import com.wildan.moviecatalogue.model.tv.DetailTvShowResponse
import com.wildan.moviecatalogue.utils.UtilsConstant.BASE_URL
import com.wildan.moviecatalogue.view.DetailTvShowView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailTvShowViewModel : ViewModel(), DetailTvShowView.ViewModel {

    private val tvShow = MutableLiveData<DetailTvShowResponse>()
    private val compositeDisposable = CompositeDisposable()

    fun getTvShowDetail(): LiveData<DetailTvShowResponse> {
        return tvShow
    }

    override fun setDetailTvShow(apiKey: String, tvId: String, view: DetailTvShowView.View) {
        view.showProgressBar()

        Rx2AndroidNetworking.get(BASE_URL + "tv/{tv_id}")
            .addPathParameter("movie_id", tvId)
            .addQueryParameter("api_key", apiKey)
            .build()
            .getObjectObservable(DetailTvShowResponse::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<DetailTvShowResponse> {
                override fun onComplete() {
                    view.onSuccess()
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: DetailTvShowResponse) {
                    tvShow.postValue(t)
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