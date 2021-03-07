package com.fneis.myevents.ui.features.event.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fneis.myevents.R
import com.fneis.myevents.model.data.Event
import com.fneis.myevents.repository.`interface`.EventRepository
import com.fneis.myevents.repository.callback.Result
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.coroutines.CoroutineContext

class EventViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: EventViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread2")

    @Mock
    private lateinit var listLiveDataObserver: Observer<Pair<List<Event>?, Int?>>

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `when view model getEvents get success then sets _listLiveData`() = runBlockingTest {

        // Arrange
        val items = listOf(
            Event(
                1,
                "Teste",
                "http://lproweb.procempa.com.br/pmpa/prefpoa/seda_news/usu_img/Papel%20de%20Parede.png",
                "Feira de adoção de animais na Redenção",
                29.99,
                -51.2146267,
                -30.0392981,
                1534784400
            )
        )

        val resultSuccess = MockRepository(Result.success(items))
        val pair: Pair<List<Event>?, Int?> = Pair(items, null)

        viewModel = EventViewModel(resultSuccess)
        viewModel.listLiveData.observeForever(listLiveDataObserver)
        // Act
        viewModel.getEvents()

        // Assert
        verify(listLiveDataObserver).onChanged(pair)
    }

    @Test
    fun `when view model getEvents get success and empty then sets _listLiveData whith list empty`() = runBlockingTest {
        // Arrange
        val items = listOf<Event>()

        val resultSuccess = MockRepository(Result.success(items))
        val pair = Pair(null, R.string.list_empty)

        viewModel = EventViewModel(resultSuccess)
        viewModel.listLiveData.observeForever(listLiveDataObserver)
        // Act
        viewModel.getEvents()

        // Assert
        verify(listLiveDataObserver).onChanged(pair)
    }

    @Test
    fun `when view model getEvents get error then sets _listLiveData with error`() = runBlockingTest {
        // Arrange
        val error = R.string.response_timeout
        val pair: Pair<List<Event>?, Int?> = Pair(null, error)

        viewModel = EventViewModel(MockRepository(Result.error(error)))
        viewModel.listLiveData.observeForever(listLiveDataObserver)
        // Act
        viewModel.getEvents()

        // Assert
        verify(listLiveDataObserver).onChanged(pair)
    }
}

class MockRepository(private val result: Result<List<Event>>) : EventRepository {
    override suspend fun getEvents(): Result<List<Event>> {
        return result
    }
}
