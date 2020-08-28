package com.test.revolut.ui.item

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.test.revolut.R
import com.test.revolut.ui.vo.CurrencyRateVo
import com.test.revolut.ui.vo.MainCurrencyVo

class MainCurrencyItem(
    private val vo: MainCurrencyVo
) : AbstractItem<MainCurrencyItem.ViewHolder>() {

    override val type = R.id.main_currency_item_id

    override val layoutRes = R.layout.currency_rate_item

    override fun getViewHolder(v: View) =
        ViewHolder(v)

    override var identifier: Long = vo.hashCode().toLong()

    class ViewHolder(view: View) : FastAdapter.ViewHolder<MainCurrencyItem>(view) {

        private val currencyImageView: ImageView = view.findViewById(R.id.currencyImageView)
        private val currencyShortName: TextView = view.findViewById(R.id.currencyShortName)
        private val currencyFullName: TextView = view.findViewById(R.id.currencyFullName)
        private val currencyRate: EditText = view.findViewById(R.id.currencyRate)

        override fun bindView(item: MainCurrencyItem, payloads: List<Any>) {
            Glide.with(currencyImageView)
                .load(item.vo.image)
                .circleCrop()
                .into(currencyImageView)

            currencyShortName.text = item.vo.currencyShortName
            currencyFullName.text = item.vo.currencyFullName
            currencyRate.setText(item.vo.amount)
            currencyRate.isEnabled = true
        }

        override fun unbindView(item: MainCurrencyItem) {
            Glide.with(currencyImageView).clear(currencyImageView)
        }
    }
}