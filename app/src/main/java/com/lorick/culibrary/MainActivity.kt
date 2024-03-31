package com.lorick.culibrary

import androidx.lifecycle.lifecycleScope
import com.lorick.culibrary.base.BaseActivity
import com.lorick.culibrary.databinding.ActivityMainBinding
import com.lorick.culibrary.sharedPreference.SharedPrefs
import com.lorick.culibrary.ui.activity.attendance.AttendanceActivity
import com.lorick.culibrary.ui.activity.dashboard.DashboardScreenActivity
import com.lorick.culibrary.ui.activity.login.LoginScreenActivity
import com.lorick.culibrary.utils.launchActivity
import com.lorick.culibrary.utils.overrideColorStatusBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var activity = this@MainActivity

    @Inject
    lateinit var sharedPrefs :SharedPrefs

    override fun getLayoutRes(): Int  = R.layout.activity_main

    override fun initView() {
        overrideColorStatusBar()
        handlingScreen()
    }

    override fun viewModel() {

    }

    private fun handlingScreen() {
        lifecycleScope.launch {
            delay(1000)
            if (sharedPrefs.getUserLogin()){
                launchActivity<DashboardScreenActivity> { }
                finishAffinity()
            }else{
                launchActivity<LoginScreenActivity> {  }
                finishAffinity()
            }
        }
    }
}