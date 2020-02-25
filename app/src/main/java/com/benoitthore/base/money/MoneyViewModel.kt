package com.benoitthore.base.money

import com.benoitthore.base.lib.*
import com.benoitthore.base.lib.mvvm.BaseViewModel
import com.benoitthore.base.money.businesslogic.CalculateCurrentMonthlyBudgetUseCase
import com.benoitthore.base.money.businesslogic.BudgetValues
import com.benoitthore.base.money.businesslogic.CurrentTimeInfo
import org.koin.core.KoinComponent
import org.koin.core.inject

class MoneyViewModel : BaseViewModel<MoneyViewModel.State, MoneyViewModel.Event>(), KoinComponent {

    private val useCase: CalculateCurrentMonthlyBudgetUseCase by inject()
    private val timeHelper: TimeHelper by inject()
    override val initialState: State get() = State()

    // TODO Fill in state with UI values
    data class State(val data: BudgetValues? = null)

    sealed class Event {
        object WrongInput : Event()
        object CloseKeyboard : Event()
    }

    @JvmOverloads
    fun onOkayClicked(moneyLeft: String, moneyPerDay: Number = 50) {
        val moneyLeft = moneyLeft.toFloatOrNull() ?: return run {
            emitEvent { Event.WrongInput }
            emitState { State(null) }
        }

        val budgetValues =
                with(timeHelper) {
                    useCase.calculateCurrentMonthlyBudget(
                            moneyLeft = moneyLeft,
                            startWithMoney = moneyPerDay.toFloat()
                                    * (today.endOfMonth - today.beginningOfMonth).nbOfDays,
                            currentTimeInfo = CurrentTimeInfo()
                    )
                }
        emitState {
            State(budgetValues)
        }
        emitEvent { Event.CloseKeyboard }
    }

}


