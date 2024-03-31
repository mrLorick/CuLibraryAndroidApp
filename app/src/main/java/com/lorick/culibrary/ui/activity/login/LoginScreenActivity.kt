package com.lorick.culibrary.ui.activity.login

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.lorick.culibrary.R
import com.lorick.culibrary.base.BaseActivity
import com.lorick.culibrary.databinding.ActivityLoginScreenBinding
import com.lorick.culibrary.genrics.Resource
import com.lorick.culibrary.genrics.RunInScope
import com.lorick.culibrary.sharedPreference.SharedPrefs
import com.lorick.culibrary.ui.activity.jobs.JobsListScreenActivity
import com.lorick.culibrary.utils.MyProgressBar
import com.lorick.culibrary.utils.constant.AppConstants
import com.lorick.culibrary.utils.finishActivity
import com.lorick.culibrary.utils.launchActivity
import com.lorick.culibrary.utils.overrideColorStatusBar
import com.lorick.culibrary.utils.showErrorSnack
import com.lorick.culibrary.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginScreenActivity : BaseActivity<ActivityLoginScreenBinding>() {
    private var activity = this@LoginScreenActivity
    private val authViewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var sharedPrefs : SharedPrefs

    override fun getLayoutRes(): Int = R.layout.activity_login_screen

    override fun initView() {
        overrideColorStatusBar()
        observeDataFromViewModal()
        setOnClickListener()
    }

    /** set on click listener*/
    private fun setOnClickListener() {
        binding.apply {
            btnLogIn.setOnClickListener {
                RunInScope.ioThread {
                    authViewModel.hitLoginCuimsApi()
                }
            }
        }
    }

    override fun viewModel() {

    }

    /** Observer Response via View model*/
    private fun observeDataFromViewModal() {
        lifecycleScope.launch {
            authViewModel.loginResponseSharedFlow.collectLatest { isResponse ->
                when (isResponse) {
                    is Resource.Success -> {
                        val data = isResponse.data
                        if (data?.isSuccess() == true) {
                            sharedPrefs.save(AppConstants.USER_SESSION_ID,data.session_id)
                            sharedPrefs.saveUserLogin(true)
                            launchActivity<JobsListScreenActivity> {  }
                            finishActivity()
                        } else {
                            showErrorSnack(activity, data?.message ?: "")
                        }
                    }

                    is Resource.Error -> {
                        isResponse.message?.let { msg ->
                            showErrorSnack(activity, msg)
                        }
                    }
                }
            }
        }

        authViewModel.showLoading.observe(activity) {
            if (it) {
                MyProgressBar.showProgress(activity)
            } else {
                MyProgressBar.hideProgress()
            }
        }
    }
}