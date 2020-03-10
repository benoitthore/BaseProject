package com.benoitthore.base.money.businesslogic

import com.benoitthore.base.lib.TimeHelper
import kotlin.math.round


// r for round, rounds up a number to the 2 decimal places and returns a Float
inline val Number.r get() : Float = round(toFloat() * 100f) / 100f

class CurrentTimeInfo(daysInMonth: Number? = null, date: Float? = null) : TimeHelper {

    val daysInMonth: Number = daysInMonth ?: (today.endOfMonth - today.beginningOfMonth).nbOfDays
    val date: Float = date ?: (today - today.beginningOfMonth).nbOfDays + 1f
    /**
     * Returns a copy of this object with the date set to beginning of day (midnight)
     */
    fun precisionDay() = CurrentTimeInfo(daysInMonth = daysInMonth, date = date.toInt().toFloat())
}

data class BudgetValues(val time: Time,
                        val moneyPerDay: MoneyPerDay,
                        val moneyPerMonth: MoneyPerMonth) {


    val recoveringTime = RecoveringTime(
            recoveringTimeWithOriginalDailyTarget = moneyPerMonth.diff / moneyPerDay.targetMoneyPerDay,
            recoveringTimeWithCurrentDailyTarget = moneyPerMonth.diff / moneyPerDay.currentMoneyPerDay
    )

    data class Time(
            val daysInMonth: Float,
            val date: Float,
            val daysLeft: Float
    )

    data class MoneyPerDay(
            val targetMoneyPerDay: Float,
            val currentMoneyPerDay: Float
    ) {
        val ratio: Float = currentMoneyPerDay / targetMoneyPerDay
    }

    data class MoneyPerMonth(
            val moneyLeft: Float,
            val targetMoneyLeft: Float,
            val diff: Float
    )

    data class RecoveringTime(
            val recoveringTimeWithOriginalDailyTarget: Float,
            val recoveringTimeWithCurrentDailyTarget: Float
    )
}