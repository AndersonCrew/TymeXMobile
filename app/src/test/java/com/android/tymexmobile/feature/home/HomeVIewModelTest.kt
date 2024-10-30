package com.android.tymexmobile.feature.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.domain.usecase.GetUsersUseCase
import com.android.domain.usecase.GetUsersUseResult
import com.android.model.User
import com.android.tymexmobile.feature.home.HomeViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by BM Anderson on 30/10/2024.
 */

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var getUsersUseCase: GetUsersUseCase

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        homeViewModel = HomeViewModel(getUsersUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be EmptyState`() = runTest {
        val initialState = homeViewModel.stateFlow.first()
        assertTrue(initialState is HomeViewModel.ViewState.EmptyState)
    }

    @Test
    fun `getUser emits GetListUserSuccess when use case returns success`() = runTest {
        // Arrange
        val users = listOf(User(login = "user1"), User(login = "user2"))
        val result = flowOf(GetUsersUseResult.GetUsersUseResultSuccess(users))
        whenever(getUsersUseCase.invoke(any())).thenReturn(result)

        // Act
        homeViewModel.getUser(1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = homeViewModel.stateFlow.first()
        assertTrue(state is HomeViewModel.ViewState.GetListUserSuccess)
        assertEquals(users, (state as HomeViewModel.ViewState.GetListUserSuccess).list)
    }

    @Test
    fun `getUser sets hasLoadMore to false after successful data load`() = runTest {
        // Arrange
        val users = listOf(User(login = "user1"))
        val result = flowOf(GetUsersUseResult.GetUsersUseResultSuccess(users))
        whenever(getUsersUseCase.invoke(any())).thenReturn(result)

        // Act
        homeViewModel.getUser(1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertFalse(homeViewModel.hasLoadMore())
    }
}