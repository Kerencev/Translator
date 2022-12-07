package com.kerencev.translator.utils

import com.kerencev.domain.model.DetailsModel

interface Filter<T> {
    fun getFilteredList(charSequence: CharSequence, data: List<T>): List<T>

    class HistoryFilter : Filter<DetailsModel> {

        override fun getFilteredList(
            charSequence: CharSequence,
            data: List<DetailsModel>
        ): List<DetailsModel> {
            val result = mutableListOf<DetailsModel>()
            data.forEach { detailsModel ->
                val word = detailsModel.word ?: ""
                val translate =
                    detailsModel.translates.substring(0, detailsModel.translates.indexOf('\n'))
                if (word.contains(charSequence, ignoreCase = true) ||
                    translate.contains(charSequence, ignoreCase = true)
                ) {
                    result.add(detailsModel)
                }
            }
            return result
        }
    }
}