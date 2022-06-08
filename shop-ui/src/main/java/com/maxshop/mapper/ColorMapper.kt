package com.maxshop.mapper

import com.maxshop.model.RecyclerItem
import com.maxshop.shop_ui.BR
import com.maxshop.shop_ui.R
import com.maxshop.viewState.ColorViewState
import javax.inject.Inject

internal class ColorMapper @Inject constructor() {
    fun toSizeViewState(size: String): ColorViewState {
        return ColorViewState(size, false)
    }

    fun toRecyclerItem(viewState: ColorViewState): RecyclerItem {
        return RecyclerItem(
            viewState,
            R.layout.item_selection_product_option,
            BR.viewState
        )
    }
}
