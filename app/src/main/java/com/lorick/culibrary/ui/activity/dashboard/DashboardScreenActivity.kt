package com.lorick.culibrary.ui.activity.dashboard

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lorick.culibrary.R
import com.lorick.culibrary.base.BaseActivity
import com.lorick.culibrary.databinding.ActivityDashboardScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardScreenActivity : BaseActivity<ActivityDashboardScreenBinding>(), NavController.OnDestinationChangedListener  {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var navigation: BottomNavigationView

    override fun getLayoutRes(): Int = R.layout.activity_dashboard_screen

    override fun initView() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostView) as NavHostFragment
        navController = navHostFragment.navController
        navigation = findViewById(R.id.bottomNavigationView)
        NavigationUI.setupWithNavController(navigation, navHostFragment.navController)
        navController.addOnDestinationChangedListener(this)
        bottomNavigationController()
    }

    override fun viewModel() {

    }

    private fun bottomNavigationController() {
        navigation.setOnItemSelectedListener { item ->
            // Handle item selection
            binding.apply {
                when (item.itemId) {
                    R.id.homeFragment -> {
                        navController.navigate(R.id.homeFragment)
                    }

                    R.id.meditationsFragment -> {
                        navController.navigate(R.id.meditationsFragment)
                    }

                    R.id.edificationFragment -> {
                        navController.navigate(R.id.edificationFragment)
                    }

                    R.id.journalFragment -> {
                        navController.navigate(R.id.journalFragment)
                    }
                }

            }


            // Deselect previously selected item (change its icon to the outline version)
            val menu = navigation.menu
            for (i in 0 until menu.size()) {
                val menuItem = menu.getItem(i)
                if (menuItem != item) {
                    when (menuItem.itemId) {
                        R.id.homeFragment -> menuItem.setIcon(R.drawable.ic_chat)
                        R.id.meditationsFragment -> menuItem.setIcon(R.drawable.ic_chat)
                        R.id.edificationFragment -> menuItem.setIcon(R.drawable.ic_chat)
                        R.id.journalFragment -> menuItem.setIcon(R.drawable.ic_chat)
                        // Handle other items similarly
                    }
                }
            }
            true
        }

    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {

    }

}