package com.wildan.moviecatalogue.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.wildan.moviecatalogue.MainActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.utils.RecyclerViewItemCountAssertion
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        activityRule.activity.supportFragmentManager.beginTransaction()
    }

    @Test
    fun testAppBehaviour() {
        Thread.sleep(2000)
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))

        Thread.sleep(2000)
        onView(withId(R.id.rv_movie)).check(RecyclerViewItemCountAssertion(10))
        onView(withId(R.id.rv_movie)).perform(scrollToPosition<RecyclerView.ViewHolder>(8))

        Thread.sleep(2000)
        onView(withId(R.id.rv_movie)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                8,
                click()
            )
        )

        Thread.sleep(2000)
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_date)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genres)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_description)).check(matches(isDisplayed()))

        Thread.sleep(2000)
        pressBack()

        Thread.sleep(2000)
        onView(withId(R.id.tvshow_menu)).perform(click())

        Thread.sleep(2000)
        onView(withId(R.id.rv_movie)).check(RecyclerViewItemCountAssertion(10))
        onView(withId(R.id.rv_movie)).perform(scrollToPosition<RecyclerView.ViewHolder>(9))

        Thread.sleep(2000)
        onView(withId(R.id.rv_movie)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                9,
                click()
            )
        )

        Thread.sleep(2000)

        pressBack()
        Thread.sleep(1000)
    }
}