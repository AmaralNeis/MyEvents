package com.fneis.myevents.ui.nav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.fneis.myevents.R
import com.fneis.myevents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //region Var
    private lateinit var binding: ActivityMainBinding
    //endregion

    //region Life Cycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_MyEvents)
        setContentView(binding.root)
        setup()
    }

    //region Setup
    private fun setup() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment? ?: return

        val navController = host.navController
        val appBarConfiguration = AppBarConfiguration(navGraph = navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }
    //endregion
}
