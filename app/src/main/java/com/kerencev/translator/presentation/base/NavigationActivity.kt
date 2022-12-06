package com.kerencev.translator.presentation.base

import androidx.fragment.app.Fragment

interface NavigationActivity {
    fun showSearchDialog(dialogFragmentListener: DialogFragmentListener)
    fun navigateTo(currentFragment: Fragment, nextFragment: Fragment)
    fun popBackStack()
}

interface DialogFragmentListener {
    fun onSearchClick(word: String)
    fun onDismiss()
}

