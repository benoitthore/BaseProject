package com.benoitthore.github

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.benoitthore.base.lib.MyDispatchers
import com.benoitthore.base.lib.data.ApiResponse
import com.benoitthore.github.model.GithubRepo
import com.benoitthore.github.model.repository.GithubRepositoryModel
import com.nhaarman.mockito_kotlin.*
import junit.framework.Assert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

@ExperimentalCoroutinesApi
class GithubRepositoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    val coroutineDispatcher = TestCoroutineDispatcher()
    val mockDispatchers: MyDispatchers = MyDispatchers(coroutineDispatcher, coroutineDispatcher, coroutineDispatcher, coroutineDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(coroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        coroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `creating ViewModel fetches repo`() {
        coroutineDispatcher.runBlockingTest {
            val mockRepo = mock<GithubRepo>()
            val list = listOf(GithubRepositoryModel("Repo 1"))
            whenever(mockRepo.getOrgData(any())).thenReturn(ApiResponse.Success(list))

            val vm = GithubRepositoryViewModel(mockRepo, mockDispatchers)

            val state: GithubRepositoryViewModel.State = GithubRepositoryViewModel.State.Success(list)

            val lifecycleOwner = mock<LifecycleOwner>()
            val lifeCycle = mock<Lifecycle>()

            whenever(lifecycleOwner.lifecycle).thenReturn(lifeCycle)

            vm.refresh()

            assertEquals(state, vm.state.value)
        }
    }

    @Test
    fun `creating ViewModel fetches repo with error`() {
        coroutineDispatcher.runBlockingTest {
            val mockRepo = mock<GithubRepo>()
            whenever(mockRepo.getOrgData(any())).thenReturn(ApiResponse.NetworkError())

            val vm = GithubRepositoryViewModel(mockRepo,mockDispatchers)

            val exceptedState: GithubRepositoryViewModel.State = GithubRepositoryViewModel.State.Error
            val expectedEvent: GithubRepositoryViewModel.Event = GithubRepositoryViewModel.Event.NetworkError

            assertEquals(exceptedState, vm.state.value)
            assertEquals(expectedEvent, vm.lastEvent)
        }
    }

}
