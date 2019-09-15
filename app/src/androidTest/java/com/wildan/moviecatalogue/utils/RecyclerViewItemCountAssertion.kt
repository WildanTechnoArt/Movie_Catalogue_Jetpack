package com.wildan.moviecatalogue.utils

import android.view.View
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertNotNull

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat

class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        assertNotNull(adapter)
        assertThat(adapter!!.itemCount, `is`(expectedCount))
    }
}