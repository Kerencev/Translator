package com.kerencev.translator.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kerencev.translator.R
import com.kerencev.translator.data.dto.DataModel
import com.kerencev.translator.databinding.FragmentSearchBinding
import com.kerencev.translator.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragmentImpl :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    SearchFragment<SearchState> {

    private val viewModel: SearchViewModel.Base by viewModel()
    private var adapter: SearchAdapter? = null
    private val onListItemClickListener: SearchAdapter.OnListItemClickListener =
        object : SearchAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Toast.makeText(requireContext(), data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.subscribe().observe(viewLifecycleOwner) {
            renderData(it)
        }
        binding.searchFab.setOnClickListener {
            mainActivity?.showSearchDialog { word ->
                viewModel.getData(word)
            }
        }
    }

    override fun renderData(appState: SearchState) {
        when (appState) {
            is SearchState.Success -> {
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        binding.mainActivityRecyclerview.adapter =
                            SearchAdapter(onListItemClickListener, dataModel)
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }
            is SearchState.Loading -> {
                showViewLoading()
                binding.progressLoading.visibility = android.view.View.VISIBLE
            }
            is SearchState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        binding.errorTextview.text = error ?: getString(R.string.undefined_error)
        binding.reloadButton.setOnClickListener {
            viewModel.getData("hi")
        }
    }

    private fun showViewSuccess() {
        binding.successLinearLayout.visibility = android.view.View.VISIBLE
        binding.progressLoading.visibility = android.view.View.GONE
        binding.errorLinearLayout.visibility = android.view.View.GONE
    }

    private fun showViewLoading() {
        binding.successLinearLayout.visibility = android.view.View.GONE
        binding.progressLoading.visibility = android.view.View.VISIBLE
        binding.errorLinearLayout.visibility = android.view.View.GONE
    }

    private fun showViewError() {
        binding.successLinearLayout.visibility = android.view.View.GONE
        binding.progressLoading.visibility = android.view.View.GONE
        binding.errorLinearLayout.visibility = android.view.View.VISIBLE
    }
}