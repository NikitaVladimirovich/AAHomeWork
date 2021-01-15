package com.aacademy.homework.foundations

import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class DebouncedQueryTextListener(
    private val coroutineScope: CoroutineScope,
    private val debouncePeriod: Long,
    private val onQueryTextChangeListener: (String?) -> Unit
) : SearchView.OnQueryTextListener {

    private var searchJob: Job? = null

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            newText?.let {
                delay(debouncePeriod)
                onQueryTextChangeListener(newText)
            }
        }
        return false
    }
}
