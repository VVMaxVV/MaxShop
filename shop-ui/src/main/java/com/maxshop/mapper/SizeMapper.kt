package com.maxshop.mapper

import com.maxshop.model.RecyclerItem
import com.maxshop.shop_ui.BR
import com.maxshop.shop_ui.R
import com.maxshop.viewState.SizeViewState
import javax.inject.Inject

internal class SizeMapper @Inject constructor() {
    fun toSizeViewState(size: String): SizeViewState {
        return SizeViewState(size, false)
    }

    fun toRecyclerItem(viewState: SizeViewState): RecyclerItem {
        return RecyclerItem(
            viewState,
            R.layout.item_size_selection,
            BR.size
        )
    }
}