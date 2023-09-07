package com.okation.aivideocreator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.okation.aivideocreator.R
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import dagger.hilt.android.AndroidEntryPoint
import com.okation.aivideocreator.util.GOOGLE_API_KEY


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(PurchasesConfiguration.Builder(this, GOOGLE_API_KEY).build())
    }
}