package com.test.revolut.ui.item

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.test.revolut.R
import com.test.revolut.ui.vo.MainCurrencyVo

class MainCurrencyItem(
    private val vo: MainCurrencyVo,
    private val onAmountChanged: (CharSequence?) -> Unit
) : AbstractItem<MainCurrencyItem.ViewHolder>() {

    override val type = R.id.main_currency_item_id

    override val layoutRes = R.layout.currency_rate_item

    override fun getViewHolder(v: View) =
        ViewHolder(v)

    override var identifier: Long = vo.hashCode().toLong()

    inner class ViewHolder(view: View) : FastAdapter.ViewHolder<MainCurrencyItem>(view) {

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
            currencyRate.apply {
                isEnabled = true
                isFocusableInTouchMode = true
                isFocusable = true
                imeOptions = EditorInfo.IME_ACTION_DONE
                setText(item.vo.amount)
                doOnTextChanged { text, _, _, _ -> onAmountChanged(text) }
                setOnFocusChangeListener { view, b ->
                    if (b) {
                        currencyRate.setSelection(currencyRate.text.length)
                    }
                }
//                setOnClickListener {
//                    post(object :Runnable {
//                        override fun run() {
//                            currencyRate.setSelection(currencyRate.text.length)
//                        }
//                    })
//                }
            }
        }

        override fun unbindView(item: MainCurrencyItem) {
            Glide.with(currencyImageView).clear(currencyImageView)
        }
    }
}