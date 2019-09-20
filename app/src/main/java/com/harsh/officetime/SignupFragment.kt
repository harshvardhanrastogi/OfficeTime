package com.harsh.officetime

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.harsh.officetime.anim.AnimationCreator
import com.harsh.officetime.http.RetrofitUtil
import com.harsh.officetime.http.models.RequestBody
import com.harsh.officetime.http.models.ResponseModel
import com.harsh.officetime.widget.OTTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference

class SignupFragment : Fragment() {

    private lateinit var tvUserName: EditText
    private lateinit var tvUserEmail: EditText
    private lateinit var tvUserPassword: EditText
    private lateinit var tvConfirmPassword: EditText
    private lateinit var errUserName: ImageView
    private lateinit var errPassword: ImageView
    private lateinit var errConfirmPassword: ImageView
    private lateinit var errEmail: ImageView
    private lateinit var imgErr: ImageView
    private lateinit var tvErr: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root: View = inflater.inflate(R.layout.layout_signup, container, false)
        tvUserName = root.findViewById(R.id.username)
        tvUserEmail = root.findViewById(R.id.email)
        tvUserPassword = root.findViewById(R.id.password)
        tvConfirmPassword = root.findViewById(R.id.confirm_password)
        errUserName = root.findViewById(R.id.img_err_username)
        errEmail = root.findViewById(R.id.img_err_email)
        errPassword = root.findViewById(R.id.img_err_password)
        errConfirmPassword = root.findViewById(R.id.img_err_confirm_password)
        imgErr = root.findViewById(R.id.err_common_image)
        tvErr = root.findViewById(R.id.err_common_text)
        val btnSignUp = root.findViewById<OTTextView>(R.id.btn_signup)
        btnSignUp.animate = true
        btnSignUp.setOnClickListener {
            signup()
        }

        return root
    }

    private fun signup() {

        removeCommonError()
        if (!setAndReturnError()) return

        val reqBody = RequestBody.SignUpBody(
            tvUserName.text.toString(), tvUserEmail.text.toString(),
            tvUserPassword.text.toString()
        )
        val call = RetrofitUtil.webApiService.signup(reqBody)
        call.enqueue(object : Callback<ResponseModel.SimpleResponse> {
            override fun onFailure(call: Call<ResponseModel.SimpleResponse>, t: Throwable) {
                if (isVisible) {
                    setCommonError(t.localizedMessage)
                }
            }

            override fun onResponse(
                call: Call<ResponseModel.SimpleResponse>,
                response: Response<ResponseModel.SimpleResponse>
            ) {
                Log.d("Sign", response.body().toString())
                if (response.isSuccessful && response.body() != null && isVisible) {
                    when (response.body()!!.code) {
                        0 -> {
                            setCommonError(response.body()!!.message!!)
                        }

                        1 -> {
                            removeCommonError()
                        }
                    }
                }
            }

        })
    }

    private fun setAndReturnError(): Boolean {

        val isUserNameValid = tvUserName.text.isNotBlank() and (tvUserName.text.length > 1)

        val isUserEmailValid = tvUserEmail.text.isNotBlank() and
                android.util.Patterns.EMAIL_ADDRESS.matcher(tvUserEmail.text).matches()

        val isPasswordValid = tvUserPassword.text.isNotBlank() and (tvUserPassword.text.length >= 6)

        val isConfirmPasswordValid = tvConfirmPassword.text.isNotBlank() and TextUtils.equals(
            tvUserPassword.text,
            tvConfirmPassword.text
        )
        setBackgroundColor(
            !isUserNameValid,
            tvUserName,
            errUserName,
            if (isUserNameValid) R.drawable.ic_user else R.drawable.ic_user_err
        )
        setBackgroundColor(
            !isUserEmailValid,
            tvUserEmail,
            errEmail,
            if (isUserEmailValid) R.drawable.ic_email else R.drawable.ic_email_err
        )
        setBackgroundColor(
            !isPasswordValid, tvUserPassword, errPassword,
            if (isPasswordValid) R.drawable.ic_password else R.drawable.ic_password_err
        )
        setBackgroundColor(
            !isConfirmPasswordValid,
            tvConfirmPassword,
            errConfirmPassword,
            if (isConfirmPasswordValid) R.drawable.ic_password else R.drawable.ic_password_err
        )
        return isUserNameValid and isUserEmailValid and isPasswordValid and isConfirmPasswordValid
    }

    private fun setBackgroundColor(
        error: Boolean,
        edittext: EditText,
        errImg: ImageView,
        leftDrawable: Int
    ) {
        if (error) {
            (edittext.background as GradientDrawable).setStroke(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 1f, resources.displayMetrics
                ).toInt(), ContextCompat.getColor(context!!, R.color.colorError)
            )
            errImg.visibility = View.VISIBLE
            edittext.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, 0, 0, 0)
            AnimationCreator.createScaleAnim(WeakReference(errImg), 0.5f).start()
        } else {
            (edittext.background as GradientDrawable).setStroke(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 1f, resources.displayMetrics
                ).toInt(), Color.BLACK
            )
            AnimationCreator.createScaleDownAnimation(WeakReference(errImg), 0f).start()
            edittext.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, 0, 0, 0)
        }
    }

    fun setCommonError(text: String) {
        imgErr.visibility = View.VISIBLE
        tvErr.visibility = View.VISIBLE
        AnimationCreator.createScaleAnim(WeakReference(imgErr), 0.5f).start()
        AnimationCreator.createScaleAnim(WeakReference(tvErr), 0.5f).start()
        tvErr.text = text
    }

    fun removeCommonError() {
        imgErr.visibility = View.INVISIBLE
        tvErr.visibility = View.INVISIBLE
        tvErr.text = ""
    }
}