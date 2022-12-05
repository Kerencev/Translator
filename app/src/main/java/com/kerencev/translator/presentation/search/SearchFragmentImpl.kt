package com.kerencev.translator.presentation.search

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kerencev.data.dto.DataModel
import com.kerencev.translator.R
import com.kerencev.translator.databinding.FragmentSearchBinding
import com.kerencev.translator.di.SCOPE_A
import com.kerencev.translator.presentation.base.BaseFragment
import com.kerencev.translator.presentation.base.makeGone
import com.kerencev.translator.presentation.base.makeVisible
import com.kerencev.translator.presentation.details.DetailsFragmentImpl
import com.kerencev.translator.presentation.history.HistoryFragmentImpl
import com.kerencev.translator.test.TestDep
import com.kerencev.translator.utils.Converter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.getOrCreateScope
import org.koin.core.qualifier.named

class SearchFragmentImpl :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    SearchFragment<SearchState>, KoinScopeComponent {

    private val myScope by lazy { getKoin().getOrCreateScope("", named(SCOPE_A)) }
    override val scope by getOrCreateScope()
    private val testDep: TestDep by inject()

    private val viewModel: SearchViewModel by viewModel()
    private val adapter by lazy { SearchAdapter(onListItemClickListener) }
    private val onListItemClickListener by lazy {
        object : SearchAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                mainActivity?.navigateTo(
                    currentFragment = this@SearchFragmentImpl,
                    nextFragment = DetailsFragmentImpl.newInstance(
                        Converter.convertToDetailsModel(
                            data
                        )
                    )
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myScope.get<TestDep>().printSelf()
        testDep.printSelf()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.main_activity_recyclerview).adapter = adapter
        viewModel.liveData.observe(viewLifecycleOwner) {
            renderData(it)
        }
        binding.searchFab.setOnClickListener {
            mainActivity?.showSearchDialog { word ->
                viewModel.getData(word)
            }
        }
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_history -> {
                    mainActivity?.navigateTo(
                        currentFragment = this,
                        nextFragment = HistoryFragmentImpl()
                    )
                }
            }
            true
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

    override fun onDestroy() {
        myScope.close()
        super.onDestroy()
    }
}