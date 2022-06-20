package com.maxshop.mapper

import com.example.bag_ui.BR
import com.example.bag_ui.R
import com.maxshop.model.BagProduct
import com.maxshop.model.RecyclerItem
import com.maxshop.viewState.BagProductViewState
import javax.inject.Inject

internal class BagProductViewStateMapper @Inject constructor() {
    fun toBagProduct(product: BagProductViewState) = BagProduct(
        product.id,
        product.title,
        product.imageUrl,
        product.color,
        product.size,
        product.amount.value ?: throw NullPointerException("Product amount can`t be null"),
        product.price
    )

    fun toRecyclerItem(viewState: BagProductViewState): RecyclerItem {
        return RecyclerItem(
            viewState,
            R.layout.item_bag_product,
            BR.viewState
        )
    }
}
