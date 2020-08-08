package com.benoitthore.sonoff.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.benoitthore.base.R

class SOnOffFragment : Fragment(R.layout.sonoff) {

    private val vm = SonoffViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.on_off_button)

//        GlobalScope.launch(Dispatchers.IO) {
//            val ip = GetIpAddress(requireContext()).invoke()
//            withContext(Dispatchers.Main) {
//                button.text = ip ?: "Not connected to Wifi"
//            }
//        }

        button.setOnClickListener {
            vm.switch()
        }

        vm.observe(this) {
            stateObserver { newState ->
                when (newState) {
                    is SonoffViewModel.State.Success -> {
                        button.text = if (newState.isOn) "ON" else "OFF"
                    }
                    SonoffViewModel.State.Loading -> {
                        button.text = "..."
                    }
                    is SonoffViewModel.State.Error -> button.text = newState.message ?: "Error"
                }

            }
        }
    }
}