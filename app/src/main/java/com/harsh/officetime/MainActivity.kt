package com.harsh.officetime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

/*    private lateinit var titleMonth: TitleTextView
    private lateinit var titleYear: TitleTextView*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /* titleMonth = findViewById(R.id.title_month)
         titleMonth.text = getCurrentMonth()

         titleYear = findViewById(R.id.title_year)
         titleYear.text = getCurrentYear().toString()

         val leftArrow: AppCompatImageView = findViewById(R.id.month_nav_left)
         val rightArrow: AppCompatImageView = findViewById(R.id.month_nav_right)

         val dateNavigator = DateNavigator(
             WeakReference(titleMonth), WeakReference(titleYear),
             WeakReference(leftArrow), WeakReference(rightArrow)
         )
         leftArrow.setOnClickListener(dateNavigator)
         rightArrow.setOnClickListener(dateNavigator)*/
    }

   /* class DateNavigator(
        monthTextViewRef: WeakReference<TitleTextView>,
        yearTextViewRef: WeakReference<TitleTextView>,
        leftArrowRef: WeakReference<AppCompatImageView>,
        rightArrowRef: WeakReference<AppCompatImageView>
    ) :
        View.OnClickListener {

        private var monthTextViewRef: WeakReference<TitleTextView>
        private var yearTextViewRef: WeakReference<TitleTextView>

        init {
            leftArrowRef.get()?.setOnClickListener(this)
            rightArrowRef.get()?.setOnClickListener(this)
            this.monthTextViewRef = monthTextViewRef
            this.yearTextViewRef = yearTextViewRef
        }

        override fun onClick(v: View?) {
            if (v?.id == R.id.month_nav_left) {
                monthTextViewRef.get()?.text = getPreviousMonth(monthTextViewRef.get()?.text.toString())
                if (TextUtils.equals(monthTextViewRef.get()?.text.toString().toLowerCase(), "dec")) {
                    yearTextViewRef.get()?.text =
                        (Integer.parseInt(yearTextViewRef.get()?.text.toString()) - 1).toString()
                }
            } else {
                monthTextViewRef.get()?.text = getNextMonth(monthTextViewRef.get()?.text.toString())
                if (TextUtils.equals(monthTextViewRef.get()?.text.toString().toLowerCase(), "jan")) {
                    yearTextViewRef.get()?.text =
                        (Integer.parseInt(yearTextViewRef.get()?.text.toString()) + 1).toString()
                }
            }
        }
    }*/
}


