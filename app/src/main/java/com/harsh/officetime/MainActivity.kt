package com.harsh.officetime

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.harsh.officetime.util.CalenderUtil.getCurrentMonth
import com.harsh.officetime.util.CalenderUtil.getCurrentYear
import com.harsh.officetime.util.CalenderUtil.getNextMonth
import com.harsh.officetime.util.CalenderUtil.getPreviousMonth
import com.harsh.officetime.widget.TitleTextView

class MainActivity : AppCompatActivity() {

    private lateinit var titleMonth: TitleTextView
    private lateinit var titleYear: TitleTextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        titleMonth = findViewById(R.id.title_month)
        titleMonth.text = getCurrentMonth()

        titleYear = findViewById(R.id.title_year)
        titleYear.text = getCurrentYear().toString()

        val leftArrow: AppCompatImageView = findViewById(R.id.month_nav_left)
        val rightArrow: AppCompatImageView = findViewById(R.id.month_nav_right)

        val dateNavigator = DateNavigator(titleMonth, titleYear, leftArrow, rightArrow)
        leftArrow.setOnClickListener(dateNavigator)
        rightArrow.setOnClickListener(dateNavigator)
    }

    class DateNavigator(
        monthTextView: TitleTextView,
        yearTextView: TitleTextView,
        leftArrow: AppCompatImageView,
        rightArrow: AppCompatImageView
    ) :
        View.OnClickListener {

        private var monthTextView: TitleTextView
        private var yearTextView: TitleTextView

        init {
            leftArrow.setOnClickListener(this)
            rightArrow.setOnClickListener(this)
            this.monthTextView = monthTextView
            this.yearTextView = yearTextView
        }

        override fun onClick(v: View?) {
            if (v?.id == R.id.month_nav_left) {
                monthTextView.text = getPreviousMonth(monthTextView.text.toString())
                if (TextUtils.equals(monthTextView.text.toString().toLowerCase(), "dec")) {
                    yearTextView.text = (Integer.parseInt(yearTextView.text.toString()) - 1).toString()
                }
            } else {
                monthTextView.text = getNextMonth(monthTextView.text.toString())
                if (TextUtils.equals(monthTextView.text.toString().toLowerCase(), "jan")) {
                    yearTextView.text = (Integer.parseInt(yearTextView.text.toString()) + 1).toString()
                }
            }
        }
    }
}


