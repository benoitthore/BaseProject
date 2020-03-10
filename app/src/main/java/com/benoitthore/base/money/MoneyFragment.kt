package com.benoitthore.base.money

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.benoitthore.base.BuildConfig
import com.benoitthore.base.R
import com.benoitthore.base.databinding.BudgetValuesItemBinding
import com.benoitthore.base.databinding.FragmentMoneyBinding
import com.benoitthore.base.lib.mvvm.Accumulator
import com.benoitthore.enamel.core.print
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import com.xwray.groupie.databinding.BindableItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


val Number.dp: Float
    get() = Resources.getSystem().displayMetrics.density * toFloat()

class MoneyFragment : Fragment() {

    private lateinit var binding: FragmentMoneyBinding

    val viewModel by viewModel<MoneyViewModel>()

    private val adapter = GroupAdapter<GroupieViewHolder>()
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
        binding.budgetRecyclerView.adapter = adapter
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
        binding.header = state.header
        adapter.clear()
        adapter.addAll(
                state.list.map {
                    BudgetItem(it)
                }
        )
    }

    class BudgetItem(val stateItem: MoneyViewModel.BudgetItem) : BindableItem<BudgetValuesItemBinding>() {
        init {
            stateItem.print
        }
        override fun getLayout(): Int = R.layout.budget_values_item
        override fun bind(viewBinding: BudgetValuesItemBinding, position: Int) {
            viewBinding.budgetItem = stateItem
        }
    }
}



