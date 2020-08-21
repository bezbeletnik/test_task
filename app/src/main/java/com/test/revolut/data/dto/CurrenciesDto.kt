package com.test.revolut.data.dto

import com.google.gson.annotations.SerializedName

data class CurrenciesDto (
    @SerializedName("baseCurrency") val baseCurrency: String?,
    @SerializedName("rates") val rates: Map<String, Float>?
) {
    companion object {
        private const val serialVersionUID = 1L
    }
}