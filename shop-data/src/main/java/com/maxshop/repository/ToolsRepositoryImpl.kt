package com.maxshop.repository

import com.maxshop.fatory.SortsFactory
import com.maxshop.model.TypeSort
import io.reactivex.Single
import javax.inject.Inject

class ToolsRepositoryImpl @Inject constructor(
    private val sortsFactory: SortsFactory
) : ToolsRepository {
    override fun getSorts(): Single<List<TypeSort>> {
        return Single.just(sortsFactory.get())
    }
}
