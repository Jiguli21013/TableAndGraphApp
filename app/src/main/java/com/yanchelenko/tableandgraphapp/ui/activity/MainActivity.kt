package com.yanchelenko.tableandgraphapp.ui.activity

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.yanchelenko.tableandgraphapp.R
import com.yanchelenko.tableandgraphapp.databinding.ActivityMainBinding
import com.yanchelenko.tableandgraphapp.utils.navigation.SCREENS
import com.yanchelenko.tableandgraphapp.utils.navigation.setCurrentScreenWithNavController
import com.yanchelenko.tableandgraphapp.utils.ui.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //todo lateinit плохо?
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //todo нужно (сгенерилось автоматически)?
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activityMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        val host = supportFragmentManager.findFragmentById(R.id.navHostContainer) as NavHostFragment
        navController = host.navController
    }

    fun navigateToScreen(screen: SCREENS) {
        hideKeyboard()
        findNavController(R.id.navHostContainer).apply {
            setCurrentScreenWithNavController(screen)
        }
    }

    private fun hideKeyboard() {
        val imm = applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    fun setVisibilityProgressBar(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
    }

}
