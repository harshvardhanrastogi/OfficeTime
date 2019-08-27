package com.harsh.attandance.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.harsh.attandance.R
import com.harsh.attandance.font.FontProvider
import com.harsh.attandance.util.CalendarUtil
import kotlin.math.max

const val TAG_WEEK_PRE = "WEEK_"
const val TAG = "AttendanceCalender"
const val LAST_MONTH = "dec"
const val FIRST_MONTH = "jan"

class AttendanceCalender : ViewGroup, View.OnClickListener {

    private var fontProvider: FontProvider
    private var childLayoutRect: Rect = Rect()
    private lateinit var monthTitleView: AppCompatTextView
    private lateinit var yearTitleView: AppCompatTextView
    private var margin8dp: Int = 0
    private var month = 0
    private var year = 0
    private val inflater: LayoutInflater
    var dateSelectedListener: OnDateSelectedListener? = null
    private val dateClickListener = OnDateClickListener()

    constructor (context: Context?) : this(context, null)
    constructor(context: Context?, attributeSet: AttributeSet?) : this(context, attributeSet, -1)
    constructor(context: Context?, attributeSet: AttributeSet?, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        inflater = LayoutInflater.from(context)
        margin8dp = resources.getDimension(R.dimen.margin_8dp).toInt()
        fontProvider = FontProvider(context!!)
        month = CalendarUtil.getCurrentMonthInt()
        year = CalendarUtil.getCurrentYear()
        addElements(context)
        for (i in 0..11) {
            Log.d(TAG, "$i: days:${CalendarUtil.getNumberOfDays(2019, i)}")
        }
    }

    private fun addElements(context: Context) {
        monthTitleView = AppCompatTextView(context)
        monthTitleView.typeface = fontProvider.getRobotoReverseItalic()
        monthTitleView.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            context.resources.getDimension(R.dimen.month_title_size)
        )
        monthTitleView.setTextColor(ContextCompat.getColor(context, R.color.colorMonthTitle))
        monthTitleView.tag = "monthTitleView"
        monthTitleView.text = CalendarUtil.getCurrentMonth()
        addView(monthTitleView)

        val monthNavLeft = ImageView(context)
        monthNavLeft.setImageResource(R.drawable.ic_round_arrow_left_24px)
        monthNavLeft.tag = "monthNavLeft"
        monthNavLeft.setOnClickListener(this)
        addView(monthNavLeft)

        val monthNavRight = ImageView(context)
        monthNavRight.setImageResource(R.drawable.ic_round_arrow_right_24px)
        monthNavRight.tag = "monthNavRight"
        monthNavRight.setOnClickListener(this)
        addView(monthNavRight)

