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

class AttendanceCalender : ViewGroup, View.OnClickListener {

    private var fontProvider: FontProvider
    private var childLayoutRect: Rect = Rect()
    private val TAG = "AttendanceCalender"
    private lateinit var monthTitleView: AppCompatTextView
    private lateinit var yearTitleView: AppCompatTextView

    constructor (context: Context?) : this(context, null)
    constructor(context: Context?, attributeSet: AttributeSet?) : this(context, attributeSet, -1)
    constructor(context: Context?, attributeSet: AttributeSet?, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {

        fontProvider = FontProvider(context!!)
        addElements(context)
        Log.d(TAG, "paddingTop: $paddingTop")
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
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width: Int
        var height = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            if (TextUtils.equals(child.tag.toString(), "monthTitleView")
                || TextUtils.equals(child.tag.toString(), "yearTitle")
            ) {
                height += child.measuredHeight
            }
        }
        height += paddingTop + paddingBottom
        width = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(width, height)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        for (i in 0 until childCount) {

            val child = getChildAt(i)

            if (TextUtils.equals(child.tag.toString(), "monthTitleView")) {
                childLayoutRect.left = (width - child.measuredWidth) / 2
                childLayoutRect.top = paddingTop
                childLayoutRect.right = childLayoutRect.left + child.measuredWidth
                childLayoutRect.bottom = childLayoutRect.top + child.measuredHeight
            }

            if (TextUtils.equals(child.tag.toString(), "monthNavLeft")) {
                val monthTitle: View? = findViewWithTag<View>("monthTitleView")
                monthTitle?.let {
                    childLayoutRect.left =
                        monthTitle.left - child.measuredWidth - resources.getDimension(R.dimen.nav_arrow_margin).toInt()
                    childLayoutRect.top = monthTitle.top + (monthTitle.measuredHeight - child.measuredHeight) / 2
                    childLayoutRect.right = childLayoutRect.left + child.measuredWidth
                    childLayoutRect.bottom = childLayoutRect.top + child.measuredHeight
                }
            }

            if (TextUtils.equals(child.tag.toString(), "monthNavRight")) {
                val monthTitle: View? = findViewWithTag<View>("monthTitleView")
                monthTitle?.let {
                    childLayoutRect.left =
                        monthTitle.right + resources.getDimension(R.dimen.nav_arrow_margin).toInt()
                    childLayoutRect.top = monthTitle.top + (monthTitle.measuredHeight - child.measuredHeight) / 2
                    childLayoutRect.right = childLayoutRect.left + child.measuredWidth
                    childLayoutRect.bottom = childLayoutRect.top + child.measuredHeight
                }
            }

            if (TextUtils.equals(child.tag.toString(), "yearTitle")) {
                val monthTitle: View? = findViewWithTag("monthTitleView")
                monthTitle?.let {
                    childLayoutRect.left = monthTitle.left + (monthTitle.measuredWidth - child.measuredWidth) / 2
                    childLayoutRect.top = monthTitle.bottom
                    childLayoutRect.right = childLayoutRect.left + child.measuredWidth
                    childLayoutRect.bottom = childLayoutRect.top + child.measuredHeight
                }
            }

            child.layout(childLayoutRect.left, childLayoutRect.top, childLayoutRect.right, childLayoutRect.bottom)
        }
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
}