package com.benoitthore.base.money

import androidx.lifecycle.*
import kotlinx.coroutines.*
import com.benoitthore.base.lib.mvvm.BaseViewModel
import com.benoitthore.base.lib.*
import org.koin.core.Koin
import org.koin.core.KoinComponent
import kotlin.math.round

class MoneyViewModel : BaseViewModel<MoneyViewModel.State, MoneyViewModel.Event>(), KoinComponent {

    override val initialState: State get() = State()

    data class State(val data: List<Any> = emptyList())

    sealed class Event {
        object Close : Event()
    }


}


// TODO If I spend £x a day, how many days to I need to get back to diff=0
// TODO If I spend £x a day during y days, what will be the value of diff in y days

// r for round, rounds up a number to the 2 decimal places and returns a Float
inline val Number.r get() : Float = round(toFloat() * 100f) / 100f

fun test(
        moneyLeft: Float = 594.27f,
        secondAccurate: Boolean = true,
        startWithMoney: Float = 1550f
): Float {

    val daysInMonth = (today.endOfMonth - today.beginningOfMonth).nbOfDays

    var date = (today - today.beginningOfMonth).nbOfDays + 1f
    if (!secondAccurate) {
        date = date.toInt().toFloat()
    }

    val daysLeft = daysInMonth - date

    val currentMoneyPerDay = moneyLeft / daysLeft

    val targetMoneyPerDay = startWithMoney / daysInMonth
    val targetMoneyLeft = startWithMoney - (date * targetMoneyPerDay)

    val diff = moneyLeft - targetMoneyLeft
    val recoveringTimeWithOriginalDailyTarget = diff / targetMoneyPerDay
    val recoveringTimeWithCurrentDailyTarget = diff / currentMoneyPerDay

    println()

    println("daysInMonth = ${daysInMonth.r}")
    println("date = ${date.r}")
    println("daysLeft = ${daysLeft.r}")

    println()

    println("target money per day = £${targetMoneyPerDay.r}")
    println("current money per day = £${currentMoneyPerDay.r}")
    println("${(100*currentMoneyPerDay/targetMoneyPerDay).r} %")

    println()

    println("money left = £${moneyLeft.r}")
    println("target money left = £${targetMoneyLeft.r}")
    println("diff = £${diff.r}")

    println()

    if (recoveringTimeWithOriginalDailyTarget > 0) {
        println("Time to get back to 0 diff with original daily target: $recoveringTimeWithOriginalDailyTarget")
    } else {
        println("Recovering time with original daily target = ${recoveringTimeWithOriginalDailyTarget.r} days")
    }

    if (recoveringTimeWithCurrentDailyTarget > 0) {
        println("Time to get back to 0 diff with current daily target: $recoveringTimeWithCurrentDailyTarget")
    } else {
        println("Recovering time with current daily target = ${recoveringTimeWithCurrentDailyTarget.r} days")
    }
    return diff
}