        yearTitleView = AppCompatTextView(context)
        yearTitleView.typeface = fontProvider.getRobotoReverseItalic()
        yearTitleView.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimension(R.dimen.year_title_size)
        )
        yearTitleView.text = year.toString()
        yearTitleView.tag = "yearTitle"
        yearTitleView.setTextColor(ContextCompat.getColor(context, R.color.colorYearTitle))
        addView(yearTitleView)

        addWeekDays(context)

        addDates()

    }

    private fun addDates() {
        val dayInMonth = CalendarUtil.daysInMonth(month, year)
        for (i in 1..dayInMonth) {
            addDateView(i)
        }
    }


    inner class OnDateClickListener : OnClickListener {
        override fun onClick(p0: View?) {
            dateSelectedListener?.onDateSelected((p0 as TextView).text.toString().toInt(), month, year)
        }
    }


    private fun addDateView(i: Int) {
        val dateTextView: TextView =
            inflater.inflate(R.layout.layout_date_view, this, false) as TextView
        dateTextView.tag = "date"
        dateTextView.text = i.toString()
        dateTextView.typeface = fontProvider.getDroidSerifRegular()
        dateTextView.setOnClickListener(dateClickListener)
        if (CalendarUtil.isTodaysDate(i, month, year)) {
            dateTextView.isSelected = true
            dateTextView.setTextColor(Color.WHITE)
        }

        if (CalendarUtil.isWeekend(i, CalendarUtil.getFirstDayOfWeek(month, year))) {
            dateTextView.setBackgroundResource(R.drawable.drawable_day_weekend)
            dateTextView.setTextColor(Color.WHITE)
        }
        addView(dateTextView)
    }

    private fun addWeekDays(context: Context) {
        for (i in 0..6) {
            val monTextView = AppCompatTextView(context)
            val day = resources.getStringArray(R.array.week_days)[i]
            monTextView.text = day
            monTextView.typeface = fontProvider.getDroidSerifRegular()
            monTextView.setTextColor(ContextCompat.getColor(context, R.color.colorWeekDay))
            monTextView.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.week_day_size)
            )
            monTextView.tag = TAG_WEEK_PRE + day
            addView(monTextView)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width: Int
        var height = 0
        var dateHeight = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            child.tag?.let {

                if (TextUtils.equals(child.tag.toString(), "monthTitleView")
                    || TextUtils.equals(child.tag.toString(), "yearTitle")
                ) {
                    height += child.measuredHeight
                } else if (TextUtils.equals(
                        child.tag.toString(),
                        TAG_WEEK_PRE + resources.getStringArray(R.array.week_days)[0]
                    )
                ) {
                    height += child.measuredHeight + margin8dp
                } else if (TextUtils.equals(child.tag.toString(), "date")) {
                    dateHeight = max(dateHeight, child.measuredHeight)
                }
            }

        }
        val numOfRows = if (CalendarUtil.getFirstDayOfWeek(month, year) == 7) 6 else 5
        height += paddingTop + paddingBottom + numOfRows * paddingTop + dateHeight * numOfRows
        width = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(width, height)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {

            val child = getChildAt(i)

            child.tag?.let {
                if (TextUtils.equals(child.tag.toString(), "monthTitleView")) {
                    val left = (width - child.measuredWidth) / 2
                    val top = paddingTop
                    val right = child.measuredWidth
                    val bottom = child.measuredHeight
                    childLayoutRect = initChildBounds(left, top, right, bottom)
                }

                if (TextUtils.equals(child.tag.toString(), "monthNavLeft")) {
                    val left =
                        monthTitleView.left - child.measuredWidth - resources.getDimension(R.dimen.nav_arrow_margin).toInt()
                    val top =
                        monthTitleView.top + (monthTitleView.measuredHeight - child.measuredHeight) / 2
                    val right = child.measuredWidth
                    val bottom = child.measuredHeight
                    childLayoutRect = initChildBounds(left, top, right, bottom)
                }

                if (TextUtils.equals(child.tag.toString(), "monthNavRight")) {
                    val left =
                        monthTitleView.right + resources.getDimension(R.dimen.nav_arrow_margin).toInt()
                    val top =
                        monthTitleView.top + (monthTitleView.measuredHeight - child.measuredHeight) / 2
                    val right = child.measuredWidth
                    val bottom = child.measuredHeight
                    childLayoutRect = initChildBounds(left, top, right, bottom)
                }

                if (TextUtils.equals(child.tag.toString(), "yearTitle")) {
                    val left =
                        monthTitleView.left + (monthTitleView.measuredWidth - child.measuredWidth) / 2
                    val top = monthTitleView.bottom
                    val right = child.measuredWidth
                    val bottom = child.measuredHeight
                    childLayoutRect = initChildBounds(left, top, right, bottom)
                }

                if (child.tag.toString().contains(TAG_WEEK_PRE)) {
                    val yearTitleView = findViewWithTag<View>("yearTitle")
                    val left = getWeekDayLeft((child as AppCompatTextView).text.toString()) +
                            (((width - paddingLeft - paddingRight) / 7) - child.measuredWidth) / 2
                    val top = yearTitleView.bottom + margin8dp
                    val right = child.measuredWidth
                    val bottom = child.measuredHeight
                    childLayoutRect = initChildBounds(left, top, right, bottom)
                }
                if (TextUtils.equals(child.tag.toString(), "date")) {
                    val firstDayOfWeek = CalendarUtil.getFirstDayOfWeek(month, year)
                    val date = (child as TextView).text.toString().toInt() + (firstDayOfWeek - 1)
                    val dayTextView =
                        findViewWithTag<View>(TAG_WEEK_PRE + "Mon")
                    val totalWidth = (width - paddingLeft - paddingRight) / 7
                    val left =
                        (paddingLeft + ((date - 1) % 7) * totalWidth) + ((totalWidth - child.measuredWidth)) / 2
                    val top =
                        paddingTop + dayTextView.bottom + (date - 1) / 7 * child.measuredHeight + (date - 1) / 7 * paddingTop
                    val right = child.measuredWidth
                    val bottom = child.measuredHeight
                    childLayoutRect = initChildBounds(left, top, right, bottom)
                }

            }


            child.layout(
                childLayoutRect.left,
                childLayoutRect.top,
                childLayoutRect.right,
                childLayoutRect.bottom
            )
        }
    }

    private fun getWeekDayLeft(day: String): Int {
        val weekDays = resources.getStringArray(R.array.week_days)
        return paddingLeft + weekDays.indexOf(day) * (width - paddingLeft - paddingRight) / 7
    }

    private fun initChildBounds(left: Int, top: Int, right: Int, bottom: Int): Rect {
        val rect = Rect()
        rect.left = left
        rect.top = top
        rect.right = rect.left + right
        rect.bottom = rect.top + bottom
        return rect
    }


    override fun onClick(v: View?) {
        v?.tag?.let {
            val tag = v.tag.toString()
            when {
                TextUtils.equals(tag, "monthNavLeft") -> {
                    val month = CalendarUtil.getPreviousMonth(monthTitleView.text.toString())
                    monthTitleView.text = month
                    this.month = CalendarUtil.getIntMonth(month!!)
                    if (TextUtils.equals(monthTitleView.text.toString().toLowerCase(), LAST_MONTH)) {
                        val preYear = Integer.parseInt(yearTitleView.text.toString()) - 1
                        yearTitleView.text = preYear.toString()
                        year = preYear
                    }
                    removeAllDateViews()
                    addDates()
                }

                TextUtils.equals(tag, "monthNavRight") -> {
                    val month = CalendarUtil.getNextMonth(monthTitleView.text.toString())
                    monthTitleView.text = month
                    this.month = CalendarUtil.getIntMonth(month!!)
                    if (TextUtils.equals(monthTitleView.text.toString().toLowerCase(), FIRST_MONTH)) {
                        val nextYear = Integer.parseInt(yearTitleView.text.toString()) + 1
                        yearTitleView.text = nextYear.toString()
                        year = nextYear
                    }
                    removeAllDateViews()
                    addDates()
                }

            }
        }
    }

    private fun removeAllDateViews() {
        for (i in 1..childCount) {
            val tv = findDateViewWithDate(i)
            tv?.let {
                removeView(tv)
            }
        }
    }

    private fun getStringRepOfDay(day: Int) = resources.getStringArray(R.array.week_days)[day - 1]

    private fun findDateViewWithDate(date: Int): TextView? {
        for (i in 0 until childCount) {
            if (getChildAt(i) is TextView
                && TextUtils.equals(date.toString(), (getChildAt(i) as TextView).text)
            ) {
                return getChildAt(i) as TextView
            }
        }
        return null
    }

    interface OnDateSelectedListener {
        fun onDateSelected(day: Int, month: Int, year: Int)
    }


}