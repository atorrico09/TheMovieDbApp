package com.atorrico.assignment

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.atorrico.assignment.domain.model.Movie
import com.atorrico.assignment.domain.usecase.GetMovieListUseCase
import com.atorrico.assignment.domain.usecase.GetMovieUseCase
import com.atorrico.assignment.presentation.screen.moviedetail.MovieDetailViewModel
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
class MovieDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val mockGetMovieUseCase: GetMovieUseCase = mockk()

    private val viewModel = MovieDetailViewModel(
        mockGetMovieUseCase
    )

    @MockK
    lateinit var mockMovie: Movie

    @MockK
    lateinit var mockException: Exception

    @CallSuper
    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `getMovieDetail emits success result`() = runTest {
        //given
        coEvery { mockGetMovieUseCase.invoke(1) } returns Result.Success(mockMovie)

        //when
        val response = viewModel.getMovie(1)

        //then
        val success = response.getOrAwaitValue()
        assertEquals(success.mapSuccess { it }, Result.Success(mockMovie).data)
    }

    @Test
    fun `getMovieDetail emits failure result`() = runTest {
        //given
        coEvery { mockGetMovieUseCase.invoke(1) } returns Result.Failure(mockException)

        //when
        val response = viewModel.getMovie(1)

        //then
        val failure = response.getOrAwaitValue()
        assertEquals(failure.mapSuccess { it }, Result.Failure<Exception>(mockException))
    }
}