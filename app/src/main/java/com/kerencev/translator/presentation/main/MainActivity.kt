package com.kerencev.translator.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kerencev.translator.R
import com.kerencev.translator.databinding.ActivityMainBinding
import com.kerencev.translator.presentation.base.DialogFragmentListener
import com.kerencev.translator.presentation.base.NavigationActivity
import com.kerencev.translator.presentation.search.SearchDialogFragment
import com.kerencev.translator.presentation.search.SearchFragmentImpl

class MainActivity : AppCompatActivity(), NavigationActivity {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragmentImpl())
                .commit()
        }
    }

    override fun showSearchDialog(dialogFragmentListener: DialogFragmentListener) {
        val searchDialogFragment = SearchDialogFragment.newInstance()
        searchDialogFragment.setOnSearchClickListener(object :
            SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                dialogFragmentListener.onSearchClick(searchWord)
            }

            override fun onDismiss() {
                dialogFragmentListener.onDismiss()
            }
        })
        searchDialogFragment.show(supportFragmentManager, "")
    }

    override fun navigateTo(currentFragment: Fragment, nextFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .hide(currentFragment)
            .add(R.id.fragment_container, nextFragment)
            .addToBackStack("")
            .commit()
    }

    override fun popBackStack() {
        supportFragmentManager.popBackStack()
    }
}