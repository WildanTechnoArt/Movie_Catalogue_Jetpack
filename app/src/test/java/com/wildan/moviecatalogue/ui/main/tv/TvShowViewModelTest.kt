package com.wildan.moviecatalogue.ui.main.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.wildan.moviecatalogue.data.source.CatalogueRepository
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteTvShowEntity
import com.wildan.moviecatalogue.data.source.local.entity.TvShowEntity
import com.wildan.moviecatalogue.utils.FakeDataDummy
import com.wildan.moviecatalogue.view.TvShowView
import com.wildan.moviecatalogue.vo.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class TvShowViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var viewModel: TvShowViewModel? = null
    private val repository = mock(CatalogueRepository::class.java)
    private val username = "Movie Catalogue"

    @Mock
    private lateinit var view: TvShowView.View

    @Mock
    private lateinit var observer: Observer<in Resource<List<TvShowEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<FavoriteTvShowEntity>

    @Mock
    private lateinit var observer2: Observer<in Resource<PagedList<FavoriteTvShowEntity>>>

    @Throws(Exception::class)
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = TvShowViewModel(repository)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getTvShow() {
        val dummyTvShow = Resource.success(FakeDataDummy.getTvShowList())

        val tvShowList = MutableLiveData<Resource<List<TvShowEntity>>>()
        tvShowList.value = dummyTvShow

        `when`(repository.getAllTvShow(view)).thenReturn(tvShowList)

        viewModel?.setUsername(username)
        viewModel?.getTvShow(view)?.observeForever(observer)

        verify(repository).getAllTvShow(view)
        verify(observer).onChanged(dummyTvShow)
    }

    @Test
    fun getTvShowPaged() {
        val dummyMovie = MutableLiveData<Resource<PagedList<FavoriteTvShowEntity>>>()

        dummyMovie.value = Resource.success(pagedList)

        `when`(repository.getTvShowAsPaged()).thenReturn(dummyMovie)

        viewModel?.getTvShowPaged()?.observeForever(observer2)

        verify(observer2).onChanged(Resource.success(pagedList))
    }
}