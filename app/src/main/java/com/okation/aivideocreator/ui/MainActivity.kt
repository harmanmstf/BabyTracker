package com.okation.aivideocreator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.okation.aivideocreator.R
import com.okation.aivideocreator.ui.inapp.PremiumViewModel
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import dagger.hilt.android.AndroidEntryPoint
import com.okation.aivideocreator.util.GOOGLE_API_KEY
import androidx.navigation.findNavController


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            WindowCompat.setDecorFitsSystemWindows(window, false)


        setContentView(R.layout.activity_main)

        val viewModel: PremiumViewModel by viewModels()
        viewModel.initializePremiumStatus(applicationContext)

        viewModel.isPremium.observe(this) { isPremium ->
            val navController = findNavController(R.id.nav_host_fragment)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (isPremium == true && destination.id == R.id.onboarding1Fragment) {
                    navController.navigate(R.id.homeFragment)
                }
            }
        }

        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(PurchasesConfiguration.Builder(this, GOOGLE_API_KEY).build())
    }
}





