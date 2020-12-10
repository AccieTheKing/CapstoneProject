package com.example.capstone.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.models.Announcement
import kotlinx.android.synthetic.main.item_announcement.view.*

class AnnouncementAdapter(private val announcements: List<Announcement>, private val onClick: (Announcement) -> Unit) :
    RecyclerView.Adapter<AnnouncementAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_announcement, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(announcements[position])
    }

    override fun getItemCount(): Int {
        return announcements.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onClick(announcements[adapterPosition])
            }
        }

        fun bind(announcement: Announcement) {
            itemView.txtAnnouncementTitle.text = announcement.title
            itemView.txtAnnouncementText.text = announcement.text
            Glide.with(itemView.context).load(announcement.banner_image).into(itemView.imgAnnouncement)
        }
    }
}