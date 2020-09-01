package com.test.revolut.ui

import com.test.revolut.ui.vo.CurrencyRateVo
import com.test.revolut.ui.vo.MainCurrencyVo
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType


interface CurrenciesView : MvpView {

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = TAG)
    fun showResult(mainCurrencyVo: MainCurrencyVo, rateVos: List<CurrencyRateVo>)

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = TAG)
    fun showError(message: String)

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = TAG)
    fun showConnectionError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun scrollToTop()

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = LOCK_TAG)
    fun showLoading()

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = LOCK_TAG)
    fun hideLoading()

    companion object {
        private const val TAG = "tag"
        private const val LOCK_TAG = "loading_tag"
    }
}