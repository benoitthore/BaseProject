package com.benoitthore.base.helloworld

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.benoitthore.base.R
import com.benoitthore.base.lib.mvvm.Accumulator
import kotlinx.android.synthetic.main.hello_world_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class HelloWorldFragment : Fragment(R.layout.hello_world_fragment) {

    private val viewModel by viewModel<HelloWorldViewModel>()
    private val adapter = HelloWorldAdapter { viewModel.onDeleteClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        helloWorldList.adapter = adapter
        helloWorldList.layoutManager = LinearLayoutManager(requireContext())

        addButton.setOnClickListener {
            viewModel.onAddClicked(editText.text.toString())
        }

        viewModel.observe(this, ::renderView, ::handleEvent)
    }

    private fun handleEvent(accumulator: Accumulator<HelloWorldViewModel.Event>) {
        accumulator.consume {
            when (it) {
                HelloWorldViewModel.Event.Close -> {
                    requireActivity().finish()
                }
            }
        }
    }

    private fun renderView(state: HelloWorldViewModel.State) {
        adapter.list = state.data
    }
}