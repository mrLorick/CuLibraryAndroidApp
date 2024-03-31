package com.lorick.culibrary.data.repository

import android.content.Context
import com.lorick.culibrary.retrofit.responseToResourceFromServer
import com.lorick.culibrary.genrics.Resource
import com.lorick.culibrary.retrofit.ApiService
import com.lorick.culibrary.retrofit.NetworkErrorHandlerImpl
import com.lorick.culibrary.sharedPreference.SharedPrefs
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 *
 * [PersonalDataSourceImp]
 * it stands for Implementation.
 * [PersonalDataSourceImp] is use to handle param Implementation and hit Api from here
 * and filter the api response using this function [response To ResourceFromServer]
 * [NetworkErrorHandlerImpl] is use to handle Api Catch Errors and show the Generic
 * */

class PersonalDataSourceImp @Inject constructor(
    private val apiService: ApiService,
    private val networkErrorHandlerImpl: NetworkErrorHandlerImpl,
    @ApplicationContext val context: Context,
    private val sharedPrefs: SharedPrefs
) {

    /**
     * this function is use to hit Jobs APi
     * */
    suspend fun executeJobsApi(apiType: String) = flow {
        try {
            emit(
                responseToResourceFromServer(
                    response = apiService.getJobsListApi(),
                    sharedPrefs = sharedPrefs,
                    context = context,
                    apiType = apiType
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(networkErrorHandlerImpl.getErrorMessage(e), apiType = apiType))
        }
    }
}


