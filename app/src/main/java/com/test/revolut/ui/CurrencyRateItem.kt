package com.test.revolut.ui

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.test.revolut.R
import com.test.revolut.ui.vo.CurrencyRateVo

class CurrencyRateItem(
    private val vo: CurrencyRateVo
) : AbstractItem<CurrencyRateItem.ViewHolder>() {

    override val type = R.id.currency_rate_item_id

    override val layoutRes = R.layout.currency_rate_item

    override fun getViewHolder(v: View) = ViewHolder(v)

    override var identifier: Long = vo.hashCode().toLong()

    class ViewHolder(val view: View) : FastAdapter.ViewHolder<CurrencyRateItem>(view) {

        private val currencyImageView: ImageView = view.findViewById(R.id.currencyImageView)
        private val currencyShortName: TextView = view.findViewById(R.id.currencyShortName)
        private val currencyFullName: TextView = view.findViewById(R.id.currencyFullName)
        private val currencyRate: EditText = view.findViewById(R.id.currencyRate)

        override fun bindView(item: CurrencyRateItem, payloads: List<Any>) {
            Glide.with(currencyImageView)
                .load(item.vo.imageUrl)
                .circleCrop()
                .into(currencyImageView)

            currencyShortName.text = item.vo.currencyShortName
            currencyFullName.text = item.vo.currencyFullName
            currencyRate.setText(item.vo.rate)
        }

        override fun unbindView(item: CurrencyRateItem) {
            Glide.with(currencyImageView).clear(currencyImageView)
        }
    }
}