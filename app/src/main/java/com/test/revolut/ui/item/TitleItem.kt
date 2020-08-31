package com.test.revolut.ui.item

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.test.revolut.R

class TitleItem : AbstractItem<TitleItem.ViewHolder>() {

    override val type = R.id.title_item_id

    override val layoutRes = R.layout.title_item

    override fun getViewHolder(v: View) = ViewHolder(v)

    class ViewHolder(view: View) : FastAdapter.ViewHolder<TitleItem>(view) {
        override fun bindView(item: TitleItem, payloads: List<Any>) {
            //nop
        }

        override fun unbindView(item: TitleItem) {
            //nop
        }
    }
}