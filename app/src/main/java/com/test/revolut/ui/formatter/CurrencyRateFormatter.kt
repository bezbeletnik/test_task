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
            CurrencyRateVo(
                currencyCode = codeMapper.mapToString(it.currency),
                currencyFullName = codeMapper.mapToString(it.currency),
                rate = it.rate.toString()
            )
        }
    }
}