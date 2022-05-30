package com.maxshop.usecase.impl

import com.maxshop.model.TypeSort
import com.maxshop.repository.ToolsRepository
import com.maxshop.usecase.GetSortsUseCase
import io.reactivex.Single
import javax.inject.Inject

internal class GetSortsUseCaseImpl @Inject constructor(
    private val toolsRepository: ToolsRepository
) : GetSortsUseCase {
    override fun execute(): Single<List<TypeSort>> {
        return toolsRepository.getSorts()
    }
}
