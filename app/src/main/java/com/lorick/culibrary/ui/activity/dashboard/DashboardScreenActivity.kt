package com.lorick.culibrary.ui.activity.dashboard

 import android.graphics.Color
 import androidx.core.content.ContextCompat
 import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.lorick.culibrary.R
import com.lorick.culibrary.base.BaseActivity
import com.lorick.culibrary.databinding.ActivityDashboardScreenBinding
import com.lorick.culibrary.ui.fragment.HomeFragment
import com.lorick.culibrary.utils.overrideColorStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardScreenActivity : BaseActivity<ActivityDashboardScreenBinding>(){
    var home = HomeFragment()

    override fun getLayoutRes(): Int = R.layout.activity_dashboard_screen

    override fun initView() {
        overrideColorStatusBar(R.color.red)
        binding.bottomNavigationView.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        setBottom(home)
        selectedListener()
    }

    override fun viewModel() {

    }

    private fun setBottom(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.navHostView, fragment, "")
        fragmentTransaction.commit()
    }

    private fun selectedListener() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> menuItem.setIcon(R.drawable.ic_chat)
                R.id.meditationsFragment -> menuItem.setIcon(R.drawable.ic_chat)
                R.id.edificationFragment -> menuItem.setIcon(R.drawable.ic_chat)
                R.id.journalFragment -> menuItem.setIcon(R.drawable.ic_chat)
                // Handle other items similarly
            }
            false
        }
    }

}