package com.test.revolut.ui.formatter

import android.util.Log
import com.test.revolut.data.mapper.CurrencyCodeMapper
import com.test.revolut.domain.model.CurrencyRate
import com.test.revolut.domain.usecase.GetCurrencyNameUseCase
import com.test.revolut.ui.vo.CurrencyRateVo
import timber.log.Timber
import javax.inject.Inject

class CurrencyRateFormatter @Inject constructor(
    private val codeMapper: CurrencyCodeMapper,
    private val iconFormatter: CurrencyIconFormatter,
    private val getCurrencyNameUseCase: GetCurrencyNameUseCase
) {

    fun format(currencyRates: List<CurrencyRate>): List<CurrencyRateVo> {
        return currencyRates.map {
            val isoCode = codeMapper.mapToIsoCode(it.currency)
            CurrencyRateVo(
                image = iconFormatter.getImage(it.currency),
                currencyShortName = isoCode,
                currencyFullName = getCurrencyNameUseCase.execute(isoCode) ?: handleUnkownName(isoCode),
                rate = it.rate.toString()
            )
        }
    }

    private fun handleUnkownName(isoCode: String): String {
        Timber.e("full name not found for $isoCode")
        return ""
    }
}