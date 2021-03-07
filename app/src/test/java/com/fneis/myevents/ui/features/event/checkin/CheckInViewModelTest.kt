package com.fneis.myevents.ui.features.event.checkin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fneis.myevents.model.data.CheckIn
import com.fneis.myevents.model.response.CheckInResponse
import com.fneis.myevents.repository.`interface`.CheckInRepository
import com.fneis.myevents.repository.callback.Result
import com.fneis.myevents.ui.features.event.checkIn.CheckInState
import com.fneis.myevents.ui.features.event.checkIn.CheckInViewModel
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CheckInViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: CheckInViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Mock
    private lateinit var checkinLiveDateObsever: Observer<Boolean>

    @Mock
    private lateinit var formLiveDataObsever: Observer<CheckInState>

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
    fun `when view model sendCheckin get success then sets checkinLiveDate whith true`() = runBlockingTest {
        // Arrange
        val checkInResponse = CheckInResponse(200)
        val resultSuccess = MockCheckInRepository(Result.success(checkInResponse))
        val checkIn = CheckIn(1, "Filipe", "filipe.aneis@gmail.com")

        viewModel = CheckInViewModel(resultSuccess)
        viewModel.checkinLiveDate.observeForever(checkinLiveDateObsever)
        // Act
        viewModel.sendCheckin(checkIn)

        // Assert
        verify(checkinLiveDateObsever).onChanged(true)
    }

    @Test
    fun `when view model sendCheckin get success then sets checkinLiveDate whith false`() = runBlockingTest {
        // Arrange
        val checkInResponse = CheckInResponse(400)
        val resultSuccess = MockCheckInRepository(Result.success(checkInResponse))
        val checkIn = CheckIn(1, "Filipe", "filipe.aneis@gmail.com")

        viewModel = CheckInViewModel(resultSuccess)
        viewModel.checkinLiveDate.observeForever(checkinLiveDateObsever)
        // Act
        viewModel.sendCheckin(checkIn)

        // Assert
        verify(checkinLiveDateObsever).onChanged(false)
    }

    @Test
    fun `when view model sendCheckin get error then sets checkinLiveDate whith false`() = runBlockingTest {

        // Arrange
        val resultSuccess = MockCheckInRepository(Result.error(0))
        val checkIn = CheckIn(1, "Filipe", "filipe.aneis@gmail.com")

        viewModel = CheckInViewModel(resultSuccess)
        viewModel.checkinLiveDate.observeForever(checkinLiveDateObsever)
        // Act
        viewModel.sendCheckin(checkIn)

        // Assert
        verify(checkinLiveDateObsever).onChanged(false)
    }

    @Test
    fun `when view model validateEmail get e-mail empty the sets formLiveData whith EmailEmpty`() {

        // Arrange
        val email = ""
        val resultSuccess = MockCheckInRepository(Result.success(CheckInResponse(400)))
        viewModel = CheckInViewModel(resultSuccess)
        viewModel.formLiveData.observeForever(formLiveDataObsever)

        // Act
        viewModel.validateEmailWith(email)

        // Assert
        verify(formLiveDataObsever).onChanged(CheckInState.EmailEmpty)
    }

    @Test
    fun `when view model validateEmail get e-mail invalid the sets formLiveData whith EmailInvalid`() {

        // Arrange
        val email = "abc"
        val resultSuccess = MockCheckInRepository(Result.success(CheckInResponse(400)))
        viewModel = CheckInViewModel(resultSuccess)
        viewModel.formLiveData.observeForever(formLiveDataObsever)

        // Act
        viewModel.validateEmailWith(email)

        // Assert
        verify(formLiveDataObsever).onChanged(CheckInState.EmailInvalid)
    }

    @Test
    fun `when view model validateName get name invalid the sets formLiveData whith ShortName`() {

        // Arrange
        val name = "ab"
        val resultSuccess = MockCheckInRepository(Result.success(CheckInResponse(400)))
        viewModel = CheckInViewModel(resultSuccess)
        viewModel.formLiveData.observeForever(formLiveDataObsever)

        // Act
        viewModel.validateNameWith(name)

        // Assert
        verify(formLiveDataObsever).onChanged(CheckInState.ShortName)
    }

    @Test
    fun `when view model validateName get name empty the sets formLiveData whith NameEmpty`() {

        // Arrange
        val name = ""
        val resultSuccess = MockCheckInRepository(Result.success(CheckInResponse(400)))
        viewModel = CheckInViewModel(resultSuccess)
        viewModel.formLiveData.observeForever(formLiveDataObsever)

        // Act
        viewModel.validateNameWith(name)

        // Assert
        verify(formLiveDataObsever).onChanged(CheckInState.NameEmpty)
    }
}

class MockCheckInRepository(private val result: Result<CheckInResponse>) : CheckInRepository {
    override suspend fun sendCheckIn(checkIn: CheckIn): Result<CheckInResponse> {
        return result
    }
}
