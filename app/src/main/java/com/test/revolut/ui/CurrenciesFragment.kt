package com.test.revolut.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import com.test.revolut.R
import com.test.revolut.ui.item.CurrencyRateItem
import com.test.revolut.ui.item.MainCurrencyItem
import com.test.revolut.ui.item.TitleItem
import com.test.revolut.ui.vo.CurrencyRateVo
import com.test.revolut.ui.vo.MainCurrencyVo
import com.test.revolut.utils.downcast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.currencies_fragment.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class CurrenciesFragment : MvpAppCompatFragment(), CurrenciesView {

    @Inject
    lateinit var presenterProvider: Provider<CurrenciesPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    private val titleItemAdapter = ItemAdapter<TitleItem>()
    private val mainItemAdapter = ItemAdapter<MainCurrencyItem>()
    private val ratesAdapter = ItemAdapter<CurrencyRateItem>()
    private val fastAdapter = FastAdapter<GenericItem>()

    init {
        fastAdapter.addAdapters(
            listOf(
                titleItemAdapter.downcast(),
                mainItemAdapter.downcast(),
                ratesAdapter.downcast()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.currencies_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currenciesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        currenciesRecyclerView.itemAnimator = null

        currenciesRecyclerView.adapter = fastAdapter
        fastAdapter.onClickListener = { _, _, item, _ ->
            if (item is CurrencyRateItem) {
                presenter.onMainCurrencyChanged(item.vo.currencyCode, item.vo.rateValue)
            }
            true
        }
        reloadButton.setOnClickListener {
            presenter.onReloadClicked()
        }
        titleItemAdapter.set(listOf(TitleItem()))
        showState(State.LOADING)
    }

    override fun showResult(mainCurrencyVo: MainCurrencyVo, rateVos: List<CurrencyRateVo>) {
        if (rateVos.isEmpty()) {
            showState(State.EMPTY_RESULT)
        } else {
            showState(State.CONTENT_SHOWN)

            val mainCurrencyItem = MainCurrencyItem(mainCurrencyVo) {
                presenter.onAmountChanged(it)
            }
            FastAdapterDiffUtil[mainItemAdapter] = listOf(mainCurrencyItem)

            val items = rateVos.map { CurrencyRateItem(it) }
            ratesAdapter.setNewList(items)
        }
    }

    private fun showState(state: State, errorText: String? = null) {
        progressBarView.isVisible = state == State.LOADING

        emptyResultTextView.isVisible = state == State.EMPTY_RESULT || state == State.ERROR
        reloadButton.isVisible = state == State.EMPTY_RESULT || state == State.ERROR
        emptyResultTextView.text = errorText

        currenciesRecyclerView.isVisible = state == State.CONTENT_SHOWN
    }

    private enum class State {
        CONTENT_SHOWN, LOADING, EMPTY_RESULT, ERROR
    }

    override fun showError(message: String) {
        showState(State.ERROR, message)
    }

    override fun showConnectionError() {
        showState(State.ERROR, getString(R.string.connection_error))
    }

    override fun scrollToTop() {
        currenciesRecyclerView.smoothScrollToPosition(0)
    }

    companion object {
        fun newInstance() = CurrenciesFragment()
    }
}
