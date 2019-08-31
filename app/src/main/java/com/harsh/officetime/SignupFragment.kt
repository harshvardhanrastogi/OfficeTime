package com.harsh.officetime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.harsh.officetime.widget.OTTextView

class SignupFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root: View = inflater.inflate(R.layout.layout_signup, container, false)
        val btnSignUp = root.findViewById<OTTextView>(R.id.btn_signup)
        btnSignUp.animate = true
        btnSignUp.setOnClickListener { }
        return root
    }
}