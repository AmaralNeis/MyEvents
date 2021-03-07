package com.fneis.myevents.extension

import android.app.AlertDialog
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.fneis.myevents.R

fun Fragment.hideKeyboard() {
    val activity = activity ?: return
    try {
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = activity.currentFocus
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Fragment.openNavigationWith(directions: NavDirections, extras: FragmentNavigator.Extras? = null) {

    if (extras != null) {
        NavHostFragment.findNavController(this)
            .navigate(directions, extras)
    } else {
        NavHostFragment.findNavController(this)
            .navigate(directions)
    }
}

fun Fragment.dialogDefaultWithYesAndNo(
    title: String,
    message: String = "",
    yesClickListener: () -> Unit,
    noClickListener: () -> Unit = {}
) {
    AlertDialog.Builder(requireContext()).apply {
        setTitle(title)
        if (message.isNotEmpty())
            setMessage(message)
        setPositiveButton(android.R.string.ok) { _, _ -> yesClickListener() }
        setNegativeButton(android.R.string.cancel) { _, _ -> noClickListener() }
        show()
    }
}
