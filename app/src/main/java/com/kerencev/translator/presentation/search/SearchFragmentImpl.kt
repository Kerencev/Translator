package com.kerencev.translator.presentation.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kerencev.translator.R
import com.kerencev.translator.databinding.FragmentSearchBinding
import com.kerencev.translator.presentation.base.BaseFragment
import com.kerencev.translator.presentation.base.makeGone
import com.kerencev.translator.presentation.base.makeVisible
import com.kerencev.translator.presentation.details.DetailsModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragmentImpl :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    SearchFragment<SearchState> {

    private val viewModel: SearchViewModel by viewModel()
    private val adapter by lazy { SearchAdapter(onListItemClickListener) }
    private val onListItemClickListener by lazy {
        object : SearchAdapter.OnListItemClickListener {
            override fun onItemClick(data: com.kerencev.translator.data.dto.DataModel) {
                navigateToDetailsFragment(this@SearchFragmentImpl, data.convertToDetailsModel())
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainActivityRecyclerview.adapter = adapter
        viewModel.liveData.observe(viewLifecycleOwner) {
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
                    showError(getString(R.string.empty_server_response_on_success))
                } else {
                    showSuccess()
                    adapter.submitList(dataModel)
                }
            }
            is SearchState.Loading -> {
                showLoading()
            }
            is SearchState.Error -> {
                showError(appState.error.message)
            }
        }
    }

    override fun showError(error: String?) = with(binding) {
        successLinearLayout.makeGone()
        progressLoading.makeGone()
        errorLinearLayout.makeVisible()
        errorTextview.text = error ?: getString(R.string.undefined_error)
        reloadButton.setOnClickListener {
            viewModel.getData("hi")
        }
    }

    override fun showSuccess() = with(binding) {
        successLinearLayout.makeVisible()
        progressLoading.makeGone()
        errorLinearLayout.makeGone()
    }

    override fun showLoading() = with(binding) {
        successLinearLayout.makeGone()
        errorLinearLayout.makeGone()
        progressLoading.makeVisible()
    }

    override fun navigateToDetailsFragment(fragment: Fragment, data: DetailsModel) {
        mainActivity?.navigateToDetailsFragment(fragment, data)
    }
}