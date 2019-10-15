package com.wildan.moviecatalogue.ui.main.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.wildan.moviecatalogue.data.source.CatalogueRepository
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteMovieEntity
import com.wildan.moviecatalogue.data.source.local.entity.MovieEntity
import com.wildan.moviecatalogue.view.MovieView
import com.wildan.moviecatalogue.vo.Resource

class MovieViewModel(private val repository: CatalogueRepository?) : ViewModel(),
    MovieView.ViewModel {

    private val mLogin = MutableLiveData<String>()

    override fun getMoviesPaged(): LiveData<Resource<PagedList<FavoriteMovieEntity>>>? {
        return repository?.getMovieAsPaged()
    }

    override fun getMovie(
        view: MovieView.View
    ): LiveData<Resource<List<MovieEntity>>>? =
        Transformations.switchMap<String, Resource<List<MovieEntity>>>(mLogin)
        { repository?.getAllMovie(view) }

    override fun setUsername(username: String) {
        mLogin.value = username
    }
}