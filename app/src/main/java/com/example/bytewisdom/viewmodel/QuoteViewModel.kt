package com.example.bytewisdom.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.bytewisdom.data.QuoteDataStore
import com.example.bytewisdom.data.QuoteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate

class QuoteViewModel(private val app: Application) : AndroidViewModel(app) {

    data class UiState(
        val isLoading: Boolean = false,
        val localQuote: String? = null,
        val date: String? = null,
        val zenQuoteToday: String? = null,   // from /api/today
        val zenQuoteRandom: String? = null,   // from /api/random
        val rateLimited: Boolean = false
    )

    private val repo = QuoteRepository()
    private val store by lazy { QuoteDataStore(app.applicationContext) }

    private val _quoteState = MutableLiveData(UiState())
    val quoteState: LiveData<UiState> = _quoteState

    // No auto-load: start empty
    init { /* intentionally empty */ }

    fun loadTodayQuote() {
        viewModelScope.launch {
            _quoteState.value = _quoteState.value?.copy(isLoading = true, rateLimited = false)

            val todayStr = LocalDate.now().toString()

            val saved = store.saved.first()
            val local = if (saved.date == todayStr && saved.quote != null) {
                saved.quote
            } else {
                val q = repo.quoteOfDay(LocalDate.parse(todayStr))
                store.save(todayStr, q)
                q
            }

            var zenToday: String? = null
            var limited = false
            try {
                zenToday = repo.zenQuoteOfDay()
            } catch (e: HttpException) {
                if (e.code() == 429) limited = true
            }

            _quoteState.postValue(
                (_quoteState.value ?: UiState()).copy(
                    isLoading = false,
                    localQuote = local,
                    date = todayStr,
                    zenQuoteToday = zenToday ?: _quoteState.value?.zenQuoteToday,
                    rateLimited = limited
                )
            )
        }
    }

    fun forceNewQuote() {
        viewModelScope.launch {
            val todayStr = LocalDate.now().toString()
            val local = repo.randomQuote()
            store.save(todayStr, local)

            _quoteState.postValue(
                (_quoteState.value ?: UiState()).copy(
                    isLoading = false,
                    localQuote = local,
                    date = todayStr,
                    rateLimited = false
                )
            )
        }
    }

    fun fetchZenOnly() {
        viewModelScope.launch {
            var zenToday: String? = null
            var limited = false
            try {
                zenToday = repo.zenQuoteOfDay()
            } catch (e: HttpException) {
                if (e.code() == 429) limited = true
            }

            _quoteState.postValue(
                (_quoteState.value ?: UiState()).copy(
                    isLoading = false,
                    zenQuoteToday = zenToday ?: _quoteState.value?.zenQuoteToday,
                    rateLimited = limited
                )
            )
        }
    }

    fun fetchZenRandom() {
        viewModelScope.launch {
            var zenRandom: String? = null
            var limited = false
            try {
                zenRandom = repo.zenRandomQuote()
            } catch (e: HttpException) {
                if (e.code() == 429) limited = true
            }

            _quoteState.postValue(
                (_quoteState.value ?: UiState()).copy(
                    isLoading = false,
                    zenQuoteRandom = zenRandom ?: _quoteState.value?.zenQuoteRandom,
                    rateLimited = limited
                )
            )
        }
    }

    fun clearQuote() {
        _quoteState.value = UiState() // back to empty, no quote
    }

    companion object {
        fun factory(app: Application?) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return QuoteViewModel(app ?: throw IllegalStateException("App required")) as T
            }
        }
    }
}
