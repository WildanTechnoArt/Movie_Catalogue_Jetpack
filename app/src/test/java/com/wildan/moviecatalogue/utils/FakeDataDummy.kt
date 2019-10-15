package com.wildan.moviecatalogue.utils

import com.wildan.moviecatalogue.data.source.local.entity.*

object FakeDataDummy {
    fun getMovieList(): List<MovieEntity> {
        return listOf(
            MovieEntity(
                429617,
                "Spider-Man: Far from Home",
                "2019-07-02",
                "7.7",
                "/lcq8dVxeeOqHvvgcte707K0KVx5.jpg",
                "371.667",
                "Peter Parker and his friends go on a summer trip to Europe. However, they will hardly be able to rest - Peter will have to agree to help Nick Fury uncover the mystery of creatures that cause natural disasters and destruction throughout the continent.",
                "/5myQbDzw3l8K9yofUXRJ4UTVgam.jpg"
            ),
            MovieEntity(
                429203,
                "The Old Man & the Gun",
                "2018-09-28",
                "6.4",
                "/a4BfxRK8dBgbQqbRxPs8kmLd8LG.jpg",
                "356.956",
                "The true story of Forrest Tucker, from his audacious escape from San Quentin at the age of 70 to an unprecedented string of heists that confounded authorities and enchanted the public. Wrapped up in the pursuit are a detective, who becomes captivated with Forrest’s commitment to his craft, and a woman, who loves him in spite of his chosen profession.",
                "/6X2YjjYcs8XyZRDmJAHNDlls7L4.jpg"
            )
        )
    }

    fun getFavMovieList(): List<FavoriteMovieEntity> {
        return listOf(
            FavoriteMovieEntity(
                429617,
                "Spider-Man: Far from Home",
                "2019-07-02",
                "7.7",
                "/lcq8dVxeeOqHvvgcte707K0KVx5.jpg",
                "371.667",
                "Peter Parker and his friends go on a summer trip to Europe. However, they will hardly be able to rest - Peter will have to agree to help Nick Fury uncover the mystery of creatures that cause natural disasters and destruction throughout the continent.",
                "/5myQbDzw3l8K9yofUXRJ4UTVgam.jpg"
            ),
            FavoriteMovieEntity(
                429203,
                "The Old Man & the Gun",
                "2018-09-28",
                "6.4",
                "/a4BfxRK8dBgbQqbRxPs8kmLd8LG.jpg",
                "356.956",
                "The true story of Forrest Tucker, from his audacious escape from San Quentin at the age of 70 to an unprecedented string of heists that confounded authorities and enchanted the public. Wrapped up in the pursuit are a detective, who becomes captivated with Forrest’s commitment to his craft, and a woman, who loves him in spite of his chosen profession.",
                "/6X2YjjYcs8XyZRDmJAHNDlls7L4.jpg"
            )
        )
    }

    fun getTvShowList(): List<TvShowEntity> {
        return listOf(
            TvShowEntity(
                1412,
                "Arrow",
                "2012-10-10",
                "5.8",
                "/mo0FP1GxOFZT4UDde7RFDz5APXF.jpg",
                "209.731",
                "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
                "/dKxkwAJfGuznW8Hu0mhaDJtna0n.jpg"
            ),
            TvShowEntity(
                62286,
                "Fear the Walking Dead",
                "2015-08-23",
                "6.2",
                "/lZMb3R3e5vqukPbeDMeyYGf2ZNG.jpg",
                "210.889",
                "What did the world look like as it was transforming into the horrifying apocalypse depicted in \\\"The Walking Dead\\\"? This spin-off set in Los Angeles, following new characters as they face the beginning of the end of the world, will answer that question.",
                "/nUXzdD2Jo3wV9Q7mIZjK46Yyty4.jpg"
            )
        )
    }

    fun getFavTvShowList(): List<FavoriteTvShowEntity> {
        return listOf(
            FavoriteTvShowEntity(
                1412,
                "Arrow",
                "2012-10-10",
                "5.8",
                "/mo0FP1GxOFZT4UDde7RFDz5APXF.jpg",
                "209.731",
                "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
                "/dKxkwAJfGuznW8Hu0mhaDJtna0n.jpg"
            ),
            FavoriteTvShowEntity(
                62286,
                "Fear the Walking Dead",
                "2015-08-23",
                "6.2",
                "/lZMb3R3e5vqukPbeDMeyYGf2ZNG.jpg",
                "210.889",
                "What did the world look like as it was transforming into the horrifying apocalypse depicted in \\\"The Walking Dead\\\"? This spin-off set in Los Angeles, following new characters as they face the beginning of the end of the world, will answer that question.",
                "/nUXzdD2Jo3wV9Q7mIZjK46Yyty4.jpg"
            )
        )
    }

    fun getDetailMovie(): DetailMovieEntity {
        return DetailMovieEntity(
            429617,
            "Spider-Man: Far from Home",
            "2019-07-02",
            "7.7",
            "/lcq8dVxeeOqHvvgcte707K0KVx5.jpg",
            "371.667",
            "Peter Parker and his friends go on a summer trip to Europe. However, they will hardly be able to rest - Peter will have to agree to help Nick Fury uncover the mystery of creatures that cause natural disasters and destruction throughout the continent.",
            "/5myQbDzw3l8K9yofUXRJ4UTVgam.jpg",
            "Action, Adventure",
            "129"
        )
    }

    fun getDetailTvShow(): DetailTvShowEntity {
        return DetailTvShowEntity(
            1412,
            "Arrow",
            "2012-10-10",
            "5.8",
            "/mo0FP1GxOFZT4UDde7RFDz5APXF.jpg",
            "209.731",
            "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
            "/dKxkwAJfGuznW8Hu0mhaDJtna0n.jpg",
            "Crime, Drama"
        )
    }
}