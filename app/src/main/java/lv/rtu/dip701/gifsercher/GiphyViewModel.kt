package lv.rtu.dip701.gifsercher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GiphyViewModel(private val repository: GiphyRepository = GiphyRepository()) : ViewModel() {
    private val _gifs = MutableLiveData<List<Gif>>()
    val gifs: LiveData<List<Gif>> = _gifs

    fun searchGifs(query: String) {
        viewModelScope.launch {
            val gifList = repository.searchGifs(query)
            _gifs.postValue(gifList)
        }
    }
}
