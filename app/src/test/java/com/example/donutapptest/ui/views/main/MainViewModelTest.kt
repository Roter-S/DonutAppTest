package com.example.donutapptest.ui.views.main

import com.example.donutapptest.data.repository.FakeUserRepository
import com.example.donutapptest.domain.usecase.auth.CheckSessionUseCase
import com.example.donutapptest.domain.usecase.auth.GetOnboardingStatusUseCase
import com.example.donutapptest.domain.usecase.auth.LogoutUseCase
import com.example.donutapptest.domain.usecase.auth.SetOnboardingCompletedUseCase
import com.example.donutapptest.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var checkSessionUseCase: CheckSessionUseCase
    private lateinit var logoutUseCase: LogoutUseCase
    private lateinit var getOnboardingStatusUseCase: GetOnboardingStatusUseCase
    private lateinit var setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        checkSessionUseCase = CheckSessionUseCase(fakeUserRepository)
        logoutUseCase = LogoutUseCase(fakeUserRepository)
        getOnboardingStatusUseCase = GetOnboardingStatusUseCase(fakeUserRepository)
        setOnboardingCompletedUseCase = SetOnboardingCompletedUseCase(fakeUserRepository)
    }

    @Test
    fun `when session is active then state flows emit active session and username`() = runTest {
        fakeUserRepository.setInitialSession(active = true, username = "testuser@example.com")

        viewModel = MainViewModel(
            checkSessionUseCase = checkSessionUseCase,
            logoutUseCase = logoutUseCase,
            getOnboardingStatusUseCase = getOnboardingStatusUseCase,
            setOnboardingCompletedUseCase = setOnboardingCompletedUseCase
        )

        assertEquals(true, viewModel.isUserLoggedIn.value)
        assertEquals("testuser@example.com", viewModel.username.value)
    }

    @Test
    fun `logout clears session and sets isLoggedOut to true`() = runTest {
        fakeUserRepository.setInitialSession(active = true, username = "testuser@example.com")

        viewModel = MainViewModel(
            checkSessionUseCase = checkSessionUseCase,
            logoutUseCase = logoutUseCase,
            getOnboardingStatusUseCase = getOnboardingStatusUseCase,
            setOnboardingCompletedUseCase = setOnboardingCompletedUseCase
        )

        viewModel.logout()

        assertTrue(viewModel.isLoggedOut.value)
        assertEquals(false, viewModel.isUserLoggedIn.value)

        viewModel.resetLogoutState()
        assertFalse(viewModel.isLoggedOut.value)
    }

    @Test
    fun `completeOnboarding updates onboarding status`() = runTest {
        viewModel = MainViewModel(
            checkSessionUseCase = checkSessionUseCase,
            logoutUseCase = logoutUseCase,
            getOnboardingStatusUseCase = getOnboardingStatusUseCase,
            setOnboardingCompletedUseCase = setOnboardingCompletedUseCase
        )

        viewModel.completeOnboarding()

        assertEquals(true, viewModel.isOnboardingCompleted.value)
    }
}
