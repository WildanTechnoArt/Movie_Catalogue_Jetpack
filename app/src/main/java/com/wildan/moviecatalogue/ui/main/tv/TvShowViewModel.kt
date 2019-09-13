package com.wildan.moviecatalogue.ui.main.tv

import androidx.lifecycle.ViewModel
import com.wildan.moviecatalogue.model.Movie

class TvShowViewModel : ViewModel() {

    fun getTvShowList(): ArrayList<Movie>? {
        return TvShowDummy.listTvShow()
    }
}
