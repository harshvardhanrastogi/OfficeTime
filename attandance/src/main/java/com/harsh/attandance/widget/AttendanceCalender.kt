package com.harsh.attandance.widget

import android.content.Context
import android.graphics.Rect
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.harsh.attandance.R
import com.harsh.attandance.font.FontProvider
import com.harsh.attandance.util.CalenderUtil
import kotlin.math.abs

class AttendanceCalender : ViewGroup, View.OnClickListener {

    private var fontProvider: FontProvider
    private var childLayoutRect: Rect = Rect()
    private val TAG = "AttendanceCalender"
    private val TAG_WEEK_PRE = "WEEK_"
    private lateinit var monthTitleView: AppCompatTextView
    private lateinit var yearTitleView: AppCompatTextView
    private var margin8dp: Int = 0

    constructor (context: Context?) : this(context, null)
    constructor(context: Context?, attributeSet: AttributeSet?) : this(context, attributeSet, -1)
    constructor(context: Context?, attributeSet: AttributeSet?, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        margin8dp = resources.getDimension(R.dimen.margin_8dp).toInt()
        fontProvider = FontProvider(context!!)
        addElements(context)
        for (i in 0..11) {
            Log.d(TAG, "$i: days:${CalenderUtil.getNumberOfDays(2019, i)}")
        }
    }

    private fun addElements(context: Context) {
        monthTitleView = AppCompatTextView(context)
        monthTitleView.typeface = fontProvider.getRobotoReverseItalic()
        monthTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.month_title_size))
        monthTitleView.setTextColor(ContextCompat.getColor(context, R.color.colorMonthTitle))
        monthTitleView.tag = "monthTitleView"
        monthTitleView.text = CalenderUtil.getCurrentMonth()
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
        yearTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.year_title_size))
        yearTitleView.text = CalenderUtil.getCurrentYear().toString()
        yearTitleView.tag = "yearTitle"
        yearTitleView.setTextColor(ContextCompat.getColor(context, R.color.colorYearTitle))
        addView(yearTitleView)

        addWeekDays(context)
    }

    private fun addWeekDays(context: Context) {
        for (i in 0..6) {
            val monTextView = AppCompatTextView(context)
            val day = resources.getStringArray(R.array.week_days)[i]
            monTextView.text = day
            monTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.week_day_size))
            monTextView.tag = TAG_WEEK_PRE + day
            addView(monTextView)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width: Int
        var height = 0

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
                }
            }
        }
        height += paddingTop + paddingBottom
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
                    val top = monthTitleView.top + (monthTitleView.measuredHeight - child.measuredHeight) / 2
                    val right = child.measuredWidth
                    val bottom = child.measuredHeight
                    childLayoutRect = initChildBounds(left, top, right, bottom)
                }

                if (TextUtils.equals(child.tag.toString(), "monthNavRight")) {
                    val left =
                        monthTitleView.right + resources.getDimension(R.dimen.nav_arrow_margin).toInt()
                    val top = monthTitleView.top + (monthTitleView.measuredHeight - child.measuredHeight) / 2
                    val right = child.measuredWidth
                    val bottom = child.measuredHeight
                    childLayoutRect = initChildBounds(left, top, right, bottom)
                }

                if (TextUtils.equals(child.tag.toString(), "yearTitle")) {
                    val left = monthTitleView.left + (monthTitleView.measuredWidth - child.measuredWidth) / 2
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

            }


            child.layout(childLayoutRect.left, childLayoutRect.top, childLayoutRect.right, childLayoutRect.bottom)
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
                    val month = CalenderUtil.getPreviousMonth(monthTitleView.text.toString())
                    monthTitleView.text = month
                    if (TextUtils.equals(monthTitleView.text.toString().toLowerCase(), "dec")) {
                        yearTitleView.text = (Integer.parseInt(yearTitleView.text.toString()) - 1).toString()
                    }
                }

                TextUtils.equals(tag, "monthNavRight") -> {
                    val month = CalenderUtil.getNextMonth(monthTitleView.text.toString())
                    monthTitleView.text = month
                    if (TextUtils.equals(monthTitleView.text.toString().toLowerCase(), "jan")) {
                        yearTitleView.text =
                            (Integer.parseInt(yearTitleView.text.toString()) + 1).toString()
                    }
                }

            }
        }
    }

    interface CalenderListener {
        fun onMonthChanged(month: String)
    }
}