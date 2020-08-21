package com.test.revolut.data

import androidx.annotation.CheckResult
import com.test.revolut.data.mapper.CurrencyCode
import com.test.revolut.data.mapper.CurrencyCodeMapper
import com.test.revolut.data.mapper.CurrencyMapper
import com.test.revolut.domain.model.CurrencyRate
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CurrenciesRepository @Inject constructor(
    private val currenciesApiService: CurrenciesApiService,
    private val currencyMapper: CurrencyMapper,
    private val currencyCodeMapper: CurrencyCodeMapper
) {

    @CheckResult
    fun getCurrencies(base: CurrencyCode): Single<List<CurrencyRate>> {
        return Single.fromCallable{ currencyCodeMapper.mapToString(base) }
            .flatMap { currenciesApiService.getLatestCurrenciesAsync(it) }
            .map { currencyMapper.map(it) }
            .subscribeOn(Schedulers.io())
    }
}