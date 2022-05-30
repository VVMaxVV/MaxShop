package com.maxshop.mapper

import androidx.lifecycle.MutableLiveData
import com.maxshop.model.RecyclerItem
import com.maxshop.model.TypeSort
import com.maxshop.shop_ui.BR
import com.maxshop.shop_ui.R
import com.maxshop.viewState.SortViewState
import javax.inject.Inject

internal class SortMapper @Inject constructor() {
    fun toViewState(type: TypeSort, position: Int): SortViewState {
        return SortViewState(
            position,
            type,
            MutableLiveData()
        )
    }

    fun toRecyclerItem(sortViewState: SortViewState): RecyclerItem {
        return RecyclerItem(
            sortViewState,
            R.layout.item_bottom_sheet_sort,
            BR.sort
        )
    }
}
