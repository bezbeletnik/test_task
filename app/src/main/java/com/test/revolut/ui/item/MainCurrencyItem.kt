package com.test.revolut.ui.item

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
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

    override var identifier: Long = vo.toIdentifierSignificantFields().hashCode().toLong()

    private fun MainCurrencyVo.toIdentifierSignificantFields(): IdentifierSignificantFields {
        return IdentifierSignificantFields(
            image = image,
            currencyShortName = currencyShortName,
            currencyFullName = currencyFullName
        )
    }

    private data class IdentifierSignificantFields(
        val image: Int,
        val currencyShortName: String,
        val currencyFullName: String
    )

    inner class ViewHolder(view: View) : FastAdapter.ViewHolder<MainCurrencyItem>(view) {

        private val currencyImageView: ImageView = view.findViewById(R.id.currencyImageView)
        private val currencyShortName: TextView = view.findViewById(R.id.currencyShortName)
        private val currencyFullName: TextView = view.findViewById(R.id.currencyFullName)
        private val currencyRate: TextView = view.findViewById(R.id.currencyRate)
        private val currencyAmount: EditText = view.findViewById(R.id.currencyAmount)

        private val textWatcher = object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                onAmountChanged(text)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //nop
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //nop
            }
        }

        override fun bindView(item: MainCurrencyItem, payloads: List<Any>) {
            currencyAmount.isVisible = true
            currencyRate.isVisible = false

            Glide.with(currencyImageView)
                .load(item.vo.image)
                .circleCrop()
                .into(currencyImageView)

            currencyShortName.text = item.vo.currencyShortName
            currencyFullName.text = item.vo.currencyFullName
            currencyAmount.apply {
                isEnabled = true
                isFocusableInTouchMode = true
                isFocusable = true
                imeOptions = EditorInfo.IME_ACTION_DONE
                setOnFocusChangeListener { _, isInFocus ->
                    if (isInFocus) {
                        post { currencyAmount.setSelection(currencyAmount.text.length) }
                    }
                }

                setOnEditorActionListener { v, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        currencyAmount.clearFocus()
                    }
                    false
                }

                if (!isFocused) {
                    setText(item.vo.amount)
                } else {
                    addTextChangedListener(textWatcher)
                }
            }
        }

        override fun unbindView(item: MainCurrencyItem) {
            Glide.with(currencyImageView).clear(currencyImageView)
            currencyAmount.removeTextChangedListener(textWatcher)
        }
    }
}