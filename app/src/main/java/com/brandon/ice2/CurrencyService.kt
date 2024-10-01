package com.brandon.ice2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class CurrencyConversionResponse(
    val status: String,                  // Status of the request
    val updated_date: String,            // Date when the currency rate was updated
    val base_currency_code: String,      // Base currency code (e.g., EUR)
    val base_currency_name: String,      // Base currency name (e.g., Euro)
    val amount: Double,                  // The amount that needs to be converted
    val rates: Map<String, RateDetail>   // Object containing exchange rates
)

data class RateDetail(
    val currency_name: String,           // Name of the required currency (e.g., Pound Sterling)
    val rate: String,                    // The conversion rate value for amount = 1
    val rate_for_amount: String           // The conversion rate value for the requested amount
)


interface CurrencyService {
    @GET("currency/convert")
    fun convertCurrency(
        @Query("api_key") apiKey: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double,
        @Query("format") format: String = "json"
    ): Call<CurrencyConversionResponse>
}