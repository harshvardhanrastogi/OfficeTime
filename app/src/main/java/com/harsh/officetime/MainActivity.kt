package com.harsh.officetime

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.harsh.officetime.anim.AnimationCreator
import com.harsh.officetime.widget.OTTextView
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    private var lastPageIndex: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_signup_layout)
        val tabLineLogin = findViewById<View>(R.id.tab_line_login)
        val tabLineSignup = findViewById<View>(R.id.tab_line_signup)
        val viewpager = findViewById<ViewPager>(R.id.view_pager)
        viewpager.adapter = LoginSignupViewPagerAdapter(supportFragmentManager)
        val tabLogin = findViewById<OTTextView>(R.id.tab_login)
        tabLogin.setOnClickListener {
            viewpager.currentItem = 0
        }
        val tabSignup = findViewById<OTTextView>(R.id.tab_signup)
        tabSignup.setOnClickListener {
            viewpager.currentItem = 1
        }
        viewpager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (lastPageIndex != position) {
                    when (position) {
                        0 -> {
                            viewpager.post { onTabClicked(tabLineLogin, tabLineSignup) }
                        }

                        1 -> {
                            viewpager.post { onTabClicked(tabLineSignup, tabLineLogin) }
                        }
                    }
                    lastPageIndex = position
                }
            }
        })
    }

    fun onTabClicked(tabLineSelected: View?, tabLineDeselected: View?) {
        tabLineSelected?.visibility = View.VISIBLE
        tabLineSelected?.let { AnimationCreator.createLineStretchAnimation(WeakReference(tabLineSelected)).start() }
        tabLineDeselected?.visibility = View.INVISIBLE
    }

}

class LoginSignupViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> LoginFragment()
            1 -> SignupFragment()
            else -> LoginFragment()
        }
    }

    override fun getCount(): Int = 2

}


