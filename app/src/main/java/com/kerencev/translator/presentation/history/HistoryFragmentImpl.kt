package com.kerencev.translator.presentation.history

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.kerencev.translator.databinding.FragmentHistoryBinding
import com.kerencev.translator.presentation.base.BaseFragment
import com.kerencev.translator.presentation.base.makeGone
import com.kerencev.translator.presentation.base.makeVisible
import com.kerencev.translator.presentation.details.DetailsFragmentImpl
import com.kerencev.translator.presentation.details.DetailsModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragmentImpl : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate),
    HistoryFragment {

    private val viewModel: HistoryViewModel by viewModel()
    private val adapter: HistoryAdapter by lazy {
        HistoryAdapter(object : HistoryAdapter.OnItemClickListener {
            override fun onClick(data: DetailsModel) {
                mainActivity?.navigateTo(
                    currentFragment = this@HistoryFragmentImpl,
                    nextFragment = DetailsFragmentImpl.newInstance(data)
                )
            }
        })
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
        binding.toolbar.setNavigationOnClickListener {
            mainActivity?.popBackStack()
        }
        binding.historyTextInput.addTextChangedListener { editable ->
            viewModel.getData(editable)
        }
    }

    override fun requestData() {
        viewModel.getData()
    }

    override fun renderData(historyState: HistoryState) {
        when (historyState) {
            is HistoryState.Success -> {
                showSuccess(historyState.data)
            }
            is HistoryState.Loading -> {
                showLoading()
            }
            is HistoryState.Error -> {
                showError(historyState.error)
            }
        }
    }

    override fun showSuccess(data: List<DetailsModel>) = with(binding) {
        historyRecycler.makeVisible()
        historyErrorLayout.makeGone()
        historyProgress.makeGone()
        adapter.submitList(data)
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
}