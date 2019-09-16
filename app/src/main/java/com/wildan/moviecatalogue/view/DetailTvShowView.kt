package com.wildan.moviecatalogue.view

import com.wildan.moviecatalogue.model.tv.DetailTvShowResponse

class DetailTvShowView {

    interface View {
        fun showDetailTvShow(tv: DetailTvShowResponse)
        fun noInternetConnection(message: String)
        fun showProgressBar()
        fun hideProgressBar()
        fun handleError(t: Throwable?)
        fun onSuccess()
    }

    interface ViewModel {
        fun setDetailTvShow(apiKey: String, tvId: String, view: View)
        fun onDestroy()
    }
}