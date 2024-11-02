package com.example.heroapp

import androidx.paging.PagingSource
import com.example.heroapp.network.MarvelApi
import com.example.heroapp.network.dataClasses.*
import com.example.heroapp.paging.HeroesPagingSource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.*
import retrofit2.Response

class HeroesPagingSourceTest {

    private lateinit var apiService: MarvelApi
    private lateinit var pagingSource: HeroesPagingSource

    @Before
    fun setUp() {
        apiService = mockk()
        pagingSource = HeroesPagingSource(apiService)
    }

    @Test
    fun `load returns Page with data when successful`() = runBlocking {
        // Given
        val expectedData = listOf(
            CharacterRestModel(
                1011334,
                "3-D Man",
                "",
                Thumbnail(
                    path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
                    extension = "jpg"
                ),
                ComicList(
                    12,
                    12,
                    "http://gateway.marvel.com/v1/public/characters/1011334/comics",
                    arrayOf(
                        ComicSummary(
                            resourceURI = "http://gateway.marvel.com/v1/public/comics/21366",
                            name = "Avengers: The Initiative (2007) #14)"
                        ),
                        ComicSummary(
                            resourceURI = "http://gateway.marvel.com/v1/public/comics/21975",
                            name = "Avengers: The Initiative (2007) #17"
                        ),
                        ComicSummary(
                            resourceURI = "http://gateway.marvel.com/v1/public/comics/22299",
                            name = "Avengers: The Initiative (2007) #18"
                        )
                    )
                ),
                StoryList(
                    3,
                    3,
                    "http://gateway.marvel.com/v1/public/characters/1011334/stories"
                ),
                EventList(
                    1,
                    1,
                    "http://gateway.marvel.com/v1/public/characters/1011334/events"
                ),
                SeriesList(
                    3,
                    3,
                    "http://gateway.marvel.com/v1/public/characters/1011334/series"
                )
            )
            ,
            CharacterRestModel(
                1017100,
                "A-Bomb",
                "A teenage boy named Peter Parker who gains spider-like abilities after being bitten by a radioactive spider.",
                Thumbnail(
                    path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
                    extension = "jpg"
                ),
                ComicList(
                    12,
                    12,
                    "http://gateway.marvel.com/v1/public/characters/1017100/comics"
                ),
                StoryList(
                    3,
                    3,
                    "http://gateway.marvel.com/v1/public/characters/1017100/stories"
                ),
                EventList(
                    1,
                    1,
                    "http://gateway.marvel.com/v1/public/characters/1017100/events"
                ),
                SeriesList(
                    3,
                    3,
                    "http://gateway.marvel.com/v1/public/characters/1017100/series"
                )
            )
        )
        val response = Response.success(
            MarvelApiResponse(
                data = MarvelApiData(
                    offset = 0,
                    limit = 2,
                    total = HeroesPagingSource.PAGE_SIZE,
                    count = HeroesPagingSource.PAGE_SIZE, results = expectedData
                ), code = 200, status = "ok"
            )
        )
        coEvery { apiService.Marvel_Api_Service.getAllCharacters(offset = 0, 10) } returns response
        val params = PagingSource.LoadParams.Refresh<Int>(null, HeroesPagingSource.PAGE_SIZE, false)

        // When
        val result = pagingSource.load(params)

        // Then
        assertEquals(PagingSource.LoadResult.Page(expectedData, null, 1), result)
    }

    @Test
    fun `load returns Error when unsuccessful`() = runBlocking {
        // Given
        val expectedError = "Failed to fetch characters"
        coEvery { apiService.Marvel_Api_Service.getAllCharacters(offset = 0, 10) } throws Exception(
            expectedError
        )
        val params = PagingSource.LoadParams.Refresh<Int>(null, HeroesPagingSource.PAGE_SIZE, false)

        // When
        val result = pagingSource.load(params)

        // Then
        assertTrue(result is PagingSource.LoadResult.Error)
        assertEquals(expectedError, (result as PagingSource.LoadResult.Error).throwable.message)
    }

    @Test
    fun `load returns empty when API returns empty`() = runBlocking {
        // Given
        val response = Response.success(
            MarvelApiResponse(
                data = MarvelApiData(
                    offset = 0,
                    limit = 0,
                    total = 0,
                    count = 0,
                    results = emptyList()
                ),
                code = 200,
                status = "ok"
            )
        )
        coEvery { apiService.Marvel_Api_Service.getAllCharacters(any(), any()) } returns response
        val params = PagingSource.LoadParams.Refresh<Int>(null, HeroesPagingSource.PAGE_SIZE, false)

        // When
        val result = pagingSource.load(params)

        // Then
        assertEquals(PagingSource.LoadResult.Page(emptyList(), null, null), result)
    }

    @Test
    fun `load returns partial page when API returns less data than expected`() = runBlocking {
        // Given
        val expectedData = listOf(
            CharacterRestModel(
                1011334,
                "3-D Man",
                "",
                Thumbnail(
                    path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
                    extension = "jpg"
                ),
                ComicList(
                    12,
                    12,
                    "http://gateway.marvel.com/v1/public/characters/1011334/comics",
                    arrayOf(
                        ComicSummary(
                            resourceURI = "http://gateway.marvel.com/v1/public/comics/21366",
                            name = "Avengers: The Initiative (2007) #14)"
                        ),
                        ComicSummary(
                            resourceURI = "http://gateway.marvel.com/v1/public/comics/21975",
                            name = "Avengers: The Initiative (2007) #17"
                        ),
                        ComicSummary(
                            resourceURI = "http://gateway.marvel.com/v1/public/comics/22299",
                            name = "Avengers: The Initiative (2007) #18"
                        )
                    )
                ),
                StoryList(
                    3,
                    3,
                    "http://gateway.marvel.com/v1/public/characters/1011334/stories"
                ),
                EventList(
                    1,
                    1,
                    "http://gateway.marvel.com/v1/public/characters/1011334/events"
                ),
                SeriesList(
                    3,
                    3,
                    "http://gateway.marvel.com/v1/public/characters/1011334/series"
                )
            ),
            CharacterRestModel(
                1017100,
                "A-Bomb",
                "A teenage boy named Peter Parker who gains spider-like abilities after being bitten by a radioactive spider.",
                Thumbnail(
                    path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
                    extension = "jpg"
                ),
                ComicList(
                    12,
                    12,
                    "http://gateway.marvel.com/v1/public/characters/1017100/comics"
                ),
                StoryList(
                    3,
                    3,
                    "http://gateway.marvel.com/v1/public/characters/1017100/stories"
                ),
                EventList(
                    1,
                    1,
                    "http://gateway.marvel.com/v1/public/characters/1017100/events"
                ),
                SeriesList(
                    3,
                    3,
                    "http://gateway.marvel.com/v1/public/characters/1017100/series"
                )
            )
        )
        val response = Response.success(
            MarvelApiResponse(
                data = MarvelApiData(
                    offset = 0,
                    limit = 2,
                    total = HeroesPagingSource.PAGE_SIZE,
                    count = expectedData.size - 1, // Return one less item than expected
                    results = expectedData.dropLast(1)
                ), code = 200, status = "ok"
            )
        )
        coEvery { apiService.Marvel_Api_Service.getAllCharacters(offset = 0, 10) } returns response
        val params = PagingSource.LoadParams.Refresh<Int>(null, HeroesPagingSource.PAGE_SIZE, false)

        // When
        val result = pagingSource.load(params)

        // Then
        assertEquals(PagingSource.LoadResult.Page(expectedData.dropLast(1), null, 1), result)
    }
}