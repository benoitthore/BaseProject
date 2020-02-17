package com.benoitthore.base.money

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter
import com.benoitthore.base.databinding.BudgetValuesViewBinding
import com.benoitthore.base.lib.viewScope
import com.benoitthore.base.money.data.BudgetValues
import com.benoitthore.base.money.data.r
import com.benoitthore.enamel.core.ignoreUnknownObjectMapper
import kotlinx.coroutines.*

class BudgetValuesView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private inline val inflater
        get() = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val binding = BudgetValuesViewBinding.inflate(inflater, this, true)

    var budgetValues
        get() = binding.budgetValues
        set(value) {
            binding.budgetValues = value
        }

    fun updateViewModel(budgetValues: BudgetValues) {
        binding.budgetValues = budgetValues
    }
}

@BindingAdapter("fromJson")
fun TextView.fromJson(obj: Any?) {
    if (obj == null) return

    viewScope.cancel()
    viewScope.launch {
        text = withContext(Dispatchers.IO) {
            val data = ignoreUnknownObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj)
            yield()
            Log.d("ben_", "done")
            data
        }

    }
}

@BindingAdapter("formattedMoneyText")
fun TextView.formattedMoneyText(obj: Number?) {
    obj?.let {
        text = "£${obj.r}" //TODO Format properly using String.format
    }
}

@BindingAdapter("hideIfNull")
fun View.hideIfNull(obj: Any?) {
    isGone = obj == null
}