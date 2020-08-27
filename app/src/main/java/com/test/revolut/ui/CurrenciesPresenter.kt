package com.test.revolut.ui

import com.test.revolut.data.mapper.CurrencyCode
import com.test.revolut.domain.model.CurrencyRate
import com.test.revolut.domain.usecase.GetCurrenciesUseCase
import com.test.revolut.ui.formatter.CurrencyRateFormatter
import com.test.revolut.ui.formatter.MainCurrencyFormatter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import moxy.MvpPresenter
import java.net.ConnectException
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class CurrenciesPresenter @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val currencyRateFormatter: CurrencyRateFormatter,
    private val mainCurrencyFormatter: MainCurrencyFormatter
) : MvpPresenter<CurrenciesView>() {

    private val disposableContainer = CompositeDisposable()

    private val mainCurrencyChangedSubject = PublishSubject.create<Any>().toSerialized()

    private var mainCurrencyCode = CurrencyCode.EUR
    private var mainCurrencyAmount = 10
    private val revision = AtomicInteger()

    override fun onFirstViewAttach() {
        Observable.merge(
            Observable.interval(REFRESH_INTERVAL_SEC, TimeUnit.SECONDS).startWithItem(0),
            mainCurrencyChangedSubject.hide()
        )
            .switchMapSingle {
                val requestedRevision = revision.get()
                getCurrenciesUseCase.execute(mainCurrencyCode)
                    .map { requestedRevision to it }
            }
            .retry(1) //todo
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(CurrenciesObserver())
    }

    fun onChangeMainCurrency(currencyCode: CurrencyCode) {
        revision.incrementAndGet()
        mainCurrencyCode = currencyCode
    }

    fun onChangeAmount(amount: Int) {
        mainCurrencyAmount = amount
    }

    override fun onDestroy() {
        disposableContainer.clear()
    }

    private inner class CurrenciesObserver : Observer<Pair<Int,List<CurrencyRate>>> {
        override fun onNext(pair: Pair<Int, List<CurrencyRate>>) {
            val (requestedRevision, rates) = pair
            if (requestedRevision == revision.get()) {
                val mainCurrencyVo = mainCurrencyFormatter.format(mainCurrencyCode, mainCurrencyAmount)
                val rateVos = currencyRateFormatter.format(rates)
                viewState.showResult(mainCurrencyVo, rateVos)
            }
        }

        override fun onError(e: Throwable) {
            if (e is ConnectException) {
                viewState.showConnectionError()
            } else {
                viewState.showError(e.message.toString())
            }
        }

        override fun onComplete() {
            //no-op
        }

        override fun onSubscribe(d: Disposable) {
            disposableContainer.add(d)
        }
    }

    companion object {
        private const val REFRESH_INTERVAL_SEC = 30L //todo 1
    }
}