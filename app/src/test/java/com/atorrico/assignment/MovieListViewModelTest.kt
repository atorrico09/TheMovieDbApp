package com.atorrico.assignment

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.atorrico.assignment.domain.model.Movie
import com.atorrico.assignment.domain.usecase.GetMovieListUseCase
import com.atorrico.assignment.presentation.screen.movielist.MovieListViewModel
import com.atorrico.assignment.presentation.util.Result
import com.atorrico.assignment.presentation.util.mapSuccess
import com.atorrico.assignment.util.MainCoroutineRule
import com.atorrico.assignment.util.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val mockGetMovieListUseCase: GetMovieListUseCase = mockk()

    private val viewModel = MovieListViewModel(
        mockGetMovieListUseCase
    )

    @MockK
    lateinit var mockMovie: Movie

    @MockK
    lateinit var mockMovieList: List<Movie>

    @MockK
    lateinit var mockException: Exception

    @CallSuper
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockMovieList = listOf(mockMovie)
    }

    @Test
    fun `getMovieList emits success result`() = runTest {
        //given
        coEvery { mockGetMovieListUseCase.invoke() } returns Result.Success(mockMovieList)

        //when
        val response = viewModel.getMovieList()

        //then
        val success = response.getOrAwaitValue()
        assertEquals(success.mapSuccess { it }, Result.Success(mockMovieList).data)
    }

    @Test
    fun `getMovieList emits failure result`() = runTest {
        //given
        coEvery { mockGetMovieListUseCase.invoke() } returns Result.Failure(mockException)

        //when
        val response = viewModel.getMovieList()

        //then
        val failure = response.getOrAwaitValue()
        assertEquals(failure.mapSuccess { it }, Result.Failure<Exception>(mockException))
    }
}