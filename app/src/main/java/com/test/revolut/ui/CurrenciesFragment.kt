package com.test.revolut.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.test.revolut.R
import com.test.revolut.ui.vo.CurrencyRateVo
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.currencies_fragment, container, false)
    }

    override fun showResult(vos: List<CurrencyRateVo>) {
        if (vos.isEmpty()) {

        } else {

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
