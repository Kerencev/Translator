package com.kerencev.translator.presentation.history

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.kerencev.domain.model.DetailsModel
import com.kerencev.translator.R
import com.kerencev.translator.databinding.FragmentHistoryBinding
import com.kerencev.translator.presentation.base.*
import com.kerencev.translator.presentation.details.DetailsFragmentImpl
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragmentImpl : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate),
    HistoryFragment {

    private val viewModel: HistoryViewModel by viewModel()
    private val adapter: HistoryAdapter by lazy {
        HistoryAdapter(object : HistoryAdapter.OnItemClickListener {
            override fun onClick(data: DetailsModel) {
                binding.historyTextInput.hideKeyboard(requireContext())
                mainActivity?.navigateTo(
                    currentFragment = this@HistoryFragmentImpl,
                    nextFragment = DetailsFragmentImpl.newInstance(data)
                )
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.historyRecycler.adapter = adapter
        if (savedInstanceState == null) {
            requestData()
        }
        viewModel.liveData.observe(viewLifecycleOwner) {
            renderData(it)
        }
        setAllClicks()
        setSearchInputListener()
    }

    override fun requestData() {
        viewModel.getData()
    }

    override fun renderData(historyState: HistoryState) {
        when (historyState) {
            is HistoryState.Success -> {
                showSuccess(historyState)
            }
            is HistoryState.Loading -> {
                showLoading()
            }
            is HistoryState.Error -> {
                showError(historyState.error)
            }
        }
    }

    override fun showSuccess(historyState: HistoryState.Success) = with(binding) {
        historyRecycler.makeVisible()
        historyErrorLayout.makeGone()
        historyProgress.makeGone()
        adapter.submitList(historyState.data)
        if (historyState.isSearchState) showSearchInput()
        else showToolbar()
    }

    override fun showLoading() = with(binding) {
        historyProgress.makeVisible()
        historyRecycler.makeGone()
        historyErrorLayout.makeGone()
    }

    override fun showError(throwable: Throwable) = with(binding) {
        historyErrorLayout.makeVisible()
        historyProgress.makeGone()
        historyRecycler.makeGone()
        reloadButton.setOnClickListener {
            viewModel.getData()
        }
    }

    private fun showToolbar() = with(binding) {
        toolbar.makeVisible()
        historyTextInput.hideKeyboard(requireContext())
        historyInputLayout.makeGone()
    }

    private fun showSearchInput() = with(binding) {
        historyInputLayout.makeVisible()
        historyTextInput.showKeyBoard(requireContext())
        toolbar.makeGone()
    }

    private fun setAllClicks() = with(binding) {
        toolbar.setNavigationOnClickListener {
            mainActivity?.popBackStack()
        }
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_search -> {
                    viewModel.getData(editable = null)
                }
            }
            true
        }
        historyInputLayout.setEndIconOnClickListener {
            historyTextInput.text?.clear()
            viewModel.getCacheData()
        }
    }

    private fun setSearchInputListener() {
        binding.historyTextInput.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do not call the method when restoring the state
                if (start == 0 && before == 0 && count == 0) return
                viewModel.getData(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun afterTextChanged(s: Editable?) = Unit
        })
    }
}