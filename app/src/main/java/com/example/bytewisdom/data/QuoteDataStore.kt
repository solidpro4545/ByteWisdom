package com.example.bytewisdom.data


import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.ds by preferencesDataStore(name = "bytewisdom_prefs")


class QuoteDataStore(private val context: Context) {
    private object Keys { val LastDate = stringPreferencesKey("last_date"); val LastQuote = stringPreferencesKey("last_quote") }


    data class SavedQuote(val date: String?, val quote: String?)


    val saved: Flow<SavedQuote> = context.ds.data.map { p: Preferences ->
        SavedQuote(p[Keys.LastDate], p[Keys.LastQuote])
    }


    suspend fun save(dateIso: String, quote: String) {
        context.ds.edit { p ->
            p[Keys.LastDate] = dateIso
            p[Keys.LastQuote] = quote
        }
    }
}