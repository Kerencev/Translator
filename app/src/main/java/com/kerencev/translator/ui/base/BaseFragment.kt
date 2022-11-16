package com.kerencev.translator.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kerencev.translator.ui.translation.TranslationPresenter
import com.kerencev.translator.ui.translation.TranslationState
import com.kerencev.translator.ui.translation.TranslationView

abstract class BaseFragment<T : TranslationState> : Fragment(), TranslationView {

    protected lateinit var presenter: TranslationPresenter<T, TranslationView>

    protected abstract fun createPresenter(): TranslationPresenter<T, TranslationView>

    abstract override fun renderData(appState: TranslationState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }
}
