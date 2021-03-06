package com.example.capstone.ui.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.repository.ProfileRepository
import com.example.capstone.ui.viewmodels.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_edit_profile.*

class ProfileEditFragment : Fragment() {
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        Glide.with(this)
            .load("https://images.pexels.com/photos/417074/pexels-photo-417074.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260")
            .into(fragmentProfileDetailImage)
        // Set email address and phone number if these already given
        txtViewEmailProfileFragment.setText(ProfileRepository.emailAddress)
        txtViewPhoneNumberProfileFragment.setText(ProfileRepository.phoneNumber)

        btnSaveProfileInfo.setOnClickListener { updateProfileInfo() }
    }

    private fun updateProfileInfo() {
        val profileEmail = txtViewEmailProfileFragment.text.toString()
        val profilePhoneNumber = txtViewPhoneNumberProfileFragment.text.toString()

        if (profileEmail.isNotEmpty() && profilePhoneNumber.isNotEmpty()) {
            viewModel.getVerificationCode(profilePhoneNumber, profileEmail)
            findNavController().navigate(
                R.id.action_profileEditFragment_to_verificationFragment,
                bundleOf(Pair("email", profileEmail), Pair("phoneNumber", profilePhoneNumber))
            )
        } else {
            Toast.makeText(context, R.string.txtCheckFields, Toast.LENGTH_SHORT).show()
        }
    }

}