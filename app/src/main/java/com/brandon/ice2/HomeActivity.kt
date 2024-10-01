package com.brandon.ice2

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.brandon.ice2.databinding.ActivityHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConvert.setOnClickListener{fetchCurrencyList()}
    }


    private fun fetchCurrencyList() {
        val apiKey = "6da42d5b7c0c5595c1b942c935e4bfc91854e264"
        val fromCurrency = "ZAR"
        val toCurrency = "USD"
        try {
            val amount = binding.Amount.text.toString().toDouble()

            APIClient.instance.convertCurrency(apiKey, fromCurrency, toCurrency, amount).enqueue(object : Callback<CurrencyConversionResponse> {
                override fun onResponse(call: Call<CurrencyConversionResponse>, response: Response<CurrencyConversionResponse>) {
                    if (response.isSuccessful) {
                        val conversionResult = response.body()
                        if (conversionResult != null) {
                            Log.d("ConversionResult", conversionResult.toString())

                            // Accessing the conversion details dynamically
                            val rateDetail = conversionResult.rates[toCurrency] // Fetch rate details for the specific currency
                            if (rateDetail != null) {
                                val convertedAmount = rateDetail.rate_for_amount
                                val currencyName = rateDetail.currency_name

                                // Displaying the result in the TextView
                                binding.convertedAmount.text = "Converted: $convertedAmount $currencyName"
                            } else {
                                Log.e("Error", "Currency not found: $toCurrency")
                            }
                        } else {
                            Log.e("Error", "Response body is null")
                        }
                    } else {
                        Log.e("Error", "Response Code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<CurrencyConversionResponse>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }
            })
        }catch(e: Exception) {
            binding.convertedAmount.text = "Invalid Amount"
        }


    }
}