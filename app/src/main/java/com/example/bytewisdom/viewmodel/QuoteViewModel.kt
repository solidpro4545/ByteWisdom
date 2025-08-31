package com.example.bytewisdom.viewmodel


import android.app.Application
import androidx.lifecycle.*
import com.example.bytewisdom.data.QuoteDataStore
import com.example.bytewisdom.data.QuoteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate


class QuoteViewModel(private val app: Application): AndroidViewModel(app) {
    data class UiState(val isLoading: Boolean = false, val quote: String? = null, val date: String? = null)


    private val repo = QuoteRepository()
    private val store by lazy { QuoteDataStore(app.applicationContext) }


    private val _quoteState = MutableLiveData(UiState())
    val quoteState: LiveData<UiState> = _quoteState


    //init { loadTodayQuote() }

    // No auto-load: start empty
    init { /* intentionally empty */ }

    fun loadTodayQuote() {
        viewModelScope.launch {
            _quoteState.value = _quoteState.value?.copy(isLoading = true)

            val today = LocalDate.now()
            val saved = store.saved.first() // ← one-shot read
            val savedDate = saved.date
            val savedQuote = saved.quote

            if (savedDate == today.toString() && savedQuote != null) {
                _quoteState.postValue(UiState(isLoading = false, quote = savedQuote, date = savedDate))
            } else {
                val q = repo.quoteOfDay(today)
                store.save(today.toString(), q)
                _quoteState.postValue(UiState(isLoading = false, quote = q, date = today.toString()))
            }
        }
    }


    fun forceNewQuote() {
        viewModelScope.launch {
            val q = repo.randomQuote()
            val today = LocalDate.now().toString()
            store.save(today, q)
            _quoteState.postValue(UiState(false, q, today))
        }
    }

    fun clearQuote() {
        _quoteState.value = UiState() // back to empty, no quote
    }



    companion object {
        fun factory(app: Application?) = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return QuoteViewModel(app ?: throw IllegalStateException("App required")) as T
            }
        }
    }
}