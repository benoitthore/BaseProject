package com.benoitthore.base.money

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.benoitthore.base.R
import com.benoitthore.base.databinding.FragmentMoneyBinding
import com.benoitthore.base.lib.mvvm.Accumulator
import org.koin.android.viewmodel.ext.android.viewModel

class MoneyFragment : Fragment() {

    private lateinit var binding: FragmentMoneyBinding

    val viewModel by viewModel<MoneyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observe(this, ::update, ::onEvent)
        binding.moneyViewModel = viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentMoneyBinding.inflate(inflater, container, false)
                .also { binding = it }
                .root
    }

    fun onEvent(acc: Accumulator<MoneyViewModel.Event>) {
        acc.consume {
            when (it) {
                MoneyViewModel.Event.WrongInput -> Toast.makeText(requireContext(), "InputError", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun update(state: MoneyViewModel.State) {
        if (state.data != null) {
            binding.budgetView.updateViewModel(state.data)
        }
    }
}

