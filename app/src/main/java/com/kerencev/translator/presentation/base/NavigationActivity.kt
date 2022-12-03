package com.kerencev.translator.presentation.base

import androidx.fragment.app.Fragment

interface NavigationActivity {
    fun showSearchDialog(listener: (s: String) -> Unit)
    fun navigateTo(currentFragment: Fragment, nextFragment: Fragment)
}

