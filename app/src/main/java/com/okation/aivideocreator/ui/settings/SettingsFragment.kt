package com.okation.aivideocreator.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.databinding.FragmentSettingsBinding
import com.okation.aivideocreator.ui.inapp.PremiumViewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    val viewModel: PremiumViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel.isPremium.observe(viewLifecycleOwner) { isPremium ->
                btnGetPremium.isVisible = !isPremium
            }

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnPrivacy.setOnClickListener {
                browsePrivacy()
            }

            btnTerms.setOnClickListener {
                browseTerms()
            }

            btnContact.setOnClickListener {
                sendEmail()
            }

            btnRate.setOnClickListener {
                rate()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun browsePrivacy(){
        val webView: WebView = binding.webView
        webView.isVisible = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://policies.google.com/privacy?hl=en")
    }

    private fun browseTerms(){
        val webView: WebView = binding.webView
        webView.isVisible = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://policies.google.com/terms?hl=en")
    }

    private fun sendEmail() {
        val recipientEmail = "support@neonapps.co"
        val subject = "About Baby Tracker App"

        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:$recipientEmail")
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        } catch (_: android.content.ActivityNotFoundException) {
        }
    }

    private fun rate() {
        val appPackageName = "com.okation.aivideocreator"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(
                "https://play.google.com/store/apps/details?id=$appPackageName")
            setPackage("com.android.vending")
        }
        startActivity(intent)
    }
}