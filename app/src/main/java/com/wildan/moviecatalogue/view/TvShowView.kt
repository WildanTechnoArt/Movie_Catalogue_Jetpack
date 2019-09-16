package com.wildan.moviecatalogue.view

import com.wildan.moviecatalogue.model.tv.TvShowResponse

class TvShowView {

    interface View {
        fun getTvShowData(tv: TvShowResponse)
        fun showProgressBar()
        fun hideProgressBar()
        fun handleError(t: Throwable?)
    }

    interface ViewModel {
        fun setTvShow(apiKey: String, page: Int, view: View)
        fun onDestroy()
    }
}