package com.test.revolut.domain.usecase

import androidx.annotation.CheckResult
import com.test.revolut.data.repository.CurrenciesRepository
import com.test.revolut.data.mapper.CurrencyCode
import com.test.revolut.domain.model.CurrencyRate
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
) {

    @CheckResult
    fun execute(mainCurrencyCode: CurrencyCode): Single<List<CurrencyRate>> {
        return currenciesRepository.getCurrencies(mainCurrencyCode)
    }
}