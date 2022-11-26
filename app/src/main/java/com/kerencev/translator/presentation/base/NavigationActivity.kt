package com.kerencev.translator.presentation.base

import androidx.fragment.app.Fragment
import com.kerencev.translator.presentation.details.DetailsModel

interface NavigationActivity {
    fun showSearchDialog(listener: (s: String) -> Unit)
    fun navigateToDetailsFragment(fragment: Fragment, data: DetailsModel)
}

