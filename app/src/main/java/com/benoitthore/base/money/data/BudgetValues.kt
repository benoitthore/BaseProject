package com.benoitthore.base.money.data

import androidx.lifecycle.ViewModel
import com.benoitthore.base.lib.beginningOfMonth
import com.benoitthore.base.lib.endOfMonth
import com.benoitthore.base.lib.nbOfDays
import com.benoitthore.base.lib.today
import kotlin.math.round


// r for round, rounds up a number to the 2 decimal places and returns a Float
inline val Number.r get() : Float = round(toFloat() * 100f) / 100f

data class CurrentTimeInfo(
        val daysInMonth: Number = (today.endOfMonth - today.beginningOfMonth).nbOfDays,
        val date: Float = (today - today.beginningOfMonth).nbOfDays + 1f
) {
    /**
     * Returns a copy of this object with the date set to beginning of day (midnight)
     */
    fun precisionDay() = copy(date = date.toInt().toFloat())
}

data class BudgetValues(val time: Time,
                        val moneyPerDay: MoneyPerDay,
                        val moneyPerMonth: MoneyPerMonth) {


    val recoveringTime = RecoveringTime(
            recoveringTimeWithOriginalDailyTarget = moneyPerMonth.diff / moneyPerDay.targetMoneyPerDay,
            recoveringTimeWithCurrentDailyTarget = moneyPerMonth.diff / moneyPerDay.currentMoneyPerDay
    )

    fun round(): BudgetValues = copy(
            moneyPerDay = moneyPerDay.round(),
            moneyPerMonth = moneyPerMonth.round())

    data class Time(
            val daysInMonth: Float,
            val date: Float,
            val daysLeft: Float
    )

    data class MoneyPerDay(
            val targetMoneyPerDay: Float,
            val currentMoneyPerDay: Float
    ) {
        val ratio: Float = (100 * currentMoneyPerDay / targetMoneyPerDay)
        fun round() = copy(
                targetMoneyPerDay = targetMoneyPerDay.r,
                currentMoneyPerDay = currentMoneyPerDay.r
        )
    }

    data class MoneyPerMonth(
            val moneyLeft: Float,
            val targetMoneyLeft: Float,
            val diff: Float
    ) {
        fun round() = copy(
                moneyLeft = moneyLeft.r,
                targetMoneyLeft = targetMoneyLeft.r,
                diff = diff.r
        )
    }

    data class RecoveringTime(
            val recoveringTimeWithOriginalDailyTarget: Float,
            val recoveringTimeWithCurrentDailyTarget: Float
    )
}