package com.kerencev.translator.presentation.history

import android.os.Bundle
import android.view.View
import com.kerencev.translator.databinding.FragmentHistoryBinding
import com.kerencev.translator.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragmentImpl : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate),
    HistoryFragment {

    private val viewModel: HistoryViewModel.Base by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestData()
    }

    override fun requestData() {
        viewModel.getData()
        viewModel.liveData.observe(viewLifecycleOwner) {
            renderData(it)
        }
    }

    override fun renderData(historyState: HistoryState) {
        when (historyState) {
            is HistoryState.Success -> {}
            is HistoryState.Loading -> {}
            is HistoryState.Error -> {}
        }
    }
}