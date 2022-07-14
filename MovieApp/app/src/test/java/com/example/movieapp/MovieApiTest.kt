package com.example.movieapp


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class MovieApiTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @ExperimentalCoroutinesApi
    @Test
    fun `Test Movie Description method` () = mainCoroutineRule.dispatcher.runBlockingTest {
        TestMovieApi().getMovieDescription(616037, "EXAMPLE_KEY").body().apply {
            assertEquals(616037, this?.id)
            assertEquals("Thor: Love and Thunder", this?.title)
            assertEquals("14/07/2022", this?.release_date)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Test List Movie method`() = mainCoroutineRule.dispatcher.runBlockingTest {
        TestMovieApi().getListMovie(1, "EXAMPLE_KEY").body().apply {
            assertEquals(1, this?.page)
            assertEquals(3, this?.results?.size)
            assertEquals(16, this?.total_pages)
        }
    }
}