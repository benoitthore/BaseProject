package com.benoitthore.base.helloworld

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.benoitthore.base.R
import com.benoitthore.base.lib.coroutines.mainThreadCoroutine
import com.benoitthore.base.lib.mvvm.Accumulator
import com.benoitthore.base.lib.mvvm.lazyViewModel
import com.benoitthore.enamel.core.threading.singleThreadCoroutine
import kotlinx.android.synthetic.main.hello_world_fragment.*
import kotlinx.coroutines.*
import java.util.*
import java.util.Collection
import java.util.concurrent.Executors
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HelloWorldFragment : Fragment(R.layout.hello_world_fragment) {


    private val viewModel by lazyViewModel<HelloWorldViewModel>()
    private val adapter = HelloWorldAdapter()
    private var defaultColor: Int = Color.MAGENTA

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        helloWorldList.adapter = adapter
        helloWorldList.layoutManager = LinearLayoutManager(requireContext())

        button.setOnClickListener {
            viewModel.onButtonClicked()
        }

        button.setOnLongClickListener {
            viewModel.onButtonLongClicked()
            true
        }

        // setup background color
        defaultColor = (view.background as? ColorDrawable)?.color ?: defaultColor
        view.setBackgroundColor(defaultColor)

        viewModel.observe(this, ::renderView, ::handleEvent)
    }

    private fun handleEvent(accumulator: Accumulator<HelloWorldViewModel.Event>) {
        accumulator.consume {
            val a = when (it) {
                is HelloWorldViewModel.Event.Blink1 -> {
                    blink(Color.RED)
                }
                is HelloWorldViewModel.Event.Blink2 -> {
                    blink(Color.GREEN)
                }
                HelloWorldViewModel.Event.Close -> {
                    requireActivity().finish()
                }
            }
        }
    }

    private fun blink(color: Int) {
        viewModel.mainThreadCoroutine {
            val view = view ?: return@mainThreadCoroutine
            view.setBackgroundColor(color)
            delay(1000L)
            view.setBackgroundColor(defaultColor)
        }
    }

    private fun renderView(state: HelloWorldViewModel.State) {
        adapter.list = state.data
    }
}