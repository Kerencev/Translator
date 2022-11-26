package com.kerencev.translator.data.dto

import com.google.gson.annotations.SerializedName

data class DataModel(
    @field:SerializedName("id") val id: String?,
    @field:SerializedName("text") val text: String?,
    @field:SerializedName("meanings") val meanings: List<Meanings>?
)