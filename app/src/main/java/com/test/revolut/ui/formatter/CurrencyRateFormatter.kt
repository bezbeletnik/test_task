package com.test.revolut.ui.formatter

import com.test.revolut.data.mapper.CurrencyCodeMapper
import com.test.revolut.domain.model.CurrencyRate
import com.test.revolut.ui.vo.CurrencyRateVo
import javax.inject.Inject

class CurrencyRateFormatter @Inject constructor(
    private val codeMapper: CurrencyCodeMapper
) {

    fun format(currencyRates: List<CurrencyRate>): List<CurrencyRateVo> {
        return currencyRates.map {
            val code = codeMapper.mapToString(it.currency)
            CurrencyRateVo(
                imageUrl = formatImageUrl(code),
                currencyShortName = code,
                currencyFullName = code,
                rate = it.rate.toString()
            )
        }
    }

    private fun formatImageUrl(code: String): String {
        return "$CURRENCY_IMG_URL$code"
    }

    companion object {
        private const val CURRENCY_IMG_URL = ""
    }
}