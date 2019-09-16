package com.wildan.moviecatalogue.view

class DetailMovieView {

    interface View {
        fun onSuccess()
        fun showProgressBar()
        fun hideProgressBar()
        fun handleError(t: Throwable?)
    }

    interface ViewModel {
        fun setDetailMovie(apiKey: String, movieId: String, view: View)
        fun onDestroy()
    }
}