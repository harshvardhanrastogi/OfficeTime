package com.harsh.officetime.util

import java.util.*
import java.text.DateFormatSymbols

object CalenderUtil {

    private val cal = Calendar.getInstance()

    @JvmStatic
    fun getCurrentMonth(): String = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())

    @JvmStatic
    fun getNextMonth(currentMonth: String): String? {
        val monthShort = arrayOfNulls<String>(12)
        DateFormatSymbols().months.forEachIndexed { index, month -> monthShort[index] = month.substring(0, 3) }
        if ((monthShort.indexOf(currentMonth) + 1) == monthShort.size) {
            return monthShort[0]
        }
        return monthShort[(monthShort.indexOf(currentMonth) + 1)]
    }

    @JvmStatic
    fun getPreviousMonth(currentMonth: String): String? {
        val monthShort = arrayOfNulls<String>(12)
        DateFormatSymbols().months.forEachIndexed { index, month -> monthShort[index] = month.substring(0, 3) }
        if ((monthShort.indexOf(currentMonth) - 1) < 0) {
            return monthShort[11]
        }
        return monthShort[(monthShort.indexOf(currentMonth) - 1)]
    }

    @JvmStatic
    fun getCurrentYear(): Int = cal.get(Calendar.YEAR)
}