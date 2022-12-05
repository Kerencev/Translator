package com.kerencev.translator.presentation.details

import com.kerencev.domain.model.DetailsModel

interface DetailsFragment {
    fun getDataFromArgs(): DetailsModel?
    fun renderData(data: DetailsModel)
}