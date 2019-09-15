package com.wildan.moviecatalogue.utils

import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.model.Movie

object MovieDummy {

    private var movies: ArrayList<Movie>? = null

    private val movieTitle = object : ArrayList<String>(){
        init {
            add("A Star Is Born")
            add("Alita: Battle Angel")
            add("Aquaman")
            add("Bohemian Rhapsody")
            add("Cold Pursuit")
            add("Creed II")
            add("Fantastic Beasts: The Crimes of Grindelwald")
            add("Glass")
            add("How to Train Your Dragon: The Hidden World")
            add("Avengers: Infinity War")
        }
    }

    private val movieDate = object : ArrayList<String>(){
        init {
            add("October 3, 2018")
            add("January 31, 2019")
            add("December 7, 2018")
            add("October 24, 2018")
            add("February 7, 2019")
            add("November 21, 2018")
            add("November 14, 2018")
            add("January 16, 2019")
            add("January 3, 2019")
            add("April 25, 2018")
        }
    }

    private val movieRating= object : ArrayList<String>(){
        init {
            add("7.5")
            add("6.7")
            add("6.8")
            add("8.1")
            add("5.3")
            add("6.7")
            add("6.9")
            add("6.5")
            add("7.6")
            add("8.3")
        }
    }

    private val movieGenres = object : ArrayList<String>(){
        init {
            add("Drama, Romance, Music")
            add("Action, Science Fiction, Thriller, Adventure")
            add("Action, Adventure, Fantasy")
            add("Drama, Music")
            add("Action, Drama, Thriller, Crime")
            add("Drama")
            add("Adventure")
            add("Thriller, Mystery, Drama")
            add("Animation, Family, Adventure")
            add("Adventure, Action, Fantasy")
        }
    }

    private val movieDescriptiom = object : ArrayList<String>(){
        init {
            add("Seasoned musician Jackson Maine discovers - and falls in love with - struggling artist Ally. She has just about given up on her dream to make it big as a singer - until Jack coaxes her into the spotlight. But even as Ally\"'\"s career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.")
            add("When Alita awakens with no memory of who she is in a future world she does not recognize, she is taken in by Ido, a compassionate doctor who realizes that somewhere in this abandoned cyborg shell is the heart and soul of a young woman with an extraordinary past.")
            add("Once home to the most advanced civilization on Earth, Atlantis is now an underwater kingdom ruled by the power-hungry King Orm. With a vast army at his disposal, Orm plans to conquer the remaining oceanic people and then the surface world. Standing in his way is Arthur Curry, Orm\"'\"s half-human, half-Atlantean brother and true heir to the throne.")
            add("Singer Freddie Mercury, guitarist Brian May, drummer Roger Taylor and bass guitarist John Deacon take the music world by storm when they form the rock \"'\"n\"'\" roll band Queen in 1970. Hit songs become instant classics. When Mercury\"'\"s increasingly wild lifestyle starts to spiral out of control, Queen soon faces its greatest challenge yet – finding a way to keep the band together amid the success and excess.")
            add("The quiet family life of Nels Coxman, a snowplow driver, is upended after his son\"'\"s murder. Nels begins a vengeful hunt for Viking, the drug lord he holds responsible for the killing, eliminating Viking\"'\"s associates one by one. As Nels draws closer to Viking, his actions bring even more unexpected and violent consequences, as he proves that revenge is all in the execution.")
            add("Between personal obligations and training for his next big fight against an opponent with ties to his family\"'\"s past, Adonis Creed is up against the challenge of his life.")
            add("Gellert Grindelwald has escaped imprisonment and has begun gathering followers to his cause-elevating wizards above all non-magical beings. The only one capable of putting a stop to him is the wizard he once called his closest friend, Albus Dumbledore. However, Dumbledore will need to seek help from the wizard who had thwarted Grindelwald once before, his former student Newt Scamander, who agrees to help, unaware of the dangers that lie ahead. Lines are drawn as love and loyalty are tested, even among the truest friends and family, in an increasingly divided wizarding world.")
            add("In a series of escalating encounters, former security guard David Dunn uses his supernatural abilities to track Kevin Wendell Crumb, a disturbed man who has twenty-four personalities. Meanwhile, the shadowy presence of Elijah Price emerges as an orchestrator who holds secrets critical to both men.")
            add("As Hiccup fulfills his dream of creating a peaceful dragon utopia, Toothless\"'\" discovery of an untamed, elusive mate draws the Night Fury away. When danger mounts at home and Hiccup\"'\"s reign as village chief is tested, both dragon and rider must make impossible decisions to save their kind.")
            add("As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.")
        }
    }

    private val moviePoster = object : ArrayList<Int>(){
        init {
            add(R.drawable.poster_a_start_is_born)
            add(R.drawable.poster_alita)
            add(R.drawable.poster_aquaman)
            add(R.drawable.poster_bohemian)
            add(R.drawable.poster_cold_persuit)
            add(R.drawable.poster_creed)
            add(R.drawable.poster_crimes)
            add(R.drawable.poster_glass)
            add(R.drawable.poster_how_to_train)
            add(R.drawable.poster_infinity_war)
        }
    }

    fun listMovie(): ArrayList<Movie>{
        movies = ArrayList()

        for (i in 0 until movieTitle.size) {
            val movie = Movie()
            movie.poster = moviePoster[i]
            movie.title = movieTitle[i]
            movie.date = movieDate[i]
            movie.rating = movieRating[i]
            movie.genres = movieGenres[i]
            movie.description = movieDescriptiom[i]
            movies?.add(movie)
        }

        return movies as ArrayList<Movie>
    }

}