package com.example.capstone.ui.screens.announcement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.R
import com.example.capstone.models.Announcement
import com.example.capstone.ui.adapters.AnnouncementAdapter
import com.example.capstone.ui.viewmodels.AnnouncementViewModel
import kotlinx.android.synthetic.main.fragment_home.*

const val ANNOUNCEMENT_KEY = "ANNOUNCEMENT_KEY"
const val ANNOUNCEMENT_BUNDLE_KEY = "ANNOUNCEMENT_BUNDLE_KEY"

class HomeFragment : Fragment() {
    private val announcements = arrayListOf<Announcement>()
    private val viewModel: AnnouncementViewModel by viewModels()
    private lateinit var announcementAdapter: AnnouncementAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        observeAnnouncement()
    }

    private fun initView() {
        announcementAdapter = AnnouncementAdapter(announcements, ::goToAnnouncement)
        rcHomeFragment.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rcHomeFragment.adapter = announcementAdapter
        DividerItemDecoration(
            context,
            DividerItemDecoration.VERTICAL
        )
        announcementAdapter.notifyDataSetChanged()
    }

    private fun observeAnnouncement() {
        viewModel.getAnnouncements()
        viewModel.announcements.observe(viewLifecycleOwner, {
            announcements.clear()
            announcements.addAll(it)
            announcementAdapter.notifyDataSetChanged()
        })
    }

    private fun goToAnnouncement(announcement: Announcement) {
        setFragmentResult(
            ANNOUNCEMENT_KEY,
            bundleOf(Pair(ANNOUNCEMENT_BUNDLE_KEY, announcement.id.toString()))
        )
        findNavController().navigate(R.id.action_homeFragment_to_announcementDetailFragment)
    }
}