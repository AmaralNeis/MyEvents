package com.fneis.myevents.ui.features.event.checkIn

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.fneis.myevents.R
import com.fneis.myevents.databinding.FragmentCheckInBinding
import com.fneis.myevents.extension.afterTextChanged
import com.fneis.myevents.extension.clearMenu
import com.fneis.myevents.extension.hideKeyboard
import com.fneis.myevents.model.data.CheckIn
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckInFragment : Fragment() {
    //region Var
    private var _binding: FragmentCheckInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CheckInViewModel by viewModel()
    private val args: CheckInFragmentArgs by navArgs()
    //endregion

    //region Life Cycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCheckInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }
    //endregion

    //region Setup
    private fun setup() {
        setupObserver()
        setupNameEditText()
        setupEmailEditText()
        setupChekInButton()
        clearMenu()
    }

    private fun setupChekInButton() {
        binding.checkInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val name = binding.nameEditText.text.toString()
            viewModel.validateFormWith(email, name)
        }
    }

    private fun setupObserver() {
        viewModel.formLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CheckInState.EmailEmpty -> showEmailEmpty()
                is CheckInState.EmailInvalid -> showEmailInvalid()
                is CheckInState.ShortName -> showNameInvalid()
                is CheckInState.NameEmpty -> showNameEmpty()
                is CheckInState.FormDone -> requestCheckIn()
            }
        }

        viewModel.checkinLiveDate.observe(viewLifecycleOwner) { isSuccess ->
            binding.checkInButton.hideLoading()
            if (isSuccess) {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.check_in)
                    .setMessage(R.string.checkin_success)
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        backToHome()
                    }
                    .show()
            } else {
                Snackbar.make(binding.checkInButton, R.string.checkin_error, Snackbar.LENGTH_LONG ).show()
            }
        }
    }

    private fun backToHome() {
        view?.let {
            Navigation.findNavController(it).popBackStack(R.id.eventFragment, true)
        }
    }

    private fun setupNameEditText() {
        binding.nameEditText.apply {
            onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus)
                    viewModel.validateNameWith(this.text.toString())
            }

            afterTextChanged {
                binding.nameTextInput.error = null
            }
        }
    }

    private fun setupEmailEditText() {
        binding.emailEditText.apply {
            onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus)
                    viewModel.validateEmailWith(this.text.toString())
            }

            afterTextChanged {
                binding.emailTextInput.error = null
            }
        }
    }

    private fun requestCheckIn() {
        hideKeyboard()
        binding.checkInButton.showLoading()
        viewModel.sendCheckin(
            CheckIn(
                args.idEvent,
                binding.nameEditText.text.toString(),
                binding.emailEditText.text.toString()
            )
        )
    }
    //endregion

    //region Messages
    private fun showEmailInvalid() { binding.emailTextInput.error = getString(R.string.email_error) }

    private fun showEmailEmpty() { binding.emailTextInput.error = getString(R.string.email_empty) }

    private fun showNameInvalid() { binding.nameTextInput.error = getString(R.string.name_error) }

    private fun showNameEmpty() { binding.nameTextInput.error = getString(R.string.name_empty) }

    //endregion
}
