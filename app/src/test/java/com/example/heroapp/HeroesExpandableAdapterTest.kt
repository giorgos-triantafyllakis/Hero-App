package com.example.heroapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.heroapp.network.dataClasses.*
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.mock


class HeroesExpandableAdapterTest {

    private lateinit var adapter: HeroesExpandableAdapter
    @get:Rule var rule: TestRule = InstantTaskExecutorRule()
    private lateinit var heroDetailsLiveData: MutableLiveData<List<CharacterRestModel>>


    @Before
    fun setUp() {
        heroDetailsLiveData = MutableLiveData()
        adapter = HeroesExpandableAdapter(mock(), heroDetailsLiveData)
    }

    @Test
    fun testGetGroup() {
        // test parent group names
        assertEquals("Comics", adapter.getGroup(ParentGroup.COMICS.ordinal))
        assertEquals("Stories", adapter.getGroup(ParentGroup.STORIES.ordinal))
        assertEquals("Events", adapter.getGroup(ParentGroup.EVENTS.ordinal))
        assertEquals("Series", adapter.getGroup(ParentGroup.SERIES.ordinal))
    }

    @Test
    fun testGetChild() {
        // create a CharacterRestModel object with test data
        val comics =
            listOf(ComicSummary("Comic 1", "Comic Name"), ComicSummary("Comic 2", "Comic Name"))
        val stories = listOf(
            StorySummary("Story 1", "Story Name", "type 1"),
            StorySummary("Story 2", "Story Name", "type 2")
        )
        val events =
            listOf(EventSummary("Event 1", "Event Name"), EventSummary("Event 2", "Event Name"))

        val series = listOf(
            SeriesSummary("Series 1", "Series Name"),
            SeriesSummary("Series 2", "Series Name")
        )
        val characterRestModel = CharacterRestModel(
            1, "Name", "description", Thumbnail(
                path = "http://i.annihil.us/u/prod/marvel/i/mg/c/60/55b6a28ef24fa",
                extension = "jpg"
            ), ComicList(
                available = comics.size,
                returned = comics.size,
                collectionURI = "",comics.toTypedArray()
            ),
            StoryList
                (
                available = stories.size,
                returned = stories.size,
                collectionURI = "", stories.toTypedArray()
            ), EventList(
                available = events.size,
                returned = events.size,
                collectionURI = "", events.toTypedArray(),
            ), SeriesList(
                available = series.size,
                returned = series.size,
                collectionURI = "", series.toTypedArray()
            )
        )

        // set the LiveData value to the test data
      heroDetailsLiveData.value = listOf(characterRestModel)

        // test child values
        assertEquals("Comic Name, Comic Name", adapter.getChild(ParentGroup.COMICS.ordinal, 0))
        assertEquals("Story Name, Story Name", adapter.getChild(ParentGroup.STORIES.ordinal, 0))
        assertEquals("Event Name, Event Name", adapter.getChild(ParentGroup.EVENTS.ordinal, 0))
        assertEquals("Series Name, Series Name", adapter.getChild(ParentGroup.SERIES.ordinal, 0))
    }
}