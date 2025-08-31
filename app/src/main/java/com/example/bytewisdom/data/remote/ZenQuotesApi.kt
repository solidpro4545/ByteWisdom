package com.example.bytewisdom.data.remote

import retrofit2.http.GET

data class ZenQuoteDto(
    val q: String,   // quote
    val a: String,   // author
    val h: String? = null
)

interface ZenQuotesApi {
    @GET("api/today")  suspend fun today(): List<ZenQuoteDto>
    @GET("api/random") suspend fun random(): List<ZenQuoteDto>
}