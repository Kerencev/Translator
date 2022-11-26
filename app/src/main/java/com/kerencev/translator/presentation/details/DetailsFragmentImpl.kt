package com.kerencev.translator.presentation.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.kerencev.translator.databinding.FragmentDetailsBinding
import com.kerencev.translator.presentation.base.BaseFragment
import com.kerencev.translator.presentation.base.loadImage

class DetailsFragmentImpl :
    BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate),
    DetailsFragment {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromArgs()?.let { renderData(it) }
    }

    override fun getDataFromArgs(): DetailsModel? {
        return arguments?.getParcelable(ARG_KEY)
    }

    @SuppressLint("SetTextI18n")
    override fun renderData(data: DetailsModel) {
        with(binding) {
            ivWord.loadImage(data.imageUrl)
            tvText.text = data.word
            tvTranscription.text = data.transcription
            tvTranslation.text = data.translates
        }
    }

    companion object {

        private const val ARG_KEY = "ARG_KEY"

        fun newInstance(data: DetailsModel): DetailsFragmentImpl {
            return DetailsFragmentImpl().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_KEY, data)
                }
            }
        }
    }
}