package com.example.capstone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.repository.ProfileRepository
import com.example.capstone.ui.viewmodels.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeProfile()
    }

    private fun observeProfile() {
        viewModel.getProfile(ProfileRepository.phoneNumber)
        viewModel.profile.observe(viewLifecycleOwner, {
            Glide.with(this).load(it.profile_picture).into(imgUserProfileFragment)
            txtEmailAddressValue.text =
                getString(R.string.txtPhoneNumberStringTemplateProfileFragment, it.phone_number)
            txtViewPhoneNumberProfile.text =
                getString(R.string.txtEmailAddressStringTemplateProfileFragment, it.email_address)
        })
    }

}