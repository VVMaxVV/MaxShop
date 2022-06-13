package com.maxshop.mapper

import androidx.lifecycle.MutableLiveData
import com.maxshop.model.RecyclerItem
import com.maxshop.model.product.SimplifiedProduct
import com.maxshop.shop_ui.BR
import com.maxshop.shop_ui.R
import com.maxshop.viewState.PLPItemViewState
import javax.inject.Inject

internal class SimplifiedProductMapper @Inject constructor() {
    fun toPLPItemViewState(product: SimplifiedProduct): PLPItemViewState {
        return PLPItemViewState(
            product.id,
            product.title,
            product.imageUrl,
            product.rating,
            product.ratingCount,
            product.price,
            MutableLiveData<Boolean>()
        )
    }

    fun toRecyclerItem(productViewState: PLPItemViewState): RecyclerItem {
        return RecyclerItem(
            productViewState,
            R.layout.item_product_list,
            BR.product
        )
    }
}
