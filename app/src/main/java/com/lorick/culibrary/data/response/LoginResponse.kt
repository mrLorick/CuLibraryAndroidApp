package com.lorick.culibrary.data.response

import com.lorick.culibrary.base.BaseResponse

data class LoginResponse(
    val session_id: String,
) :BaseResponse()