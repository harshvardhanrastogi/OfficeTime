package com.harsh.officetime

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.harsh.attandance.widget.AttendanceCalender

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val attendanceCalender = findViewById<AttendanceCalender>(R.id.attendance_cal)
        attendanceCalender.dateSelectedListener =
            object : AttendanceCalender.OnDateSelectedListener {
                override fun onDateSelected(day: Int, month: Int, year: Int) {
                    Log.d("MainActivity", "$day/$month/$year")
                }

            }
    }
}


