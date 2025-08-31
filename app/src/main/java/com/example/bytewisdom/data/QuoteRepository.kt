package com.example.bytewisdom.data


import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import kotlin.random.Random


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


    /** Deterministic quote of the day based on date. */
    @RequiresApi(Build.VERSION_CODES.O)
    fun quoteOfDay(date: LocalDate = LocalDate.now()): String {
        val index = (date.toEpochDay().toInt() and Int.MAX_VALUE) % quotes.size
        return quotes[index]
    }


    /** Random quote for manual refresh. */
    fun randomQuote(): String = quotes[Random.nextInt(quotes.size)]
}