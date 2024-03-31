package com.lorick.culibrary.viewModel

import com.lorick.culibrary.base.BaseViewModel
import com.lorick.culibrary.data.repository.PersonalDataSourceImp
import com.lorick.culibrary.data.response.jobs.JobsResponse
import com.lorick.culibrary.genrics.Resource
import com.lorick.culibrary.sharedPreference.SharedPrefs
import com.lorick.culibrary.utils.constant.ApiConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor(private val personalDataSourceImp: PersonalDataSourceImp) : BaseViewModel() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    private val jobsResponse: MutableSharedFlow<Resource<JobsResponse>> = MutableSharedFlow()
    val jobsResponseSharedFlow = jobsResponse.asSharedFlow()

    /**
     * this function is use to hit Login Api
     * */
    suspend fun hitJobsApi() {
        showLoading.postValue(true)

        personalDataSourceImp.executeJobsApi(apiType = ApiConstants.GET_JOB_LIST_ENDPOINT).catch { e ->
            jobsResponse.emit(Resource.Error(e.message.toString()))
            showLoading.postValue(false)
        }.collect { isResponse ->
            showLoading.postValue(false)
            jobsResponse.emit(isResponse)
        }
    }
}









