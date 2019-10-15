package com.wildan.moviecatalogue.ui.main.detail.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.wildan.moviecatalogue.data.source.CatalogueRepository
import com.wildan.moviecatalogue.data.source.local.entity.DetailTvShowEntity
import com.wildan.moviecatalogue.utils.FakeDataDummy
import com.wildan.moviecatalogue.view.DetailTvShowView
import com.wildan.moviecatalogue.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailTvShowViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var viewModel: DetailTvShowViewModel? = null
    private val repository = Mockito.mock(CatalogueRepository::class.java)
    private val username = "Movie Catalogue"

    @Mock
    private lateinit var view: DetailTvShowView.View

    @Mock
    private lateinit var observer: Observer<in Resource<DetailTvShowEntity>>

    @Throws(Exception::class)
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = DetailTvShowViewModel(repository)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getDetailTvShow() {
        val tvId = "1412"
        val tvShow = MutableLiveData<Resource<DetailTvShowEntity>>()

        tvShow.value = Resource.success(FakeDataDummy.getDetailTvShow())
        Mockito.`when`(repository.getDetailTvShow(tvId, view)).thenReturn(tvShow)

        viewModel?.setUsername(username)
        viewModel?.getDetailTvShow(false, tvId, view)
            ?.observeForever(observer)

        Mockito.verify(repository).getDetailTvShow(tvId, view)
        Mockito.verify(observer)?.onChanged(Resource.success(FakeDataDummy.getDetailTvShow()))

        assertEquals(
            tvShow.value?.data?.tvId.toString(),
            viewModel?.getDetailTvShow(false, tvId, view)?.value?.data?.tvId.toString()
        )

        assertEquals(
            tvShow.value?.data?.tvName.toString(),
            viewModel?.getDetailTvShow(false, tvId, view)?.value?.data?.tvName.toString()
        )

        assertEquals(
            tvShow.value?.data?.tvPoster.toString(),
            viewModel?.getDetailTvShow(false, tvId, view)?.value?.data?.tvPoster.toString()
        )

        assertEquals(
            tvShow.value?.data?.tvBackdrop.toString(),
            viewModel?.getDetailTvShow(false, tvId, view)?.value?.data?.tvBackdrop.toString()
        )

        assertEquals(
            tvShow.value?.data?.tvOverview.toString(),
            viewModel?.getDetailTvShow(false, tvId, view)?.value?.data?.tvOverview.toString()
        )
    }
}