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
                val translate = if (detailsModel.translates.contains("\n")) {
                    detailsModel.translates.substring(0, detailsModel.translates.indexOf('\n'))
                } else {
                    detailsModel.translates
                }
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