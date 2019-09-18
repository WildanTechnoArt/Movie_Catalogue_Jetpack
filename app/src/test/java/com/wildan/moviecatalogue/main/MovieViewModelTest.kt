package com.wildan.moviecatalogue.main

import com.wildan.moviecatalogue.movie.MovieViewModel
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