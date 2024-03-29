package com.harsh.attandance.util

import android.text.TextUtils
import java.util.*
import java.text.DateFormatSymbols

object CalendarUtil {

    private val cal = Calendar.getInstance()
    private val months =
        arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    @JvmStatic
    fun getCurrentMonth(): String =
        cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())

    @JvmStatic
    fun getCurrentMonthInt(): Int = cal.get(Calendar.MONTH) + 1

    @JvmStatic
    fun getNextMonth(currentMonth: String): String? {
        val monthShort = arrayOfNulls<String>(12)
        DateFormatSymbols().months.forEachIndexed { index, month ->
            monthShort[index] = month.substring(0, 3)
        }
        if ((monthShort.indexOf(currentMonth) + 1) == monthShort.size) {
            return monthShort[0]
        }
        return monthShort[(monthShort.indexOf(currentMonth) + 1)]
    }

    @JvmStatic
    fun getPreviousMonth(currentMonth: String): String? {
        val monthShort = arrayOfNulls<String>(12)
        DateFormatSymbols().months.forEachIndexed { index, month ->
            monthShort[index] = month.substring(0, 3)
        }
        if ((monthShort.indexOf(currentMonth) - 1) < 0) {
            return monthShort[11]
        }
        return monthShort[(monthShort.indexOf(currentMonth) - 1)]
    }

    @JvmStatic
    fun getCurrentYear(): Int = cal.get(Calendar.YEAR)

    @JvmStatic
    fun getNumberOfDays(month: Int, year: Int): Int {
        val calender = Calendar.getInstance()
        calender.set(Calendar.YEAR, year)
        calender.set(Calendar.MONTH, month)
        return calender.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    private fun isLeapYear(year: Int) = ((year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)))

    @JvmStatic
    fun daysInMonth(month: Int, year: Int): Int {
        when {
            month == 2 -> {
                if (isLeapYear(year)) return 29
                return 28
            }
            (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) -> return 31
            else -> return 30
        }
    }

    @JvmStatic
    fun getFirstDayOfWeek(month: Int, year: Int): Int {
        var dow = 6

        for (i in 1583 until year) {
            dow += if (isLeapYear(i)) 2 else 1
        }

        for (i in 1 until month) {
            dow += daysInMonth(i, year)
        }

        return if (dow % 7 == 0) 7 else dow % 7
    }

    @JvmStatic
    fun getIntMonth(month: String) = months.indexOf(month) + 1

    @JvmStatic
    fun isTodaysDate(day: Int, month: Int, year: Int): Boolean {
        val tDay = cal.get(Calendar.DATE)
        if (day != tDay) {
            return false
        }

        val tMon = getCurrentMonthInt()
        if (month != tMon) {
            return false
        }

        val tYear = getCurrentYear()
        if (year != tYear) {
            return false
        }
        return true
    }

    @JvmStatic
    fun isWeekend(day: Int, firstDay: Int): Boolean {
        if ((day + firstDay - 1) % 7 == 0 || (day + firstDay - 1) % 7 == 6) return true
        return false
    }

}