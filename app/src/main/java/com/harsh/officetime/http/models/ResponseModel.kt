package com.harsh.officetime.http.models

object ResponseModel {
    data class SimpleResponse (
        val code: Int,
        val message: String?
    )
}