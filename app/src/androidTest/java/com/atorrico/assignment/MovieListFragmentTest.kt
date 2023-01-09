package com.atorrico.assignment

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.atorrico.assignment.presentation.screen.movielist.MovieListFragment
import com.atorrico.assignment.presentation.screen.movielist.MovieViewHolder
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//TODO: Fix test being cancelled on runtime
@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
class MovieListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun isListFragmentVisible_onAppLunch() {
        onView(withId(R.id.rvMovieList)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationToInMovieDetailFragment() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        launchFragmentInHiltContainer<MovieListFragment> {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(this.requireView(), navController)
        }

        onView(withId(R.id.rvMovieList)).perform(
            actionOnItemAtPosition<MovieViewHolder>(1, click())
        )
        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()))
        assertEquals(navController.currentDestination?.id, R.id.layout_detail_movie)
    }
}