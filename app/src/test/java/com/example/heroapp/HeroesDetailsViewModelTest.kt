package com.example.heroapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.heroapp.network.ApiService
import com.example.heroapp.network.MarvelApi
import com.example.heroapp.network.dataClasses.*
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response


class HeroesDetailsViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private lateinit var viewModel: HeroesDetailsViewModel
    private val apiService: ApiService = mockk()

    @Before
    fun setUp() {
        viewModel = HeroesDetailsViewModel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testFetchesCharacterDetails() = runTest {
        // Create a test character
        val testCharacter = CharacterRestModel(
            1011334, "Spider-Man",
            "A teenage boy named Peter Parker who gains spider-like abilities after being bitten by a radioactive spider.",
            Thumbnail(
                path = "http://i.annihil.us/u/prod/marvel/i/mg/c/60/55b6a28ef24fa",
                extension = "jpg"
            ),
            ComicList(1, 1, ""),
            StoryList(1, 1, ""),
            EventList(1, 1, ""),
            SeriesList(1, 1, "")
        )

        val successResponse = Response.success(
            MarvelApiResponse(
                code = 200, data = MarvelApiData(
                    results = listOf(testCharacter), offset = 0,
                    limit = 1,
                    total = 1,
                    count = 1
                ), status = "ok"
            )
        )

        // Mock the ApiService interface to return the success response for getCharacter method
        coEvery { apiService.getCharacter(1011334) } returns successResponse

        // Assign the mocked ApiService to the MarvelApi interface
        MarvelApi.Marvel_Api_Service = apiService

        // Call the fetchCharacters method to fetch the test character details
        viewModel.fetchCharacters(1011334)
        // Wait 3 seconds for the response to be returned giving API more time to respond
        delay(3000)

          // Verify that the live data received the expected values
            assertEquals(listOf(testCharacter), viewModel.character.value)
            assertEquals(testCharacter.thumbnail.getUrl(), viewModel.heroImage.value)
            assertEquals(testCharacter.name, viewModel.heroName.value)
            assertNull(viewModel.error.value)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testFetchEmptyCharacter() = runTest {
        // Create an empty response
        val emptyResponse = Response.success(
            MarvelApiResponse(
                code = 200, data = MarvelApiData(
                    results = emptyList(), offset = 0,
                    limit = 0,
                    total = 0,
                    count = 0
                ), status = "ok"
            )
        )

        // Mock the ApiService interface to return the empty response for getCharacter method
        coEvery { apiService.getCharacter(any()) } returns emptyResponse

        // Call the fetchCharacters method to fetch the character details
        viewModel.fetchCharacters(1011334)

        // Wait for the response to be returned
        delay(3000)

        // Verify that the character LiveData is empty and that no error message is set
        assertTrue(viewModel.character.value.isNullOrEmpty())
        assertNull(viewModel.error.value)
    }
}