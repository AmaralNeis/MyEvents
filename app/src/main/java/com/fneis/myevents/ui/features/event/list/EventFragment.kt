package com.fneis.myevents.ui.features.event.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.fneis.myevents.databinding.FragmentEventsBinding
import com.fneis.myevents.extension.openNavigationWith
import com.fneis.myevents.model.data.Event
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventFragment : Fragment() {

    //region Var
    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!
    private val adapter = EventListAdapter { openDetailWith(it) }
    private val viewModel: EventViewModel by viewModel()
    //endregion

    //region Life Cycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }
    //endregion

    //region Setup
    private fun setup() {
        setupRecyclerView()
        setupObsever()
        viewModel.getEvents()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = this@EventFragment.adapter
        }
    }

    private fun setupObsever() {
        viewModel.listLiveData.observe(
            viewLifecycleOwner,
            Observer {
                it.first?.let { items -> showList(items) }
                it.second?.let { message -> showError(message) }
            }
        )
    }

    private fun showList(items: List<Event>) {
        binding.viewFlipper.displayedChild = 1
        adapter.submitList(items)
    }

    private fun showError(message: Int) {
        binding.viewFlipper.displayedChild = 2
        binding.errorTextView.text = getString(message)
    }

    private fun openDetailWith(event: Event) {
        val directions = EventFragmentDirections.actionEventFragmentToEventDetailFragment(event)
        openNavigationWith(directions)
    }
    //endregion
}
