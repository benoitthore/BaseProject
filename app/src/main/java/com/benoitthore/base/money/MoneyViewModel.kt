package com.benoitthore.base.money

import com.benoitthore.base.lib.beginningOfMonth
import com.benoitthore.base.lib.endOfMonth
import com.benoitthore.base.lib.mvvm.BaseViewModel
import com.benoitthore.base.lib.nbOfDays
import com.benoitthore.base.lib.today
import com.benoitthore.base.money.data.BudgetValues
import com.benoitthore.base.money.data.CurrentTimeInfo
import org.koin.core.KoinComponent

class MoneyViewModel : BaseViewModel<MoneyViewModel.State, MoneyViewModel.Event>(), KoinComponent {

    override val initialState: State get() = State()

    data class State(val data: BudgetValues? = null)

    sealed class Event {
        object WrongInput : Event()
        // Nothing for now
    }

    @JvmOverloads
    fun onOkayClicked(moneyLeft: String, moneyPerDay: Number = 50) {
        val moneyLeft = moneyLeft.toFloatOrNull() ?: return run {
            emitEvent { Event.WrongInput }
            emitState { State(null) }
        }

        val budgetValues = calculateCurrentMonthlyBudget(
                moneyLeft = moneyLeft,
                startWithMoney = moneyPerDay.toFloat() * (today.endOfMonth - today.beginningOfMonth).nbOfDays,
                currentTimeInfo = CurrentTimeInfo()
        )
        emitState {
            State(budgetValues)
        }
    }

}


// TODO If I spend £x a day, how many days to I need to get back to diff=0
// TODO If I spend £x a day during y days, what will be the value of diff in y days

fun calculateCurrentMonthlyBudget(
        moneyLeft: Number,
        startWithMoney: Number,
        currentTimeInfo: CurrentTimeInfo = CurrentTimeInfo()
): BudgetValues {
    val moneyLeft = moneyLeft.toFloat()
    val startWithMoney = startWithMoney.toFloat()
    val daysInMonth = currentTimeInfo.daysInMonth.toFloat()
    val date = currentTimeInfo.date

    val daysLeft = daysInMonth - date

    val currentMoneyPerDay = moneyLeft / daysLeft

    val targetMoneyPerDay = startWithMoney / daysInMonth
    val targetMoneyLeft = startWithMoney - (date * targetMoneyPerDay)

    val diff = moneyLeft - targetMoneyLeft

    return BudgetValues(
            time = BudgetValues.Time(daysInMonth = daysInMonth, date = date, daysLeft = daysLeft),
            moneyPerDay = BudgetValues.MoneyPerDay(targetMoneyPerDay = targetMoneyPerDay, currentMoneyPerDay = currentMoneyPerDay),
            moneyPerMonth = BudgetValues.MoneyPerMonth(moneyLeft = moneyLeft, targetMoneyLeft = targetMoneyLeft, diff = diff)
    )
}