package com.test.revolut.data

import androidx.annotation.CheckResult
import com.test.revolut.data.dto.CurrenciesDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesApiService {

    @CheckResult
    @GET("api/android/latest")
    fun getLatestCurrenciesAsync(
        @Query("base") base: String
    ) : Single<CurrenciesDto>
}