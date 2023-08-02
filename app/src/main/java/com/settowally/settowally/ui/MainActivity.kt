package com.settowally.settowally.ui

import android.annotation.SuppressLint
import android.app.UiModeManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.color.MaterialColors
import com.settowally.settowally.R
import com.settowally.settowally.databinding.ActivityMainBinding
import com.settowally.settowally.data.local.data_store.DataStoreRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor(
    //private val dataStoreRepository: DataStoreRepository,
) : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

      /*  val savedThemeStr = dataStoreRepository.darkModeSavedOption
        val savedTheme =return when(savedThemeStr){
            is "Dark" -> 1
            is "Light"->0
        }
        if (android.os.Build.VERSION.SDK_INT < 30) {
            AppCompatDelegate.setDefaultNightMode(savedTheme)
        } else {
            UiModeManager.MODE_NIGHT_YES
        }*/

    }

    /**     Status & Nav bar color changer        */
    /*private fun windowBarsColorChanger(color: Int) {
        window.statusBarColor = MaterialColors.getColor(
            binding.root,
            color
        )
        window.navigationBarColor = MaterialColors.getColor(
            binding.root,
            color
        )

    }*/


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp()
                || super.onSupportNavigateUp()
    }
}