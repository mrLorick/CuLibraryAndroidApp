package com.lorick.culibrary.ui.login

import com.lorick.culibrary.R
import com.lorick.culibrary.base.BaseActivity
import com.lorick.culibrary.databinding.ActivityLoginScreenBinding
import com.lorick.culibrary.utils.setStatusBarHideBoth

class LoginScreenActivity : BaseActivity<ActivityLoginScreenBinding>() {

    override fun getLayoutRes(): Int = R.layout.activity_login_screen

    override fun initView() {
        setStatusBarHideBoth()

    }

    override fun viewModel() {

    }
}