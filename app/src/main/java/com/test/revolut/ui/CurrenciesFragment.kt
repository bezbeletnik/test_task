package com.test.revolut.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.test.revolut.R
import com.test.revolut.ui.vo.CurrencyRateVo
import com.test.revolut.ui.vo.MainCurrencyVo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.currencies_fragment.*
import moxy.MvpAppCompatFragment
import javax.inject.Inject
import javax.inject.Provider
import moxy.ktx.moxyPresenter

@AndroidEntryPoint
class CurrenciesFragment : MvpAppCompatFragment(), CurrenciesView {

    @Inject
    lateinit var presenterProvider: Provider<CurrenciesPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    private val itemAdapter = ItemAdapter<CurrencyRateItem>()

    private val fastAdapter = FastAdapter.with(itemAdapter)

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
//        mainCurrencyVo
        if (rateVos.isEmpty()) {
            emptyResultTextView.visibility = View.VISIBLE
            itemAdapter.clear()
        } else {
            emptyResultTextView.visibility = View.GONE
            val items = rateVos.map { CurrencyRateItem(it) }
            itemAdapter.setNewList(items)
        }
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
