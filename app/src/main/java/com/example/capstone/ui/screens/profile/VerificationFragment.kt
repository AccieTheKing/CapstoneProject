package com.example.capstone.ui.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.capstone.R
import com.example.capstone.ui.viewmodels.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_verification.*

class VerificationFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        arguments.let {
            val userEmail = it?.getString("email").toString()
            val userPhoneNumber = it?.getString("phoneNumber").toString()

            btnSendVerification.setOnClickListener {
                val code = txtFieldVerificationCode.text.toString()
                viewModel.sendVerificationCode(code, userPhoneNumber, userEmail)
            }

            viewModel.success.observe(viewLifecycleOwner, { validToken ->
                if (validToken) findNavController().navigate(R.id.action_verificationFragment_to_profileFragment)
                else view?.let { it1 ->
                    Snackbar.make(
                        it1.rootView,
                        "Wrong token",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
//            findNavController().popBackStack()
            })
        }
    }

}