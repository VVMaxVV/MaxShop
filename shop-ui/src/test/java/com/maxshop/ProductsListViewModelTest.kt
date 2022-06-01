package com.maxshop

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.maxshop.mapper.SimplifiedProductMapper
import com.maxshop.model.TypeSort
import com.maxshop.model.product.SimplifiedProduct
import com.maxshop.usecase.GetProductsCategoryUseCase
import com.maxshop.viewModel.ProductsListViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.Single
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

internal class ProductsListViewModelTest {
    @RelaxedMockK
    lateinit var getProductsCategoryUseCase: GetProductsCategoryUseCase

    @RelaxedMockK
    lateinit var simplifiedProductMapper: SimplifiedProductMapper

    @RelaxedMockK
    lateinit var sortStream: SortStream

    @Rule
    @JvmField
    val androidTestRule = RxAndroidSchedulerRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    private fun getViewModel() =
        ProductsListViewModel(getProductsCategoryUseCase, simplifiedProductMapper, sortStream)

    @Test
    fun `GIVEN viewModel WHEN getActiveSort() THEN sorted == sortStream`() {
        val typeSort = TypeSort.Error
        every {
            sortStream.value.stream()
        }.returns(Observable.just(typeSort))
        val viewModel = getViewModel()

        // WHEN
        viewModel.getActiveSort()

        // THEN
        assert(viewModel.sort.value == typeSort)
    }

    @Test
    fun `GIVEN viewModel WHEN showSorts() THEN event == ShowSorts()`() {
        // GIVEN
        val viewModel = getViewModel()

        // WHEN
        viewModel.showSorts()

        // THEN
        assert(viewModel.event.value == ProductsListViewModel.Event.ShowSorts)
    }

    @Test
    fun `GIVEN viewModel WHEN getProducts() THEN received products in productsList`() {
        // GIVEN
        every {
            getProductsCategoryUseCase.execute("1", mockk())
        }.returns(Single.just(mockk()))
        val viewModel = getViewModel()

        // WHEN
        viewModel.getProducts()

        // THEN
        assertNotNull(viewModel.productsList.value)
    }
}