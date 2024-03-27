package com.lorick.culibrary

import androidx.lifecycle.lifecycleScope
import com.lorick.culibrary.base.BaseActivity
import com.lorick.culibrary.databinding.ActivityMainBinding
import com.lorick.culibrary.ui.login.LoginScreenActivity
import com.lorick.culibrary.utils.launchActivity
import com.lorick.culibrary.utils.overrideColorStatusBar
import com.lorick.culibrary.utils.setStatusBarHideBoth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutRes(): Int  = R.layout.activity_main

    override fun initView() {
        overrideColorStatusBar(R.color.white)
        handlingScreen()
    }

    override fun viewModel() {

    }

    private fun handlingScreen() {
        lifecycleScope.launch {
            delay(1000)
            if (true){
                launchActivity<LoginScreenActivity> { }
                finishAffinity()
            }else{
                launchActivity<LoginScreenActivity> {  }
                finishAffinity()
            }
        }
    }
}