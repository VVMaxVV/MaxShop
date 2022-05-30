package com.maxshop.usecase

import com.maxshop.model.TypeSort
import io.reactivex.Single

interface GetSortsUseCase {
    fun execute(): Single<List<TypeSort>>
}
