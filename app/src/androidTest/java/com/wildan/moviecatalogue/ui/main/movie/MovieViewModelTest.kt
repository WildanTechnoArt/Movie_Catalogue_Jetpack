package com.wildan.moviecatalogue.ui.main.movie

import org.junit.Assert.*
import org.junit.Test
import org.junit.Before

class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel

    @Before
    fun setUp() {
        viewModel = MovieViewModel()
    }

    @Test
    fun getMovieList() {
        val movieList = viewModel.getMovieList()
        assertNotNull(movieList)
        assertEquals(10, movieList?.size)
    }
}