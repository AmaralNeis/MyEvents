package com.fneis.myevents.extension

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.fneis.myevents.R
import com.fneis.myevents.ui.nav.MainActivity

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

fun Fragment.clearMenu() {
    val main = requireActivity() as? MainActivity ?: return
    main.toolbar.menu.clear()
}

fun Fragment.addMenu(callback: () -> Unit) {
    clearMenu()
    val main = requireActivity() as? MainActivity ?: return
    main.toolbar.inflateMenu(R.menu.detail_menu)
    main.toolbar.setOnMenuItemClickListener {
        if (it.itemId == R.id.actionShare) { callback() }

        return@setOnMenuItemClickListener true
    }
}
