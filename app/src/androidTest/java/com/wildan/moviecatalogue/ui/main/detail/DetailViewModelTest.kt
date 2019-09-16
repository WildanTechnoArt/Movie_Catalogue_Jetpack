package com.wildan.moviecatalogue.ui.main.detail

import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.model.Movie
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private lateinit var dummyMovie: Movie
    private lateinit var dummyTvShow: Movie

    @Before
    fun setUp() {
        viewModel = DetailViewModel()
        dummyMovie = Movie(
            R.drawable.poster_a_start_is_born,
            "A Star Is Born",
            "October 3, 2018",
            "7.5",
            "Drama, Romance, Music",
            "Seasoned musician Jackson Maine discovers - and falls in love with - struggling artist Ally. She has just about given up on her dream to make it big as a singer - until Jack coaxes her into the spotlight. But even as Ally\"'\"s career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons."
        )
        dummyTvShow = Movie(
            R.drawable.poster_arrow,
            "Arrow",
            "October 10, 2012",
            "5.8",
            "Crime, Drama, Mystery, Action, Adventure",
            "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow."
        )
    }

    @Test
    fun getMovieShowList() {
        viewModel.setMoviePosition(0)
        val movie = viewModel.getMovieShowList()
        assertNotNull(movie)
        assertEquals(dummyMovie.poster, movie?.poster)
        assertEquals(dummyMovie.title, movie?.title)
        assertEquals(dummyMovie.date, movie?.date)
        assertEquals(dummyMovie.rating, movie?.rating)
        assertEquals(dummyMovie.genres, movie?.genres)
        assertEquals(dummyMovie.description, movie?.description)
    }

    @Test
    fun getTvShowList() {
        viewModel.setMoviePosition(0)
        val tvShow = viewModel.getTvShowList()
        assertNotNull(tvShow)
        assertEquals(dummyTvShow.poster, tvShow?.poster)
        assertEquals(dummyTvShow.title, tvShow?.title)
        assertEquals(dummyTvShow.date, tvShow?.date)
        assertEquals(dummyTvShow.rating, tvShow?.rating)
        assertEquals(dummyTvShow.genres, tvShow?.genres)
        assertEquals(dummyTvShow.description, tvShow?.description)
    }
}