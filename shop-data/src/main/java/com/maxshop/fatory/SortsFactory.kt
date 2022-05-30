package com.maxshop.fatory

import com.maxshop.model.TypeSort
import javax.inject.Inject

internal class SortsFactory @Inject constructor() {
    fun get(): List<TypeSort> {
        return listOf(
            TypeSort.Popular,
            TypeSort.PriceHighestToLow,
            TypeSort.PriceLowestToHigh
        )
    }
}
