package com.wildan.moviecatalogue.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.wildan.moviecatalogue.MainActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.utils.EspressoIdlingResource
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.After

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoIdlingResource)
        activityRule.activity.supportFragmentManager.beginTransaction()
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoIdlingResource)
    }

    @Test
    fun testAppBehaviour() {
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_movie)).perform(scrollToPosition<RecyclerView.ViewHolder>(6))

        onView(withId(R.id.rv_movie)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                6,
                click()
            )
        )

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_date)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_genres)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_description)).check(matches(isDisplayed()))

        onView(withId(R.id.add_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_favorite)).perform(click())

        pressBack()

        onView(withId(R.id.tvshow_menu)).perform(click())

        onView(withId(R.id.rv_movie)).perform(scrollToPosition<RecyclerView.ViewHolder>(6))

        onView(withId(R.id.rv_movie)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                6,
                click()
            )
        )

        onView(withId(R.id.add_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_favorite)).perform(click())

        pressBack()

        onView(withId(R.id.favorite_menu)).perform(click())

        onView(allOf(isDisplayed(), withId(R.id.rv_favorite))).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.add_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_favorite)).perform(click())

        pressBack()

        onView(withId(R.id.tabs_movie)).check(matches(isDisplayed()))

        val tab = allOf(withText("Tv Show"), isDescendantOfA(withId(R.id.tabs_movie)))
        onView(tab).perform(click())

        onView(allOf(isDisplayed(), withId(R.id.rv_favorite))).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.add_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_favorite)).perform(click())

        pressBack()
    }
}