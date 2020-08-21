package com.test.revolut.data

import com.test.revolut.data.dto.CurrenciesDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesApiService {

    @GET("api/android/latest")
    fun getLatestCurrenciesAsync(
        @Query("base") base: String
    ) : Single<CurrenciesDto>
}