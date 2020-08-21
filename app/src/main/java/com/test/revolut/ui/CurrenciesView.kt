package com.test.revolut.ui

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle


interface CurrenciesView : MvpView {

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = TAG)
    fun display(text: String)

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = TAG)
    fun showError(message: String)

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = TAG)
    fun showConnectionError()

    companion object {
        private const val TAG = "tag"
    }
}