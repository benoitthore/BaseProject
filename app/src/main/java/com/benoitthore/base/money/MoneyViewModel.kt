package com.benoitthore.base.money

import com.benoitthore.base.lib.mvvm.BaseViewModel
import com.benoitthore.base.money.businesslogic.CalculateCurrentMonthlyBudgetUseCase
import com.benoitthore.base.money.businesslogic.CurrentTimeInfo
import com.benoitthore.base.money.businesslogic.r
import com.benoitthore.enamel.core.plusAssign
import com.benoitthore.enamel.core.print
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.StringBuilder
import kotlin.math.absoluteValue

class MoneyViewModel : BaseViewModel<MoneyViewModel.State, MoneyViewModel.Event>(), KoinComponent {

    private val useCase: CalculateCurrentMonthlyBudgetUseCase by inject()
    private val currencySymbol = "£"

    override val initialState: State get() = State()

    data class State(val header: String = "", val list: List<BudgetItem> = emptyList())
    data class BudgetItem(val title: String, val value: String)

    sealed class Event {
        object WrongInput : Event()
        object CloseKeyboard : Event()
    }

    fun onOkayClicked(moneyLeft: String, monthlyBudget: String) {

        val moneyLeft = moneyLeft.toFloatOrNull() ?: return run {
            emitEvent { Event.WrongInput }
            emitState { State() }
        }

        val budgetValues = useCase.calculateCurrentMonthlyBudget(
                moneyLeft = moneyLeft,
                startWithMoney = monthlyBudget.toFloat(),
                currentTimeInfo = CurrentTimeInfo()
        )

        emitState {
            State(budgetValues.moneyPerDay.currentMoneyPerDay.toCurrency(),
                    listOf(
                            BudgetItem("Money Left", budgetValues.moneyPerMonth.moneyLeft.toCurrency()),
                            BudgetItem("Target money Left", budgetValues.moneyPerMonth.targetMoneyLeft.toCurrency()),
                            BudgetItem("Difference", budgetValues.moneyPerMonth.diff.toCurrency()),


                            budgetValues.recoveringTime.recoveringTimeWithOriginalDailyTarget.let { recoveringTime ->
                                BudgetItem(if (recoveringTime > 0) "Extra time" else "Recovering time", recoveringTime.absoluteValue.toTime())
                            }
                    ))
        }
        emitEvent { Event.CloseKeyboard }
    }

    private fun Number.toCurrency() = r.let {
        val s = if (it < 0) {
            "-"
        } else ""

        s + "$currencySymbol${it.absoluteValue}"
    }

    private fun Number.toTime() = toTimeList().joinToString(separator = ", ")
    private fun Number.toTimeList(): List<String> {
        val days = toFloat()
        val daysInt = toInt()

        val hours = (days - daysInt) * 24
        val hoursInt = hours.toInt()

        val minutes = (hours - hoursInt) * 60
        val minutesInt = minutes.toInt()

        val seconds = (minutes - minutesInt) * 60
        val secondsInt = seconds.toInt()

        val list = mutableListOf("$daysInt days")
        if (hoursInt > 0)
            list += "$hoursInt hours"
        if (minutesInt > 0)
            list += "$minutesInt minutes"
        if (secondsInt > 0)
            list += "$secondsInt seconds"

        return list
    }
}


