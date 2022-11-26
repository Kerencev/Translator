package com.kerencev.translator.data.dto

import com.google.gson.annotations.SerializedName

data class Translation(
    @field:SerializedName("text") val translation: String?
)
