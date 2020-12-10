package com.example.capstone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.ui.viewmodels.AnnouncementViewModel
import kotlinx.android.synthetic.main.fragment_announcement_detail.*

class AnnouncementDetailFragment : Fragment() {
    private val viewModel: AnnouncementViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_announcement_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAnnouncementDetail()
    }

    private fun observeAnnouncementDetail() {
        setFragmentResultListener(ANNOUNCEMENT_KEY) { _, bundle ->
            bundle.getString(ANNOUNCEMENT_BUNDLE_KEY)?.let {
                val announcementID = it.toInt()
                viewModel.getAnnouncementDetails(announcementID)
            }
        }

        viewModel.announcement.observe(viewLifecycleOwner, {
            txtViewAnnouncementDetailTitle.text = it.page_detail.title
            txtViewAnnouncementText.text = it.page_detail.text
            txtViewPublishDate.text = getString(R.string.txtViewPublishDate, it.page_detail.date)

            context?.let { it1 ->
                Glide.with(it1).load(it.banner_image).into(backdropImageAnnouncement)
            }
        })
    }
}