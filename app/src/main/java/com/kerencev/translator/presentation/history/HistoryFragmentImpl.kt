package com.kerencev.translator.presentation.history

import android.os.Bundle
import android.view.View
import com.kerencev.translator.databinding.FragmentHistoryBinding
import com.kerencev.translator.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragmentImpl : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate),
    HistoryFragment {

    private val viewModel: HistoryViewModel by viewModel()
    private val adapter: HistoryAdapter by lazy {
        HistoryAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            requestData()
        }
        viewModel.liveData.observe(viewLifecycleOwner) {
            renderData(it)
        }
        binding.historyRecycler.adapter = adapter
    }

    override fun requestData() {
        viewModel.getData()
    }

    override fun renderData(historyState: HistoryState) {
        when (historyState) {
            is HistoryState.Success -> {
                adapter.submitList(historyState.data)
            }
            is HistoryState.Loading -> {

            }
            is HistoryState.Error -> {

            }
        }
    }
}