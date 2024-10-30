package com.android.tymexmobile.feature.detail_user

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.domain.usecase.GetUserDetailResult
import com.android.domain.usecase.GetUserDetailUseCase
import com.android.model.User
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * Created by BM Anderson on 30/10/2024.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class DetailUserViewModelTest {

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    var instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: DetailUserViewModel

    // Mocked UseCase
    private lateinit var getUserDetailUseCase: GetUserDetailUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getUserDetailUseCase = mock(GetUserDetailUseCase::class.java)
        viewModel = DetailUserViewModel(getUserDetailUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Initial state should be EmptyState`() = runTest {
        // Assert initial state
        val initialState = viewModel.stateFlow.first()
        assertTrue(initialState is DetailUserViewModel.ViewState.EmptyState)
    }

    @Test
    fun `getUser emits GetUserSuccess when use case returns success`() = runTest {
        // Arrange
        val user = User(login = "user1")
        val result = flowOf(GetUserDetailResult.GetUserDetailSuccess(user))
        whenever(getUserDetailUseCase.invoke(anyString())).thenReturn(result)

        // Act
        viewModel.getUser("user1")

        var state = viewModel.stateFlow.first()
        assertTrue(state is DetailUserViewModel.ViewState.GetUserSuccess)
        assertEquals(user, (state as DetailUserViewModel.ViewState.GetUserSuccess).user)
    }
}