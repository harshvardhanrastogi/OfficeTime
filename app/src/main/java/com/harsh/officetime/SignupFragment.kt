package com.harsh.officetime

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.harsh.officetime.http.RetrofitUtil
import com.harsh.officetime.http.models.RequestBody
import com.harsh.officetime.http.models.ResponseModel
import com.harsh.officetime.widget.OTTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupFragment : Fragment() {

    private lateinit var tvUserName: TextView
    private lateinit var tvUserEmail: TextView
    private lateinit var tvUserPassword: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.layout_signup, container, false)
        tvUserName = root.findViewById(R.id.username)
        tvUserEmail = root.findViewById(R.id.email)
        tvUserPassword = root.findViewById(R.id.password)
        val btnSignUp = root.findViewById<OTTextView>(R.id.btn_signup)
        btnSignUp.animate = true
        btnSignUp.setOnClickListener {
            signup()
        }
        return root
    }

    private fun signup() {
        val reqBody = RequestBody.SignUpBody(
            tvUserName.text.toString(), tvUserEmail.text.toString(),
            tvUserPassword.text.toString()
        )
        val call = RetrofitUtil.webApiService.signup(reqBody)
        call.enqueue(object:Callback<ResponseModel.SimpleResponse> {
            override fun onFailure(call: Call<ResponseModel.SimpleResponse>, t: Throwable) {
                Log.d("Sign", "Failure")
            }

            override fun onResponse(
                call: Call<ResponseModel.SimpleResponse>,
                response: Response<ResponseModel.SimpleResponse>
            ) {
                Log.d("Sign", "Success")
            }

        })
    }
}