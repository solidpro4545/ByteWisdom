package com.example.bytewisdom.data

import android.util.Log
import com.example.bytewisdom.data.remote.NetworkModule
import retrofit2.HttpException
import java.time.LocalDate
import kotlin.random.Random
private const val TAG = "QuoteRepo"
class QuoteRepository {

    private val quotes = listOf(
        "Growth starts with perspective.",
        "Small steps compound into big change.",
        "Discipline beats motivation—show up anyway.",
        "You don’t need more time, just a first minute.",
        "Be the person your future self thanks.",
        "Courage is doing it while afraid.",
        "You become what you repeat."
    )

    /** Deterministic quote of the day based on date (offline fallback). */
    fun quoteOfDay(date: LocalDate = LocalDate.now()): String {
        val index = (date.toEpochDay().toInt() and Int.MAX_VALUE) % quotes.size
        return quotes[index]
    }

    /** Random quote for manual refresh (offline fallback). */
    fun randomQuote(): String = quotes[Random.nextInt(quotes.size)]

    /** Try ZenQuotes “today”; return null on any network/parse error. */


    suspend fun zenQuoteOfDay(): String? {
        return try {
            val today = NetworkModule.api.today()
            val list = if (today.isNotEmpty()) today else NetworkModule.api.random()
            val dto = list.firstOrNull()
            val q = dto?.q?.trim().orEmpty()
            val a = dto?.a?.trim().orEmpty()
            when {
                q.isBlank() -> null
                a.isBlank() -> q
                else -> "$q — $a"
            }
        } catch (e: HttpException) {
            if (e.code() == 429) throw e else null        // 👈 propagate rate limit
        } catch (_: Exception) {
            null
        }
    }

    suspend fun zenRandomQuote(): String? {
        return try {
            val dto = NetworkModule.api.random().firstOrNull()
            val q = dto?.q?.trim().orEmpty()
            val a = dto?.a?.trim().orEmpty()
            when {
                q.isBlank() -> null
                a.isBlank() -> q
                else -> "$q — $a"
            }
        } catch (e: HttpException) {
            if (e.code() == 429) throw e else null        // 👈 propagate rate limit
        } catch (_: Exception) {
            null
        }
    }

}
