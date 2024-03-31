package com.lorick.culibrary.utils.constant


/**
 * [ApiConstants]
 * this class contains All Constant variable of the app
 * In Kotlin you also have the #object keyword.
 * It is used to obtain a data type with a single implementation.
 * If you are a Java user and want to understand what "single" means, you can think of the Singleton pattern:
 * it ensures you that only one instance of that class is created even if 2 threads try to create it.
 * */


object ApiConstants {
    const val LOGIN_ENDPOINT = "login"
    const val GET_ATTENDANCE_ENDPOINT = "getAttendance"
    const val GET_JOB_LIST_ENDPOINT = "get_jobs_list"
    const val VERIFICATION_OTP = "User/VerifyOTP"


    /**
     * APi Params
     * ************************************************
     * */
    enum class ApiParams(val value: String) {
        UID("user_id"),
        PASSWORD("password"),
        SESSION_ID("session_id"),
    }
}


