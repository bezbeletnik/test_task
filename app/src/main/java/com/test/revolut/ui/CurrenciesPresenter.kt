package com.test.revolut.ui

import com.test.revolut.data.mapper.CurrencyCode
import com.test.revolut.domain.model.CurrencyRate
import com.test.revolut.domain.usecase.GetCurrenciesUseCase
import com.test.revolut.ui.formatter.CurrencyRateFormatter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import moxy.MvpPresenter
import java.net.ConnectException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrenciesPresenter @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val formatter: CurrencyRateFormatter
) : MvpPresenter<CurrenciesView>() {

    private val disposableContainer = CompositeDisposable()

    private val mainCurrencyChangedSubject = PublishSubject.create<Any>().toSerialized()

    private var mainCurrencyCode = CurrencyCode.EUR //todo: EUR by default, otherwise from pref

    override fun onFirstViewAttach() {
        Observable.merge(
            Observable.interval(REFRESH_INTERVAL_SEC, TimeUnit.SECONDS).startWithItem(0),
            mainCurrencyChangedSubject.hide()
        )
            .flatMapSingle { getCurrenciesUseCase.execute(mainCurrencyCode) }
            .retry(1) //todo
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(CurrenciesObserver())
    }

    fun onChangeMainCurrency(currencyCode: CurrencyCode) {
        //todo save in pref
        mainCurrencyCode = currencyCode
    }

    override fun onDestroy() {
        disposableContainer.clear()
    }

    private inner class CurrenciesObserver : Observer<List<CurrencyRate>> {
        override fun onNext(rates: List<CurrencyRate>) {
            viewState.showResult(formatter.format(rates))
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