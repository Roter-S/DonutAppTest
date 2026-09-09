package com.example.donutapptest.ui.views.register

import com.example.donutapptest.data.repository.FakeUserRepository
import com.example.donutapptest.domain.model.User
import com.example.donutapptest.domain.usecase.auth.RegisterUseCase
import com.example.donutapptest.domain.usecase.auth.ValidateEmailUseCase
import com.example.donutapptest.domain.usecase.auth.ValidatePasswordUseCase
import com.example.donutapptest.ui.common.AppNotification
import com.example.donutapptest.ui.common.AppNotificationManager
import com.example.donutapptest.ui.views.register.model.RegisterUiEvent
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
class RegisterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var registerUseCase: RegisterUseCase
    private lateinit var validateEmailUseCase: ValidateEmailUseCase
    private lateinit var validatePasswordUseCase: ValidatePasswordUseCase
    private lateinit var notificationManager: AppNotificationManager
    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        registerUseCase = RegisterUseCase(fakeUserRepository)
        validateEmailUseCase = ValidateEmailUseCase()
        validatePasswordUseCase = ValidatePasswordUseCase()
        notificationManager = AppNotificationManager()
        viewModel = RegisterViewModel(
            registerUseCase = registerUseCase,
            validateEmailUseCase = validateEmailUseCase,
            validatePasswordUseCase = validatePasswordUseCase,
            notificationManager = notificationManager
        )
    }

    @Test
    fun `initial state has empty fields and invalid form`() {
        val state = viewModel.uiState.value
        assertEquals("", state.firstName)
        assertEquals("", state.lastName)
        assertEquals("", state.email)
        assertEquals("", state.password)
        assertEquals("", state.confirmPassword)
        assertFalse(state.isFormValid)
    }

    @Test
    fun `when passwords do not match then confirmPasswordError is present and form is invalid`() {
        viewModel.onFirstNameChange("John")
        viewModel.onLastNameChange("Doe")
        viewModel.onEmailChange("john@example.com")
        viewModel.onPasswordChange("password123")
        viewModel.onConfirmPasswordChange("differentPassword")

        val state = viewModel.uiState.value
        assertFalse(state.isFormValid)
        assertNotNull(state.confirmPasswordError)
    }

    @Test
    fun `when all fields are valid and match then form is valid`() {
        viewModel.onFirstNameChange("John")
        viewModel.onLastNameChange("Doe")
        viewModel.onEmailChange("john@example.com")
        viewModel.onPasswordChange("password123")
        viewModel.onConfirmPasswordChange("password123")

        val state = viewModel.uiState.value
        assertTrue(state.isFormValid)
        assertNull(state.firstNameError)
        assertNull(state.lastNameError)
        assertNull(state.emailError)
        assertNull(state.passwordError)
        assertNull(state.confirmPasswordError)
    }

    @Test
    fun `when registration succeeds then emits NavigateToHome event`() = runTest {
        viewModel.onFirstNameChange("John")
        viewModel.onLastNameChange("Doe")
        viewModel.onEmailChange("john@example.com")
        viewModel.onPasswordChange("password123")
        viewModel.onConfirmPasswordChange("password123")

        var receivedEvent: RegisterUiEvent? = null
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiEvent.collect { receivedEvent = it }
        }

        viewModel.register()

        assertEquals(RegisterUiEvent.NavigateToHome, receivedEvent)
        assertFalse(viewModel.uiState.value.isLoading)

        collectJob.cancel()
    }

    @Test
    fun `when email is already registered then shows error notification`() = runTest {
        val existingUser = User(id = 1, firstName = "Existing", lastName = "User", email = "existing@example.com")
        fakeUserRepository.addUser(existingUser, "password123")

        viewModel.onFirstNameChange("John")
        viewModel.onLastNameChange("Doe")
        viewModel.onEmailChange("existing@example.com")
        viewModel.onPasswordChange("password123")
        viewModel.onConfirmPasswordChange("password123")

        var receivedNotification: AppNotification? = null
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            notificationManager.notifications.collect { receivedNotification = it }
        }

        viewModel.register()

        assertNotNull(receivedNotification)
        assertEquals(Alerts.ERROR, receivedNotification?.type)
        assertNotNull(viewModel.uiState.value.emailError)
        assertFalse(viewModel.uiState.value.isLoading)

        collectJob.cancel()
    }
}
