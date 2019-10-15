package com.wildan.moviecatalogue.data.source.remote

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rx2androidnetworking.Rx2AndroidNetworking
import com.wildan.moviecatalogue.data.source.remote.response.movie.DetailMovieResponse
import com.wildan.moviecatalogue.data.source.remote.response.movie.MovieResponse
import com.wildan.moviecatalogue.data.source.remote.response.movie.MovieResult
import com.wildan.moviecatalogue.data.source.remote.response.tv.DetailTvShowResponse
import com.wildan.moviecatalogue.data.source.remote.response.tv.TvShowResponse
import com.wildan.moviecatalogue.data.source.remote.response.tv.TvShowResult
import com.wildan.moviecatalogue.utils.EspressoIdlingResource
import com.wildan.moviecatalogue.utils.UtilsConstant.API_KEY
import com.wildan.moviecatalogue.utils.UtilsConstant.BASE_URL
import com.wildan.moviecatalogue.view.DetailMovieView
import com.wildan.moviecatalogue.view.DetailTvShowView
import com.wildan.moviecatalogue.view.MovieView
import com.wildan.moviecatalogue.view.TvShowView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RemoteRepository {

    companion object {
        private var INSTANCE: RemoteRepository? = null
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000
        fun getInstance(): RemoteRepository {
            if (INSTANCE == null) {
                INSTANCE =
                    RemoteRepository()
            }
            return INSTANCE as RemoteRepository
        }
    }

    private val compositeDisposable = CompositeDisposable()

    fun getAllMovie(view: MovieView.View): LiveData<ApiResponse<List<MovieResult>>> {
        view.showProgressBar()
        EspressoIdlingResource.increment()

        val movieList = MutableLiveData<ApiResponse<List<MovieResult>>>()

        val handler = Handler()
        handler.postDelayed(
            {
                Rx2AndroidNetworking.get(BASE_URL + "discover/movie")
                    .addQueryParameter("api_key", API_KEY)
                    .build()
                    .getObjectObservable(MovieResponse::class.java)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<MovieResponse> {
                        override fun onComplete() {
                            view.hideProgressBar()
                            EspressoIdlingResource.decrement()
                        }

                        override fun onSubscribe(d: Disposable) {
                            compositeDisposable.add(d)
                        }

                        override fun onNext(t: MovieResponse) {
                            t.let { view.getMovieData(it) }
                            movieList.value = ApiResponse.success(t.movieResult)
                        }

                        override fun onError(e: Throwable) {
                            view.hideProgressBar()
                            view.handleError(e)
                            EspressoIdlingResource.decrement()
                        }

                    })
            },
            SERVICE_LATENCY_IN_MILLIS
        )
        return movieList
    }

    fun getAllTvShow(view: TvShowView.View): LiveData<ApiResponse<List<TvShowResult>>> {
        view.showProgressBar()
        EspressoIdlingResource.increment()

        val tvShowList = MutableLiveData<ApiResponse<List<TvShowResult>>>()

        val handler = Handler()
        handler.postDelayed(
            {
                Rx2AndroidNetworking.get(BASE_URL + "discover/tv")
                    .addQueryParameter("api_key", API_KEY)
                    .build()
                    .getObjectObservable(TvShowResponse::class.java)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<TvShowResponse> {
                        override fun onComplete() {
                            view.hideProgressBar()
                            EspressoIdlingResource.decrement()
                        }

                        override fun onSubscribe(d: Disposable) {
                            compositeDisposable.add(d)
                        }

                        override fun onNext(t: TvShowResponse) {
                            t.let { view.getTvShowData(it) }
                            tvShowList.value = ApiResponse.success(t.tvResult)
                        }

                        override fun onError(e: Throwable) {
                            view.hideProgressBar()
                            view.handleError(e)
                            EspressoIdlingResource.decrement()
                        }

                    })
            },
            SERVICE_LATENCY_IN_MILLIS
        )
        return tvShowList
    }

    fun getDetailMovie(
        movieId: String,
        view: DetailMovieView.View
    ): LiveData<ApiResponse<DetailMovieResponse>> {
        view.showProgressBar()
        EspressoIdlingResource.increment()

        val detailMovie = MutableLiveData<ApiResponse<DetailMovieResponse>>()

        val handler = Handler()
        handler.postDelayed(
            {
                Rx2AndroidNetworking.get(BASE_URL + "movie/{movie_id}")
                    .addPathParameter("movie_id", movieId)
                    .addQueryParameter("api_key", API_KEY)
                    .build()
                    .getObjectObservable(DetailMovieResponse::class.java)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<DetailMovieResponse> {
                        override fun onComplete() {
                            view.hideProgressBar()
                            EspressoIdlingResource.decrement()
                        }

                        override fun onSubscribe(d: Disposable) {
                            compositeDisposable.add(d)
                        }

                        override fun onNext(t: DetailMovieResponse) {
                            detailMovie.value = ApiResponse.success(t)
                        }

                        override fun onError(e: Throwable) {
                            view.hideProgressBar()
                            view.handleError(e)
                            EspressoIdlingResource.decrement()
                        }

                    })
            },
            SERVICE_LATENCY_IN_MILLIS
        )
        return detailMovie
    }

    fun getDetailTvShow(
        tvId: String,
        view: DetailTvShowView.View
    ): LiveData<ApiResponse<DetailTvShowResponse>>  {
        view.showProgressBar()
        EspressoIdlingResource.increment()

        val detailTvShow = MutableLiveData<ApiResponse<DetailTvShowResponse>>()

        val handler = Handler()
        handler.postDelayed(
            {
                Rx2AndroidNetworking.get(BASE_URL + "tv/{tv_id}")
                    .addPathParameter("tv_id", tvId)
                    .addQueryParameter("api_key", API_KEY)
                    .build()
                    .getObjectObservable(DetailTvShowResponse::class.java)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<DetailTvShowResponse> {
                        override fun onComplete() {
                            view.hideProgressBar()
                            EspressoIdlingResource.decrement()
                        }

                        override fun onSubscribe(d: Disposable) {
                            compositeDisposable.add(d)
                        }

                        override fun onNext(t: DetailTvShowResponse) {
                            detailTvShow.value = ApiResponse.success(t)
                        }

                        override fun onError(e: Throwable) {
                            view.hideProgressBar()
                            view.handleError(e)
                            EspressoIdlingResource.decrement()
                        }

                    })
            },
            SERVICE_LATENCY_IN_MILLIS
        )
        return detailTvShow
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }
}