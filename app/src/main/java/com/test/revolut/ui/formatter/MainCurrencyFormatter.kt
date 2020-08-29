package com.test.revolut.ui.formatter

import com.test.revolut.data.mapper.CurrencyCode
import com.test.revolut.data.mapper.CurrencyCodeMapper
import com.test.revolut.domain.usecase.GetCurrencyNameUseCase
import com.test.revolut.ui.vo.MainCurrencyVo
import timber.log.Timber
import java.text.DecimalFormat
import javax.inject.Inject

class MainCurrencyFormatter @Inject constructor(
    private val codeMapper: CurrencyCodeMapper,
    private val iconFormatter: CurrencyIconFormatter,
    private val getCurrencyNameUseCase: GetCurrencyNameUseCase
) {

    fun format(
        mainCurrencyCode: CurrencyCode,
        mainCurrencyAmount: Double
    ): MainCurrencyVo {
        val isoCode = codeMapper.mapToIsoCode(mainCurrencyCode)
        return MainCurrencyVo(
            image = iconFormatter.getImage(mainCurrencyCode),
            currencyShortName = isoCode,
            currencyFullName = getCurrencyNameUseCase.execute(isoCode) ?: handleUnkownName(isoCode),
            amount = DecimalFormat("0.#").format(mainCurrencyAmount)
        )

    }

    private fun handleUnkownName(isoCode: String): String {
        Timber.e("full name not found for $isoCode")
        return ""
    }
}