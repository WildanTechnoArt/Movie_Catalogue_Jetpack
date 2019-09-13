package com.wildan.moviecatalogue.ui.main.tv

import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.model.Movie

object TvShowDummy {

    private var tvShows: ArrayList<Movie>? = null

    private val tvShowTitle = object : ArrayList<String>(){
        init {
            add("Arrow")
            add("Doom Patrol")
            add("Dragon ball")
            add("Fairy Tail")
            add("Family Guy")
            add("The Flash")
            add("Game of Thrones")
            add("Gotham")
            add("Grey\"'\"s Anatomy")
            add("Hanna")
        }
    }

    private val tvShowDate = object : ArrayList<String>(){
        init {
            add("October 10, 2012")
            add("February 15, 2019")
            add("February 26, 1986")
            add("October 12, 2009")
            add("January 31, 1999")
            add("October 7, 2014")
            add("April 17, 2011")
            add("September 22, 2014")
            add("March 27, 2005")
            add("March 28, 2019")
        }
    }

    private val tvShowRating = object : ArrayList<String>(){
        init {
            add("5.8")
            add("6.1")
            add("7.2")
            add("6.4")
            add("6.5")
            add("6.7")
            add("8.1")
            add("6.8")
            add("6.3")
            add("6.4")
        }
    }

    private val tvShowGenres = object : ArrayList<String>(){
        init {
            add("Crime, Drama, Mystery, Action, Adventure")
            add("Science Fiction, Fantasy, Action, Adventure")
            add("Comedy, Science Fiction, Fantasy, Animation, Action, Adventure")
            add("Action, Adventure, Animation, Comedy, Science Fiction")
            add("Animation, Comedy")
            add("Drama, Science Fiction, Fantasy")
            add("Science Fiction, Fantasy, Drama, Action, Adventure")
            add("Drama, Fantasy, Crime")
            add("Drama")
            add("Action, Adventure, Drama")
        }
    }

    private val tvShowDescriptiom = object : ArrayList<String>(){
        init {
            add("Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.")
            add("The Doom Patrol\"'\"s members each suffered horrible accidents that gave them superhuman abilities - but also left them scarred and disfigured. Traumatized and downtrodden, the team found purpose through The Chief, who brought them together to investigate the weirdest phenomena in existence - and to protect Earth from what they find.")
            add("Long ago in the mountains, a fighting master known as Gohan discovered a strange boy whom he named Goku. Gohan raised him and trained Goku in martial arts until he died. The young and very strong boy was on his own, but easily managed. Then one day, Goku met a teenage girl named Bulma, whose search for the dragon balls brought her to Goku\"'\"s home. Together, they set off to find all seven dragon balls in an adventure that would change Goku\"'\"s life forever. See how Goku met his life long friends Bulma, Yamcha, Krillin, Master Roshi and more. And see his adventures as a boy, all leading up to Dragonball Z and later Dragonball GT.")
            add("Lucy is a 17-year-old girl, who wants to be a full-fledged mage. One day when visiting Harujion Town, she meets Natsu, a young man who gets sick easily by any type of transportation. But Natsu isn\"'\"t just any ordinary kid, he\"'\"s a member of one of the world\"'\"s most infamous mage guilds: Fairy Tail.")
            add("Sick, twisted, politically incorrect and Freakin\"'\" Sweet animated series featuring the adventures of the dysfunctional Griffin family. Bumbling Peter and long-suffering Lois have three kids. Stewie (a brilliant but sadistic baby bent on killing his mother and taking over the world), Meg (the oldest, and is the most unpopular girl in town) and Chris (the middle kid, he\"'\"s not very bright but has a passion for movies). The final member of the family is Brian - a talking dog and much more than a pet, he keeps Stewie in check whilst sipping Martinis and sorting through his own life issues.")
            add("After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won\"'\"t be long before the world learns what Barry Allen has become…The Flash.")
            add("Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night\"'\"s Watch, is all that stands between the realms of men and icy horrors beyond.")
            add("Before there was Batman, there was GOTHAM. Everyone knows the name Commissioner Gordon. He is one of the crime world\"'\"s greatest foes, a man whose reputation is synonymous with law and order. But what is known of Gordon\"'\"s story and his rise from rookie detective to Police Commissioner? What did it take to navigate the multiple layers of corruption that secretly ruled Gotham City, the spawning ground of the world\"'\"s most iconic villains? And what circumstances created them – the larger-than-life personas who would become Catwoman, The Penguin, The Riddler, Two-Face and The Joker?")
            add("Follows the personal and professional lives of a group of doctors at Seattle\"'\"s Grey Sloan Memorial Hospital.")
            add("This thriller and coming-of-age drama follows the journey of an extraordinary young girl as she evades the relentless pursuit of an off-book CIA agent and tries to unearth the truth behind who she is. Based on the 2011 Joe Wright film.")
        }
    }

    private val tvShowPoster = object : ArrayList<Int>(){
        init {
            add(R.drawable.poster_arrow)
            add(R.drawable.poster_doom_patrol)
            add(R.drawable.poster_dragon_ball)
            add(R.drawable.poster_fairytail)
            add(R.drawable.poster_family_guy)
            add(R.drawable.poster_flash)
            add(R.drawable.poster_god)
            add(R.drawable.poster_gotham)
            add(R.drawable.poster_grey_anatomy)
            add(R.drawable.poster_hanna)
        }
    }

    fun listTvShow(): ArrayList<Movie>{
        tvShows = ArrayList()

        for (i in 0 until tvShowTitle.size) {
            val tvShow = Movie()
            tvShow.poster = tvShowPoster[i]
            tvShow.title = tvShowTitle[i]
            tvShow.date = tvShowDate[i]
            tvShow.rating = tvShowRating[i]
            tvShow.genres = tvShowGenres[i]
            tvShow.description = tvShowDescriptiom[i]
            tvShows?.add(tvShow)
        }

        return tvShows as ArrayList<Movie>
    }

}