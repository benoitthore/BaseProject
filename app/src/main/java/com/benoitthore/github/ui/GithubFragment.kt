package com.benoitthore.github.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.benoitthore.base.R
import com.benoitthore.base.databinding.GithubFragmentBinding
import com.benoitthore.base.lib.mvvm.Accumulator
import com.benoitthore.base.lib.ui.VerticalSpaceItemDecoration
import com.benoitthore.github.GithubRepositoryViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class GithubFragment : Fragment(R.layout.github_fragment) {
    private lateinit var binding: GithubFragmentBinding
    private val viewModel by viewModel<GithubRepositoryViewModel>()
    private val adapter = GithubRepositoryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = GithubFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.repositoryRecyclerView.adapter = adapter
        val verticalSpacing = requireContext().resources.getDimensionPixelSize(R.dimen.repository_card_vertical_margin)
        binding.repositoryRecyclerView.addItemDecoration(VerticalSpaceItemDecoration(verticalSpacing))
        viewModel.observe(this, ::observeState, ::observeEvent)

        binding.repositoryProgressBar.isVisible = true
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun observeState(state: GithubRepositoryViewModel.State) {
        binding.swipeToRefresh.isRefreshing = false
        binding.repositoryProgressBar.isVisible = false

        when (state) {
            is GithubRepositoryViewModel.State.Success -> {
                setState(state)
            }
            GithubRepositoryViewModel.State.Error -> {
                setError()
            }
        }
    }

    private fun setError() {
        adapter.repositories = emptyList()
        binding.errorTextView.isVisible = true
        binding.repositoryRecyclerView.isVisible = false
    }

    private fun setState(state: GithubRepositoryViewModel.State.Success) {
        adapter.repositories = state.repositoryList
        binding.errorTextView.isVisible = false
        binding.repositoryRecyclerView.isVisible = true
    }

    private fun observeEvent(accumulator: Accumulator<GithubRepositoryViewModel.Event>) {
        accumulator.consume {
            when (it) {
                GithubRepositoryViewModel.Event.NetworkError -> showNetworkError()
                GithubRepositoryViewModel.Event.ApiError -> showApiError()
            }
        }
    }

    private fun showApiError() {
        binding.errorTextView.setText(R.string.api_error)
    }

    private fun showNetworkError() {
        binding.errorTextView.setText(R.string.network_error)
    }
}