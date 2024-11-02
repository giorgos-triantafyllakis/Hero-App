package com.example.heroapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.heroapp.network.dataClasses.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.lifecycle.LiveData
import junit.framework.TestCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.kotlin.*


@ExperimentalCoroutinesApi
class HeroesViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private lateinit var viewModel: HeroesViewModel
    private val pagingData: PagingData<CharacterRestModel> = mock()

    @Before
    fun setup() {
        viewModel = HeroesViewModel()
    }

    @Test
    fun `charactersLiveData should be initially empty`() {
        // Create the view model
        val viewModel = HeroesViewModel()
        // Check if charactersLiveData is null
        assertNull(viewModel.charactersLiveData.value)
    }

    @Test
    fun `charactersLiveData should be updated with PagingData`() = runTest {
        // Mock Pager and Flow
        val pager: Pager<Int, CharacterRestModel> = mock()
        val flow: Flow<PagingData<CharacterRestModel>> = flowOf(pagingData)
        whenever(pager.flow).thenReturn(flow)


        // Verify LiveData is updated with expected PagingData
        viewModel.charactersLiveData.observeForTesting {
            assertEquals(pagingData, viewModel.charactersLiveData.value)
        }
    }
}

private fun <T> LiveData<T>.observeForTesting(block: (T?) -> Unit) {
    val observer = Observer<T> { data -> block(data) }
    observeForever(observer)
}

@ExperimentalCoroutinesApi
class MainDispatcherRule(private val dispatcher: TestDispatcher = StandardTestDispatcher()) :
    TestWatcher() {

    override fun starting(description: Description) = Dispatchers.setMain(dispatcher)
    override fun finished(description: Description) = Dispatchers.resetMain()

}