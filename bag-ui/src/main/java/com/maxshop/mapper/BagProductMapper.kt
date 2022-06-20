package com.maxshop.mapper

import androidx.lifecycle.MutableLiveData
import com.maxshop.model.BagProduct
import com.maxshop.viewState.BagProductViewState
import javax.inject.Inject

internal class BagProductMapper @Inject constructor() {
    fun toViewState(product: BagProduct): BagProductViewState {
        return BagProductViewState(
            product.id,
            product.title,
            product.imageUrl,
            product.color,
            product.size,
            MutableLiveData(product.amount),
            product.price
        )
    }
}
