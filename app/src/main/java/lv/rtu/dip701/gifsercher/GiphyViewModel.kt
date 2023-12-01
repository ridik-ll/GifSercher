package lv.rtu.dip701.gifsercher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GiphyViewModel(private val repository: GiphyRepository = GiphyRepository()) : ViewModel() {
    private val _gifs = MutableLiveData<List<Gif>>()
    val gifs: LiveData<List<Gif>> = _gifs

    private var currentPage = 0
    private var isLoadingMore = false
    private var currentQuery: String? = null

    fun searchGifs(query: String) {
        currentQuery = query
        currentPage = 0
        viewModelScope.launch {
            val gifList = repository.searchGifs(query, currentPage)
            _gifs.postValue(gifList)
        }
    }

    fun loadMoreGifs() {
        if (isLoadingMore || currentQuery == null) {
            return
        }

        isLoadingMore = true
        currentPage++

        viewModelScope.launch {
            val newGifs = repository.searchGifs(currentQuery!!, currentPage)
            val currentGifs = _gifs.value ?: emptyList()
            _gifs.postValue(currentGifs + newGifs)
            isLoadingMore = false
        }
    }
}
