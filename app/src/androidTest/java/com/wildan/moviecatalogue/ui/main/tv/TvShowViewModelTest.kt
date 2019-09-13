package com.wildan.moviecatalogue.ui.main.tv

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel

    @Before
    fun setUp() {
        viewModel = TvShowViewModel()
    }

    @Test
    fun getTvShowList() {
        val tvShowList = viewModel.getTvShowList()
        assertNotNull(tvShowList)
        assertEquals(10, tvShowList?.size)
    }

}