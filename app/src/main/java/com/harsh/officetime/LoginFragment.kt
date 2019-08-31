package com.harsh.officetime

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.harsh.officetime.widget.OTTextView

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root: View = inflater.inflate(R.layout.layout_login, container, false)
        val btnLogin = root.findViewById<OTTextView>(R.id.btn_login)
        btnLogin.animate = true
        btnLogin.setOnClickListener { Log.d("LoginFragment", "Clicked!") }
        return root
    }
}