package com.fneis.myevents.ui.features.event.list

import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fneis.myevents.databinding.RowEventBinding
import com.fneis.myevents.extension.loadWith
import com.fneis.myevents.extension.toDateString
import com.fneis.myevents.extension.toPriceAmount
import com.fneis.myevents.model.data.Event
import com.fneis.myevents.util.ItemEventClicked

class EventViewHolder(
    private val binding: RowEventBinding,
    private val callback: ItemEventClicked
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(event: Event) {
        binding.titleTextView.text = event.title
        binding.descriptionTextView.text = event.description
        binding.dateTextView.text = event.date.toDateString()

        binding.eventImageView.apply {
            loadWith(
                event.image,
                onSuccess = {
                    TransitionManager.beginDelayedTransition(binding.root)
                    this.visibility = View.VISIBLE
                },
                onError = {
                    TransitionManager.beginDelayedTransition(binding.root)
                    this.visibility = View.GONE
                }
            )
        }

        event.price?.let {
            binding.priceTextView.visibility = View.VISIBLE
            binding.priceTextView.text = it.toPriceAmount()
        } ?: kotlin.run {
            binding.priceTextView.visibility = View.INVISIBLE
        }

        binding.root.setOnClickListener {
            callback.invoke(event)
        }
    }

    companion object {
        fun create(parent: ViewGroup, callback: ItemEventClicked): EventViewHolder {
            val binding = RowEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return EventViewHolder(binding, callback)
        }
    }
}
