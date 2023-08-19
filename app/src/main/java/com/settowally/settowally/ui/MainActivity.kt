package com.settowally.settowally.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.color.MaterialColors
import com.settowally.settowally.R
import com.settowally.settowally.data.local.data_store.DataStoreRepository
import com.settowally.settowally.data.model.Theme
import com.settowally.settowally.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTheme()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.wallpaperDetailsFragment -> {
                    binding.bottomNavBar.visibility = View.GONE
                    window.statusBarColor = Color.TRANSPARENT
                    window.navigationBarColor = Color.TRANSPARENT
                }

                R.id.setWallpaperBottomSheetFragment -> {
                    binding.bottomNavBar.visibility = View.GONE
                    window.navigationBarColor = Color.TRANSPARENT
                }

                R.id.downloadBottomSheetFragment -> {
                    binding.bottomNavBar.visibility = View.GONE
                    window.navigationBarColor = Color.TRANSPARENT
                }

                R.id.detailsBottomSheetFragment -> {
                    binding.bottomNavBar.visibility = View.GONE
                    window.navigationBarColor = Color.TRANSPARENT
                }

                else -> {
                    binding.bottomNavBar.visibility = View.VISIBLE
                    window.statusBarColor = MaterialColors.getColor(
                        binding.root,
                        com.google.android.material.R.attr.colorSecondaryContainer
                    )
                    window.navigationBarColor = MaterialColors.getColor(
                        binding.root,
                        com.google.android.material.R.attr.colorSecondaryContainer
                    )
                }
            }
        }
    }

    private fun setTheme() {
        lifecycleScope.launch {
            dataStoreRepository.selectedThemeFlow.collect { savedTheme ->
                when (savedTheme) {
                    Theme.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    Theme.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    Theme.SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp()
                || super.onSupportNavigateUp()
    }
}