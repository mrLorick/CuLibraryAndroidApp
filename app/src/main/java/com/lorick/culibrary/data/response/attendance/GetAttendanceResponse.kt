package com.lorick.culibrary.data.response.attendance

import com.lorick.culibrary.base.BaseResponse

data class GetAttendanceResponse(
    val `data`: ArrayList<GetAttendanceListResponse>,
    val sessionID: String,
) :BaseResponse()


data class GetAttendanceListResponse(
    val Code: String,
    val DutyLeave: Int,
    val DutyLeave_N_P: Int,
    val DutyLeave_Others: Int,
    val EligibilityAttended: String,
    val EligibilityDelivered: String,
    val EligibilityPercentage: String,
    val EncryptCode: String,
    val Lec_Attd: Int,
    val Lec_Delv: Int,
    val Lec_Perc: Double,
    val MedicalLeave: Int,
    val Prac_Attd: Int,
    val Prac_Delv: Int,
    val Prac_Perc: Int,
    val Semester: Int,
    val StudentId: Int,
    val Title: String,
    val TotalPercentage: String,
    val Total_Attd: String,
    val Total_Delv: Int,
    val Total_Perc: Double,
    val Trl_Attd: Int,
    val Trl_Delv: Int,
    val Trl_Perc: Double,
    val UId: String,
    val colorcode: String,
    val name: String
)