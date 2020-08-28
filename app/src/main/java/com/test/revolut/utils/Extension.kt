package com.test.revolut.utils

import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.IAdapter

@Suppress("UNCHECKED_CAST")
fun <T : GenericItem> IAdapter<T>.downcast(): IAdapter<GenericItem> {
    return this as IAdapter<GenericItem>
}