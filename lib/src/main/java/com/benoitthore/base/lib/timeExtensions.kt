package com.benoitthore.base.lib

import java.util.*

interface TimeHelper {

    val Number.seconds: Long get() = 1000L * toLong()
    val Number.minutes: Long get() = 60 * this.seconds
    val Number.hours: Long get() = 60 * this.minutes
    val Number.days: Long get() = 24 * this.hours
    val Number.weeks: Long get() = 7 * this.days
    val Number.years: Long get() = (365.25 * this.days).toLong()

    val Number.nbOfSeconds: Float get() = toFloat() / 1.seconds
    val Number.nbOfMinutes: Float get() = toFloat() / 1.minutes
    val Number.nbOfHours: Float get() = toFloat() / 1.hours
    val Number.nbOfDays: Float get() = toFloat() / 1.days
    val Number.nbOfWeeks: Float get() = toFloat() / 1.weeks
    val Number.nbOfYears: Float get() = toFloat() / 1.years + 1970

    val Long.date: Date get() = Date(this)
    val today: Long get() = System.currentTimeMillis()

    val Long.endOfDay: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
            calendar.set(Calendar.MILLISECOND, 999)

            return calendar.timeInMillis
        }

    val Long.beginningOfDay: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            return calendar.timeInMillis
        }

    val Long.beginningOfWeek: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            return calendar.timeInMillis.beginningOfDay
        }

    val Long.endOfWeek: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
            return calendar.timeInMillis.endOfDay
        }

    val Long.beginningOfMonth: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            return calendar.timeInMillis.beginningOfDay
        }

    val Long.endOfMonth: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            return calendar.timeInMillis.endOfDay
        }


    val Long.beginningOfYear: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.set(Calendar.DAY_OF_YEAR, 1)
            return calendar.timeInMillis.beginningOfDay
        }

    val Long.endOfYear: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR))
            return calendar.timeInMillis.endOfDay
        }
}