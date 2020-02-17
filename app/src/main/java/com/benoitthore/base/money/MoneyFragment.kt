package com.benoitthore.base.money

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.benoitthore.base.databinding.FragmentMoneyBinding
import com.benoitthore.base.lib.mvvm.Accumulator
import org.koin.android.viewmodel.ext.android.viewModel


class MoneyFragment : Fragment() {

    private lateinit var binding: FragmentMoneyBinding

    val viewModel by viewModel<MoneyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observe(this) {
            stateObserver(::update)
            eventObserver(::onEvent)
        }
        binding.moneyViewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        binding.inputField.showKeyboard()
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
                MoneyViewModel.Event.CloseKeyboard -> binding.inputField.closeKeyboard()
            }
        }
    }

    fun update(state: MoneyViewModel.State) {
        binding.budgetView.updateViewModel(state.data)
    }
}

private fun EditText.closeKeyboard() {
    val imm = getSystemService(context, InputMethodManager::class.java)
    imm!!.hideSoftInputFromWindow(windowToken, 0)

}

private fun EditText.showKeyboard() {
    val imm = getSystemService(context, InputMethodManager::class.java)
    imm!!.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}
