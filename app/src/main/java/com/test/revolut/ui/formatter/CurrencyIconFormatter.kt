package com.test.revolut.ui.formatter

import androidx.annotation.DrawableRes
import com.test.revolut.R
import com.test.revolut.data.mapper.CurrencyCode
import com.test.revolut.data.mapper.CurrencyCodeMapper
import com.test.revolut.domain.model.CurrencyRate
import com.test.revolut.ui.vo.CurrencyRateVo
import javax.inject.Inject

class CurrencyIconFormatter @Inject constructor() {

    @DrawableRes
    fun getImage(code: CurrencyCode): Int {
        return when (code) {
            CurrencyCode.EUR -> R.drawable.eur
            CurrencyCode.AUD -> R.drawable.aud
            CurrencyCode.BGN -> R.drawable.bgn
            CurrencyCode.BRL -> R.drawable.brl
            CurrencyCode.CAD -> R.drawable.cad
            CurrencyCode.CHF -> R.drawable.chf
            CurrencyCode.CNY -> R.drawable.cny
            CurrencyCode.CZK -> R.drawable.czk
            CurrencyCode.DKK -> R.drawable.dkk
            CurrencyCode.GBP -> R.drawable.gbp
            CurrencyCode.HKD -> R.drawable.hkd
            CurrencyCode.HRK -> R.drawable.hrk
            CurrencyCode.HUF -> R.drawable.huf
            CurrencyCode.IDR -> R.drawable.idr
            CurrencyCode.ILS -> R.drawable.ils
            CurrencyCode.INR -> R.drawable.inr
            CurrencyCode.ISK -> R.drawable.isk
            CurrencyCode.JPY -> R.drawable.jpy
            CurrencyCode.KRW -> R.drawable.krw
            CurrencyCode.MXN -> R.drawable.mxn
            CurrencyCode.MYR -> R.drawable.myr
            CurrencyCode.NOK -> R.drawable.nok
            CurrencyCode.NZD -> R.drawable.nzd
            CurrencyCode.PHP -> R.drawable.php
            CurrencyCode.PLN -> R.drawable.pln
            CurrencyCode.RON -> R.drawable.ron
            CurrencyCode.RUB -> R.drawable.rub
            CurrencyCode.SEK -> R.drawable.sek
            CurrencyCode.SGD -> R.drawable.sgd
            CurrencyCode.THB -> R.drawable.thb
            CurrencyCode.USD -> R.drawable.usd
            CurrencyCode.ZAR -> R.drawable.zar
        }
    }
}