package com.fneis.myevents.ui.features.event.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.fneis.myevents.databinding.FragmentEventDetailBinding
import com.fneis.myevents.extension.loadWith
import com.fneis.myevents.extension.openNavigationWith
import com.fneis.myevents.model.data.Event

class EventDetailFragment : Fragment() {
    //region Var
    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!
    private val args: EventDetailFragmentArgs by navArgs()
    //endregion

    //region Life Cycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }
    //endregion

    //region Setup
    private fun setup() {
        val event = args.event
        setTitle(event)
        setupImageView(event)
        setupMapButton(event)
        setupCheckInButton(event)
    }

    private fun setupImageView(event: Event) {
        binding.imageView.apply {
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
    }

    private fun setTitle(event: Event) {
        binding.titleTextView.text = event.title
        binding.subTitleTextView.text = event.description
    }

    private fun setupCheckInButton(event: Event) {
        event.id?.let { idEvent ->
            binding.chekinButton.apply {
                visibility = View.VISIBLE
                setOnClickListener { openChekInWith(idEvent) }
            }
        }
    }

    private fun setupMapButton(event: Event) {
        if (event.latitude != null && event.longitude != null) {
            binding.seeMapButton.apply {
                visibility = View.VISIBLE
                setOnClickListener { openMap(event.latitude, event.longitude) }
            }
        }
    }

    private fun openMap(latitude: Double, longitude: Double) {
        val gmmIntentUri = Uri.parse("geo:0,0?q=$latitude,$longitude(${args.event.title})")
        Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
            setPackage("com.google.android.apps.maps")
            resolveActivity(requireActivity().packageManager)?.let {
                startActivity(this)
            }
        }
    }

    private fun openChekInWith(idEvent: Int) {
        val directions = EventDetailFragmentDirections.actionEventDetailFragmentToChekinFragment(idEvent)
        openNavigationWith(directions)
    }
    //endregion
}
