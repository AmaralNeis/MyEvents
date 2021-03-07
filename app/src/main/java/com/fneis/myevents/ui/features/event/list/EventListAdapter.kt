package com.fneis.myevents.ui.features.event.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.fneis.myevents.model.data.Event
import com.fneis.myevents.util.ItemEventClicked

class EventListAdapter(private val callback: ItemEventClicked) : ListAdapter<Event, EventViewHolder>(this) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EventViewHolder.create(parent, callback)

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Event, newItem: Event) = oldItem == newItem
    }

}