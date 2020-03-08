package com.benoitthore.base.money

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.benoitthore.base.BuildConfig
import com.benoitthore.base.databinding.FragmentMoneyBinding
import com.benoitthore.base.lib.mvvm.Accumulator
import kotlinx.android.synthetic.main.fragment_money.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File


val Number.dp: Float
    get() = Resources.getSystem().displayMetrics.density * toFloat()

class MoneyFragment : Fragment() {

    private lateinit var binding: FragmentMoneyBinding

    val viewModel by viewModel<MoneyViewModel>()

    private inline val isChloe get() = BuildConfig.Chloe

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observe(this) {
            stateObserver(::update)
            eventObserver(::onEvent)
        }
        binding.moneyViewModel = viewModel

        binding.monthlyBudgetInputField.setText(
                if (isChloe) "500" else "1500"
        )

    }

    override fun onResume() {
        super.onResume()
        if (isChloe) {
            Toast.makeText(requireContext(), "Je t'aime", Toast.LENGTH_SHORT).apply {
                val backgroundDrawable = GradientDrawable()
                backgroundDrawable.cornerRadius = 8.dp
                val tv = ((view as? ViewGroup)?.getChildAt(0) as? TextView)
                tv?.setTextColor(Color.WHITE)
                view.background = GradientDrawable().apply {
                    cornerRadius = 16.dp
                    setColor(0xFF_0096AE.toInt())
                }
            }.show()
        }
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
                MoneyViewModel.Event.CloseKeyboard -> {
                }// TODO
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

fun main() {

}