package com.test.revolut.ui

import com.test.revolut.data.CurrenciesRepository
import com.test.revolut.domain.model.CurrencyRate
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
    private val currenciesRepository: CurrenciesRepository
) : MvpPresenter<CurrenciesView>() {

    private val disposableContainer = CompositeDisposable()

    private val mainCurrencyChangedSubject = PublishSubject.create<Any>().toSerialized()

    private var mainCurrencyCode = "EUR" //todo save in pref

    override fun onFirstViewAttach() {
        Observable.merge(
            Observable.interval(REFRESH_INTERVAL_SEC, TimeUnit.SECONDS),
            mainCurrencyChangedSubject.hide()
        )
            .flatMapSingle { currenciesRepository.getCurrencies(mainCurrencyCode) }
            .retry(1) //todo
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<List<CurrencyRate>> {
                    override fun onNext(t: List<CurrencyRate>) {
                        viewState.display(t.toString())
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
            )
    }

    fun onChangeMainCurrency(currencyCode: String) {
        mainCurrencyCode = currencyCode
    }

    override fun onDestroy() {
        disposableContainer.clear()
    }

    companion object {
        private const val REFRESH_INTERVAL_SEC = 30L //todo 1
    }
}