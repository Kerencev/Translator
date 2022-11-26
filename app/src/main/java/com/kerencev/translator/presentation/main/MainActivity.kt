package com.kerencev.translator.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kerencev.translator.R
import com.kerencev.translator.databinding.ActivityMainBinding
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
                .commitNow()
        }
    }

    override fun showSearchDialog(listener: (s: String) -> Unit) {
        val searchDialogFragment = SearchDialogFragment.newInstance()
        searchDialogFragment.setOnSearchClickListener(object :
            SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                listener.invoke(searchWord)
            }
        })
        searchDialogFragment.show(supportFragmentManager, "")
    }
}