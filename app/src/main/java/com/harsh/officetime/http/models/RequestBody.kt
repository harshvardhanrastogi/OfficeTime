package com.harsh.officetime.http.models

import com.google.gson.annotations.SerializedName

object RequestBody {
    data class SignUpBody(
        @SerializedName("user_name") val userName: String,
        @SerializedName("user_email") val userEmail: String,
        @SerializedName("user_password") val userPassword: String
    )
}