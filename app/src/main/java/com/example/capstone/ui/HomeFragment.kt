package com.example.capstone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.R
import com.example.capstone.models.Announcement
import com.example.capstone.ui.adapters.AnnouncementAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private val announcements = arrayListOf<Announcement>()
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
    }

    private fun initView() {
        announcementAdapter = AnnouncementAdapter(announcements)
        rcHomeFragment.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rcHomeFragment.adapter = announcementAdapter
        DividerItemDecoration(
            context,
            DividerItemDecoration.VERTICAL
        )
        announcementAdapter.notifyDataSetChanged()
    }
}