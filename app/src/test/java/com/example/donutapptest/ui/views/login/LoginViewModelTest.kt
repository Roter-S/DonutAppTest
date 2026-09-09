package com.example.donutapptest.ui.views.login

import com.example.donutapptest.data.repository.FakeUserRepository
import com.example.donutapptest.domain.model.User
import com.example.donutapptest.domain.usecase.auth.LoginUseCase
import com.example.donutapptest.domain.usecase.auth.ValidatePasswordUseCase
import com.example.donutapptest.domain.usecase.auth.ValidateUsernameUseCase
import com.example.donutapptest.ui.common.AppNotification
import com.example.donutapptest.ui.common.AppNotificationManager
import com.example.donutapptest.ui.views.login.model.LoginUiEvent
import com.example.donutapptest.util.MainDispatcherRule
import com.example.donutapptest.utils.enums.Alerts
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var validateUsernameUseCase: ValidateUsernameUseCase
    private lateinit var validatePasswordUseCase: ValidatePasswordUseCase
    private lateinit var notificationManager: AppNotificationManager
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        loginUseCase = LoginUseCase(fakeUserRepository)
        validateUsernameUseCase = ValidateUsernameUseCase()
        validatePasswordUseCase = ValidatePasswordUseCase()
        notificationManager = AppNotificationManager()
        viewModel = LoginViewModel(
            loginUseCase = loginUseCase,
            validateUsernameUseCase = validateUsernameUseCase,
            validatePasswordUseCase = validatePasswordUseCase,
            notificationManager = notificationManager
        )
    }

    @Test
    fun `initial state has empty fields and invalid form`() {
        val state = viewModel.uiState.value
        assertEquals("", state.username)
        assertEquals("", state.password)
        assertFalse(state.isFormValid)
        assertFalse(state.isLoading)
        assertNull(state.usernameError)
        assertNull(state.passwordError)
    }

    @Test
    fun `when username is less than 8 characters then form is invalid and shows error`() {
        viewModel.onUsernameChange("short")
        viewModel.onPasswordChange("ValidPass1")

        val state = viewModel.uiState.value
        assertFalse(state.isFormValid)
        assertNotNull(state.usernameError)
    }

    @Test
    fun `when username and password meet all requirements then form is valid`() {
        viewModel.onUsernameChange("validUsername")
        viewModel.onPasswordChange("ValidPassword1")

        val state = viewModel.uiState.value
        assertTrue(state.isFormValid)
        assertNull(state.usernameError)
        assertNull(state.passwordError)
    }

    @Test
    fun `when login succeeds then emits NavigateToHome event`() = runTest {
        val user = User(id = 1, firstName = "Test", lastName = "User", email = "validUsername")
        fakeUserRepository.addUser(user, "ValidPassword1")

        viewModel.onUsernameChange("validUsername")
        viewModel.onPasswordChange("ValidPassword1")

        var receivedEvent: LoginUiEvent? = null
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiEvent.collect { receivedEvent = it }
        }

        viewModel.login()

        assertEquals(LoginUiEvent.NavigateToHome, receivedEvent)
        assertFalse(viewModel.uiState.value.isLoading)

        collectJob.cancel()
    }

    @Test
    fun `when login fails then shows error notification and remains on screen`() = runTest {
        viewModel.onUsernameChange("validUsername")
        viewModel.onPasswordChange("ValidPassword1")

        var receivedNotification: AppNotification? = null
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            notificationManager.notifications.collect { receivedNotification = it }
        }

        viewModel.login()

        assertNotNull(receivedNotification)
        assertEquals(Alerts.ERROR, receivedNotification?.type)
        assertFalse(viewModel.uiState.value.isLoading)

        collectJob.cancel()
    }
}
