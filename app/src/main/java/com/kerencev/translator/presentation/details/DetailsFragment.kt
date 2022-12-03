package com.kerencev.translator.presentation.details

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface DetailsFragment {
    fun getDataFromArgs(): DetailsModel?
    fun renderData(data: DetailsModel)
}

@Parcelize
data class DetailsModel(
    val id: String,
    val word: String?,
    val transcription: String?,
    val translates: String,
    val imageUrl: String?
) : Parcelable