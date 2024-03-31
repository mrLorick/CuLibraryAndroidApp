package com.lorick.culibrary.viewModel

import androidx.databinding.ObservableField
import com.lorick.culibrary.base.BaseViewModel
import com.lorick.culibrary.data.repository.AuthDataSourceImp
import com.lorick.culibrary.data.response.LoginResponse
import com.lorick.culibrary.data.response.attendance.GetAttendanceResponse
import com.lorick.culibrary.genrics.Resource
import com.lorick.culibrary.sharedPreference.SharedPrefs
import com.lorick.culibrary.utils.constant.ApiConstants
import com.lorick.culibrary.utils.constant.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authDataSourceImp: AuthDataSourceImp) : BaseViewModel() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    var uid = ObservableField("")
    var password = ObservableField("")


    private val loginResponse: MutableSharedFlow<Resource<LoginResponse>> = MutableSharedFlow()
    val loginResponseSharedFlow = loginResponse.asSharedFlow()

    private val getAttendanceResponse: MutableSharedFlow<Resource<GetAttendanceResponse>> = MutableSharedFlow()
    val getAttendanceResponseSharedFlow = getAttendanceResponse.asSharedFlow()


    /**
     * this function is use to hit Login Api
     * */
    suspend fun hitLoginCuimsApi() {
        showLoading.postValue(true)
        val param = HashMap<String, String?>()
        param[ApiConstants.ApiParams.UID.value] = "22BCA10952"
        param[ApiConstants.ApiParams.PASSWORD.value] = "Yadav@1202"

        authDataSourceImp.executeLoginCuimsApi(param, apiType = ApiConstants.LOGIN_ENDPOINT).catch { e ->
            loginResponse.emit(Resource.Error(e.message.toString()))
            showLoading.postValue(false)
        }.collect { isResponse ->
            showLoading.postValue(false)
            loginResponse.emit(isResponse)
        }
    }

    /**
     * this function is use to hit get attendance Api
     * */
    suspend fun hitGetAttendanceApi() {
        showLoading.postValue(true)
        val param = HashMap<String, String?>()
        param[ApiConstants.ApiParams.SESSION_ID.value] = sharedPrefs.getString(AppConstants.USER_SESSION_ID)

        authDataSourceImp.executeGetAttendanceApi(param, apiType = ApiConstants.GET_ATTENDANCE_ENDPOINT).catch { e ->
            getAttendanceResponse.emit(Resource.Error(e.message.toString()))
            showLoading.postValue(false)
        }.collect { isResponse ->
            showLoading.postValue(false)
            getAttendanceResponse.emit(isResponse)
        }
    }


}









