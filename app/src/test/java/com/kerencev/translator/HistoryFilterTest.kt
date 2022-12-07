package com.kerencev.translator

import com.kerencev.domain.model.DetailsModel
import com.kerencev.translator.utils.Filter
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HistoryFilterTest {

    private lateinit var filter: Filter<DetailsModel>

    @Before
    fun setUp() {
        filter = Filter.HistoryFilter()
    }

    @Test
    fun empty_list_search_input_is_empty() {
        val data = emptyList<DetailsModel>()
        val expected = emptyList<DetailsModel>()
        val actual = filter.getFilteredList(data = data, charSequence = "")
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun empty_list_search_input_is_not_empty() {
        val data = emptyList<DetailsModel>()
        val expected = emptyList<DetailsModel>()
        val actual = filter.getFilteredList(data = data, charSequence = "word")
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun list_with_same_items_search_input_is_empty() {
        val data = mutableListOf<DetailsModel>()
        val model = DetailsModel(
            id = "0",
            word = "word",
            transcription = "transcription",
            translates = "translates",
            imageUrl = null
        )
        repeat(5) {
            data.add(model)
        }
        val actual = filter.getFilteredList(data = data, charSequence = "")
        Assert.assertEquals(data, actual)
    }

    @Test
    fun list_with_same_items_word_contains_search_input() {
        val data = mutableListOf<DetailsModel>()
        val model = DetailsModel(
            id = "0",
            word = "word",
            transcription = "transcription",
            translates = "translates",
            imageUrl = null
        )
        repeat(5) {
            data.add(model)
        }
        val actual = filter.getFilteredList(data = data, charSequence = "wo")
        Assert.assertEquals(data, actual)
    }

    @Test
    fun list_with_same_items_translates_contains_search_input() {
        val data = mutableListOf<DetailsModel>()
        val model = DetailsModel(
            id = "0",
            word = "word",
            transcription = "transcription",
            translates = "translates",
            imageUrl = null
        )
        repeat(5) {
            data.add(model)
        }
        val actual = filter.getFilteredList(data = data, charSequence = "late")
        Assert.assertEquals(data, actual)
    }

    @Test
    fun list_with_same_items_word_not_contains_search_input() {
        val data = mutableListOf<DetailsModel>()
        val model = DetailsModel(
            id = "0",
            word = "word",
            transcription = "transcription",
            translates = "translates",
            imageUrl = null
        )
        repeat(5) {
            data.add(model)
        }
        val actual = filter.getFilteredList(data = data, charSequence = "йц")
        Assert.assertEquals(emptyList<DetailsModel>(), actual)
    }
}