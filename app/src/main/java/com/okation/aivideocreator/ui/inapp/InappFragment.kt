package com.okation.aivideocreator.ui.inapp

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.UnderlineSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.FragmentInappBinding
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.PurchaseParams
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.getOfferingsWith
import com.revenuecat.purchases.purchaseWith

class InappFragment : Fragment() {

    private var _binding: FragmentInappBinding? = null
    private val binding get() = _binding!!

    private lateinit var revPackage: Package
    private val viewModel: PremiumViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentInappBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            val privacyText = getString(R.string.privacy_policy_innapp)
            setUnderlinedText(tvPrivacy, privacyText)

            val restoreText = getString(R.string.restore_purchase_inapp)
            setUnderlinedText(tvRestore, restoreText)

            val termsText = getString(R.string.terms_of_use_inapp)
            setUnderlinedText(tvTerms, termsText)

            setButtonEnabled(false)

            btnClose.setOnClickListener {
                findNavController().navigate(R.id.action_inappFragment_to_homeFragment)
            }



            Purchases.sharedInstance.getOfferingsWith({
            }) { offerings ->
                offerings.current?.availablePackages?.forEach {
                    println(it)
                }
                offerings.current?.getPackage("Small")?.also {
                    tvPrice.text = it.product.price.formatted
                }

                Purchases.sharedInstance.getOfferingsWith({
                }) { offerings ->
                    annual.setOnClickListener {
                        annual.isChecked = true
                        btnStart.run {
                            isClickable = true
                            isActivated = true
                            isEnabled = true
                        }
                        offerings.current?.getPackage("Small")?.also {
                            revPackage = it
                        }
                    }
                }


                btnStart.setOnClickListener {
                    setButtonEnabled(false)
                    Purchases.sharedInstance.purchaseWith(
                        PurchaseParams.Builder(requireActivity(), revPackage).build(),
                        onError = { error, _ ->
                            Log.e("errorCode: ${error.code}", error.message)
                            setButtonEnabled(true)
                        },
                        onSuccess = { _, _ ->
                            viewModel.setPremiumStatus(true)

                            val sharedPreferences = requireContext().getSharedPreferences("premium", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putBoolean("is_premium", true)
                            editor.apply()

                            findNavController().navigate(R.id.action_inappFragment_to_homeFragment)
                        }
                    )
                }
            }

            tvPrivacy.setOnClickListener{
                browsePrivacy()
            }

            tvTerms.setOnClickListener {
                browseTerms()
            }
        }
    }

    private fun setButtonEnabled(enabled: Boolean) {
        binding.btnStart.apply {
            isClickable = enabled
            isActivated = enabled
            isEnabled = enabled
        }
    }


    private fun setUnderlinedText(textView: TextView, text: String) {
        val spannableString = SpannableString(text)
        spannableString.setSpan(UnderlineSpan(), 0, text.length, 0)
        val builder = SpannableStringBuilder().append(spannableString)
        textView.text = builder
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
}