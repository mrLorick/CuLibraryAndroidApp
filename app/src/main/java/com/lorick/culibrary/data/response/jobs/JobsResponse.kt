package com.lorick.culibrary.data.response.jobs

import com.lorick.culibrary.base.BaseResponse

data class JobsResponse(
    val `data`: ArrayList<JobsListResponse>,
) :BaseResponse()

data class JobsListResponse(
    val company_location: String?,
    val company_logo: String,
    val company_name: String,
    val hr_email: String,
    val hr_number: String,
    val id: Int,
    val job_course: String,
    val job_experience: String,
    val job_title: String,
    val job_type: String
)