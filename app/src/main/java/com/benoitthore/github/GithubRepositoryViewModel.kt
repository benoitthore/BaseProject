package com.benoitthore.github

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.benoitthore.base.lib.MyDispatchers
import com.benoitthore.base.lib.mvvm.BaseViewModel
import com.benoitthore.base.lib.data.ApiResponse
import com.benoitthore.github.GithubRepositoryViewModel.*
import com.benoitthore.github.model.GithubRepo
import com.benoitthore.github.model.repository.GithubOrganisation
import com.benoitthore.github.model.repository.GithubRepositoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GithubRepositoryViewModel(private val githubRepo: GithubRepo, val dispatchers: MyDispatchers) : BaseViewModel<State, Event>() {

    override val initialState: State = State.Success(emptyList())

    sealed class State {
        data class Success(val repositoryList: List<GithubRepositoryModel>) : State()
        object Error : State()
    }

    sealed class Event {
        object NetworkError : Event()
        object ApiError : Event()
    }

    private var job: Job? = null

    init {
        refresh()
    }

    fun refresh() {
        if (job?.isActive == true) {
            return
        }

        job = viewModelScope.launch(dispatchers.io) {

            val repositoryResponse = githubRepo.getOrgData(GithubOrganisation.Square)

            val (newState: State, event: Event?) = repositoryResponse.toStateEvent()

            emitState { newState }
            event?.let {
                emitEvent {
                    event
                }
            }
        }
    }

    fun ApiResponse<List<GithubRepositoryModel>>.toStateEvent(): Pair<State, Event?> =
            when (this) {
                is ApiResponse.Success -> State.Success(this.value) to null
                is ApiResponse.ApiError -> State.Error to Event.ApiError
                is ApiResponse.NetworkError -> State.Error to Event.NetworkError
            }
}