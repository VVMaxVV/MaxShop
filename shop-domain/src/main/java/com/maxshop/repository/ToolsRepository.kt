package com.maxshop.repository

import com.maxshop.model.TypeSort
import io.reactivex.Single

interface ToolsRepository {
    fun getSorts(): Single<List<TypeSort>>
}
