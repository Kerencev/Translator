package com.kerencev.translator.data

import com.google.gson.annotations.SerializedName

data class DataModel(
    @field:SerializedName("text") val text: String?,
    @field:SerializedName("meanings") val meanings: List<Meanings>?
)

data class Meanings(
    @field:SerializedName("translation") val translation: Translation?,
    @field:SerializedName("imageUrl") val imageUrl: String?
)

data class Translation(@field:SerializedName("text") val translation: String?)