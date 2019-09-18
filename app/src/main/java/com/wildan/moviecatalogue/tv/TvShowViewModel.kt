package com.wildan.moviecatalogue.tv

import androidx.lifecycle.ViewModel
import com.wildan.moviecatalogue.model.Movie
import com.wildan.moviecatalogue.utils.TvShowDummy

class TvShowViewModel : ViewModel() {

    fun getTvShowList(): ArrayList<Movie>? {
        return TvShowDummy.listTvShow()
    }
}
