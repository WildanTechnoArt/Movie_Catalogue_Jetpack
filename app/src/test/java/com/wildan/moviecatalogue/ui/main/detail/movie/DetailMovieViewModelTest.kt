package com.wildan.moviecatalogue.ui.main.detail.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.wildan.moviecatalogue.data.source.CatalogueRepository
import com.wildan.moviecatalogue.data.source.local.entity.DetailMovieEntity
import com.wildan.moviecatalogue.utils.FakeDataDummy
import com.wildan.moviecatalogue.view.DetailMovieView
import com.wildan.moviecatalogue.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class DetailMovieViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var viewModel: DetailMovieViewModel? = null
    private val repository = mock(CatalogueRepository::class.java)
    private val username = "Movie Catalogue"

    @Mock
    private lateinit var view: DetailMovieView.View

    @Mock
    private lateinit var observer: Observer<in Resource<DetailMovieEntity>>

    @Throws(Exception::class)
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = DetailMovieViewModel(repository)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getDetailMovie() {
        val movieId = "429617"
        val movies = MutableLiveData<Resource<DetailMovieEntity>>()

        movies.value = Resource.success(FakeDataDummy.getDetailMovie())
        `when`(repository.getDetailMovie(movieId, view)).thenReturn(movies)

        viewModel?.setUsername(username)
        viewModel?.getDetailMovie(false, movieId, view)
            ?.observeForever(observer)

        verify(repository).getDetailMovie(movieId, view)
        verify(observer)?.onChanged(Resource.success(FakeDataDummy.getDetailMovie()))

        assertEquals(
            movies.value?.data?.movieId.toString(),
            viewModel?.getDetailMovie(false, movieId, view)?.value?.data?.movieId.toString()
        )

        assertEquals(
            movies.value?.data?.movieName.toString(),
            viewModel?.getDetailMovie(false, movieId, view)?.value?.data?.movieName.toString()
        )

        assertEquals(
            movies.value?.data?.moviePoster.toString(),
            viewModel?.getDetailMovie(false, movieId, view)?.value?.data?.moviePoster.toString()
        )

        assertEquals(
            movies.value?.data?.movieBackdrop.toString(),
            viewModel?.getDetailMovie(false, movieId, view)?.value?.data?.movieBackdrop.toString()
        )

        assertEquals(
            movies.value?.data?.movieOverview.toString(),
            viewModel?.getDetailMovie(false, movieId, view)?.value?.data?.movieOverview.toString()
        )
    }
}