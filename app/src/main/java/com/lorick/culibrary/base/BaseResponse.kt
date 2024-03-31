package com.lorick.culibrary.base

abstract class BaseResponse {
    val status: Boolean = false
    val message: String? = null

    fun isSuccess(): Boolean {
        return status
    }
}