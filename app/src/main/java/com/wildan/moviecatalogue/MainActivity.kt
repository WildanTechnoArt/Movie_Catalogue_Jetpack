package com.wildan.moviecatalogue

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wildan.moviecatalogue.movie.MovieFragment
import com.wildan.moviecatalogue.tv.TvShowFragment
import com.wildan.moviecatalogue.utils.UtilsConstant.KEY_FRAGMENT
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var pageContent: Fragment? = MovieFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        if (savedInstanceState == null) {
            pageContent?.let {
                supportFragmentManager.beginTransaction().replace(R.id.container, it).commit()
            }
        } else {
            pageContent = supportFragmentManager.getFragment(savedInstanceState, KEY_FRAGMENT)
            pageContent?.let {
                supportFragmentManager.beginTransaction().replace(R.id.container, it).commit()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        pageContent?.let { supportFragmentManager.putFragment(outState, KEY_FRAGMENT, it) }
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onNavigationItemSelected(menu: MenuItem): Boolean {
        when (menu.itemId) {
            R.id.movie_menu -> pageContent = MovieFragment()
            R.id.tvshow_menu -> pageContent = TvShowFragment()
        }
        pageContent?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, it)
                .commit()
        }
        return true
    }
}