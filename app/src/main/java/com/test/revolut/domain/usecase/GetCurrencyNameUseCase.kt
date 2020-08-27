package com.test.revolut.domain.usecase

import androidx.annotation.CheckResult
import com.test.revolut.data.repository.CurrenciesRepository
import com.test.revolut.data.mapper.CurrencyCode
import com.test.revolut.domain.model.CurrencyRate
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetCurrencyNameUseCase @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
) {

    fun execute(isoCode: String): String? = currenciesRepository.getCurrencyName(isoCode)
}