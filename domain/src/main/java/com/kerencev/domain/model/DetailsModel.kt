package com.kerencev.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailsModel(
    val id: String,
    val word: String?,
    val transcription: String?,
    val translates: String,
    val imageUrl: String?
) : Parcelable
