package com.wildan.moviecatalogue.ui.main.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.wildan.moviecatalogue.data.source.CatalogueRepository
import com.wildan.moviecatalogue.data.source.local.entity.MovieEntity
import com.wildan.moviecatalogue.utils.FakeDataDummy
import com.wildan.moviecatalogue.view.MovieView
import com.wildan.moviecatalogue.vo.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import androidx.paging.PagedList
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteMovieEntity
import org.mockito.Mockito.`when`

class MovieViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var viewModel: MovieViewModel? = null
    private val repository = mock(CatalogueRepository::class.java)
    private val username = "Movie Catalogue"

    @Mock
    private lateinit var view: MovieView.View

    @Mock
    private lateinit var observer: Observer<in Resource<List<MovieEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<FavoriteMovieEntity>

    @Mock
    private lateinit var observer2: Observer<in Resource<PagedList<FavoriteMovieEntity>>>

    @Throws(Exception::class)
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MovieViewModel(repository)
    }

    @Test
    fun getMovie() {
        val dummyMovie = Resource.success(FakeDataDummy.getMovieList())

        val movieList = MutableLiveData<Resource<List<MovieEntity>>>()
        movieList.value = dummyMovie

        `when`(repository.getAllMovie(view)).thenReturn(movieList)

        viewModel?.setUsername(username)
        viewModel?.getMovie(view)?.observeForever(observer)

        verify(repository).getAllMovie(view)
        verify(observer).onChanged(dummyMovie)
    }

    @Test
    fun getMoviesPaged() {
        val dummyMovie = MutableLiveData<Resource<PagedList<FavoriteMovieEntity>>>()

        dummyMovie.value = Resource.success(pagedList)

        `when`(repository.getMovieAsPaged()).thenReturn(dummyMovie)

        viewModel?.getMoviesPaged()?.observeForever(observer2)

        verify(observer2).onChanged(Resource.success(pagedList))
    }
}