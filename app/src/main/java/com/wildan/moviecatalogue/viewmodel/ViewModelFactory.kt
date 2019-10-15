package com.wildan.moviecatalogue.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.wildan.moviecatalogue.di.Injection
import com.wildan.moviecatalogue.data.source.CatalogueRepository
import com.wildan.moviecatalogue.ui.main.detail.movie.DetailMovieViewModel
import com.wildan.moviecatalogue.ui.main.detail.tv.DetailTvShowViewModel
import com.wildan.moviecatalogue.ui.main.movie.MovieViewModel
import com.wildan.moviecatalogue.ui.main.tv.TvShowViewModel

class ViewModelFactory private constructor(private val repository: CatalogueRepository?) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> MovieViewModel(repository) as T
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> TvShowViewModel(repository) as T
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> DetailMovieViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(DetailTvShowViewModel::class.java) -> DetailTvShowViewModel(
                repository
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            Injection.provideRepository(application)?.let { ViewModelFactory(it) }
                    }
                }
            }
            return INSTANCE
        }
    }
}