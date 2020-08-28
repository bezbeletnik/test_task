package com.test.revolut.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.test.revolut.R
import com.test.revolut.ui.item.CurrencyRateItem
import com.test.revolut.ui.item.MainCurrencyItem
import com.test.revolut.ui.vo.CurrencyRateVo
import com.test.revolut.ui.vo.MainCurrencyVo
import com.test.revolut.utils.downcast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.currencies_fragment.currenciesRecyclerView
import kotlinx.android.synthetic.main.currencies_fragment.emptyResultTextView
import moxy.MvpAppCompatFragment
import javax.inject.Inject
import javax.inject.Provider
import moxy.ktx.moxyPresenter

@AndroidEntryPoint
class CurrenciesFragment : MvpAppCompatFragment(), CurrenciesView {

    @Inject
    lateinit var presenterProvider: Provider<CurrenciesPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    private val ratesAdapter = ItemAdapter<CurrencyRateItem>()
    private val mainItemAdapter = ItemAdapter<MainCurrencyItem>()

    private val fastAdapter = FastAdapter<GenericItem>()

    init {
        fastAdapter.addAdapters(listOf(mainItemAdapter.downcast(), ratesAdapter.downcast()))
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
        currenciesRecyclerView.adapter = fastAdapter
    }

    override fun showResult(mainCurrencyVo: MainCurrencyVo, rateVos: List<CurrencyRateVo>) {
        if (rateVos.isEmpty()) {
            emptyResultTextView.visibility = View.VISIBLE
            ratesAdapter.clear()
        } else {
            emptyResultTextView.visibility = View.GONE
            val items = rateVos.map {
                CurrencyRateItem(
                    it
                )
            }
            ratesAdapter.set(items)
        }
        mainItemAdapter.set(listOf(MainCurrencyItem(mainCurrencyVo)))
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun showConnectionError() {
        Toast.makeText(requireContext(), R.string.connection_error, Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance() = CurrenciesFragment()
    }
}
