package lv.rtu.dip701.gifsercher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GiphyViewModelFactory(private val repository: GiphyRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GiphyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GiphyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
