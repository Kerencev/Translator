package com.kerencev.translator.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meanings(
    @field:SerializedName("translation") val translation: Translation?,
    @field:SerializedName("transcription") val transcription: String?,
    @field:SerializedName("imageUrl") val imageUrl: String?
) : Parcelable
