package com.okation.aivideocreator.util

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.NavController
import kotlinx.coroutines.*

class LoadingState(private val vLoading: View, private val progressBar: View, private val tvSaved: View, private val navController: NavController) {

    fun showLoadingState() {
        progressBar.visibility = View.VISIBLE
        vLoading.visibility = View.VISIBLE


        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)

            withContext(Dispatchers.Main) {
                vLoading.visibility = View.GONE
                progressBar.visibility = View.GONE
                tvSaved.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    navController.navigateUp()
                }, 500)
            }
        }
    }
}

