package com.example.babytracker.ui.inapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.babytracker.R
import com.example.babytracker.databinding.FragmentInappBinding
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.PurchaseParams
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.getOfferingsWith
import com.revenuecat.purchases.models.StoreProduct
import com.revenuecat.purchases.purchaseWith

class InappFragment : Fragment() {

    private var _binding: FragmentInappBinding? = null
    private val binding get() = _binding!!

    private lateinit var revPackage: Package


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentInappBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Purchases.sharedInstance.getOfferingsWith({ error ->
            // An error occurred
        }) { offerings ->
            offerings["Small"]?.availablePackages?.takeUnless { it.isNullOrEmpty() }
                ?.let {
                    // Display packages for sale
                }
        }



        binding.annual.setOnClickListener {
            Purchases.sharedInstance.getOfferingsWith(
                onError = { error ->
                    /* Optional error handling */
                },
                onSuccess = { offerings ->
                    // Display current offering with offerings.current
                })
        }

        binding.btnStart.setOnClickListener {
            Purchases.sharedInstance.purchaseWith(
                PurchaseParams.Builder(requireActivity(), revPackage).build(),
                onError = { error, userCancelled -> /* No purchase */ },
                onSuccess = { product, customerInfo ->
                    findNavController().navigate(R.id.action_inappFragment_to_homeFragment)

                }
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}