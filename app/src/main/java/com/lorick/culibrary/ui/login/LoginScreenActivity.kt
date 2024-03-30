package com.lorick.culibrary.ui.login

import com.lorick.culibrary.R
import com.lorick.culibrary.base.BaseActivity
import com.lorick.culibrary.databinding.ActivityLoginScreenBinding
import com.lorick.culibrary.ui.activity.AttendanceActivity
import com.lorick.culibrary.utils.finishActivity
import com.lorick.culibrary.utils.launchActivity
import com.lorick.culibrary.utils.overrideColorStatusBar

class LoginScreenActivity : BaseActivity<ActivityLoginScreenBinding>() {

    override fun getLayoutRes(): Int = R.layout.activity_login_screen

    override fun initView() {
        overrideColorStatusBar()
        setOnClickListener()
    }

    /** set on click listener*/
    private fun setOnClickListener() {
        binding.apply {
            btnLogIn.setOnClickListener {
                launchActivity<AttendanceActivity> {  }
                finishActivity()
            }
        }
    }

    override fun viewModel() {

    }
}