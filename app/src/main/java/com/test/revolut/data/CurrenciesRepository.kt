package com.test.revolut.data

import androidx.annotation.CheckResult
import com.test.revolut.data.mapper.CurrencyMapper
import com.test.revolut.domain.model.CurrencyRate
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CurrenciesRepository @Inject constructor(
    private val currenciesApiService: CurrenciesApiService,
    private val currencyMapper: CurrencyMapper
) {

    @CheckResult
    fun getCurrencies(base: String): Single<List<CurrencyRate>> {
        return currenciesApiService.getLatestCurrenciesAsync(base)
            .map { currencyMapper.map(it) }
            .subscribeOn(Schedulers.io())
    }
}