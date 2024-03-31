package com.lorick.culibrary.retrofit

import com.lorick.culibrary.data.response.LoginResponse
import com.lorick.culibrary.data.response.attendance.GetAttendanceResponse
import com.lorick.culibrary.data.response.jobs.JobsResponse
import com.lorick.culibrary.utils.constant.ApiConstants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    /** Login Api*/
    @FormUrlEncoded
    @POST(ApiConstants.LOGIN_ENDPOINT)
    suspend fun loginCuApi(@FieldMap map: HashMap<String, String?>): Response<LoginResponse>

   /** Get attendance Api*/
    @FormUrlEncoded
    @POST(ApiConstants.GET_ATTENDANCE_ENDPOINT)
    suspend fun getAttendanceApi(@FieldMap map: HashMap<String, String?>): Response<GetAttendanceResponse>

    /** Get attendance Api*/
    @GET(ApiConstants.GET_JOB_LIST_ENDPOINT)
    suspend fun getJobsListApi(): Response<JobsResponse>


    /** Registration Api*/
    @Multipart
    @POST(ApiConstants.LOGIN_ENDPOINT)
    suspend fun registrationApi(
        @PartMap map: HashMap<String, RequestBody?>,
        @Part profileImage: MultipartBody.Part?,
    ): Response<LoginResponse>

}
