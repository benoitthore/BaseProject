package com.benoitthore.base.money.businesslogic

class CalculateCurrentMonthlyBudgetUseCase {

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
}