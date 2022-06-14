package com.maxshop.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.maxshop.RxAndroidSchedulerRule
import com.jraska.livedata.test
import com.maxshop.mapper.SimplifiedProductMapper
import com.maxshop.model.TypeSort
import com.maxshop.stream.SortStream
import com.maxshop.usecase.GetProductsCategoryUseCase
import com.maxshop.viewState.PLPItemViewState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verifyOrder
import io.reactivex.Observable
import io.reactivex.Single
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
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

    @RelaxedMockK
    private lateinit var progressBar: Observer<Boolean>

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
    fun `GIVEN viewModel WHEN getActiveSort() THEN receive sorts`() {
        val typeSort = TypeSort.Error
        every {
            sortStream.value.stream()
        }.returns(Observable.just(typeSort))
        val viewModel = getViewModel()

        // WHEN
        viewModel.getActiveSort()

        // THEN
        assertTrue(viewModel.sort.value == typeSort)
    }

    @Test
    fun `GIVEN viewModel WHEN showSorts() THEN send ShowSorts event`() {
        // GIVEN
        val viewModel = getViewModel()

        // WHEN
        viewModel.showSorts()

        // THEN
        assertTrue(viewModel.event.value == ProductsListViewModel.Event.ShowSorts)
    }

    @Test
    fun `GIVEN viewModel WHEN click on product THEN send open product event`() {
        // GIVEN
        val viewState = PLPItemViewState(
            0,
            "",
            "",
            0f,
            0,
            "",
            MutableLiveData()
        )
        every {
            getProductsCategoryUseCase.execute(any(), any())
        }.returns(Single.just(listOf(mockk())))
        every {
            simplifiedProductMapper.toPLPItemViewState(any())
        }.returns(viewState)
        val viewModel = getViewModel()
        viewModel.categoryName = ""
        val testObserver = viewModel.event.test()

        // WHEN
        viewModel.getProducts()
        viewState.onClick(viewState)

        // THEN
        testObserver.assertValue {
            it is ProductsListViewModel.Event.OpenProduct
        }
    }

    @Test
    fun `GIVEN viewModel WHEN click on favorite THEN send FavoriteOnClicked event`() {
        // GIVEN
        val viewState = PLPItemViewState(
            0,
            "",
            "",
            0f,
            0,
            "",
            MutableLiveData()
        )
        every {
            getProductsCategoryUseCase.execute(any(), any())
        }.returns(Single.just(listOf(mockk())))
        every {
            simplifiedProductMapper.toPLPItemViewState(any())
        }.returns(viewState)
        val viewModel = getViewModel()
        viewModel.categoryName = ""
        val testObserver = viewModel.event.test()

        // WHEN
        viewModel.getProducts()
        viewState.onFavoriteClick(viewState)

        // THEN
        testObserver.assertValue {
            it is ProductsListViewModel.Event.FavoriteOnClicked
        }
    }

    @Test
    fun `GIVEN viewModel WHEN productsUseCase get products THEN products added in productsList`() {
        // GIVEN
        val category = ""
        every {
            getProductsCategoryUseCase.execute(category, TypeSort.Popular)
        }.returns(Single.just(listOf(mockk())))
        val viewModel = getViewModel()
        viewModel.categoryName = category

        // WHEN
        viewModel.getProducts()

        // THEN
        assertNotNull(viewModel.productsList.value)
    }

    @Test
    fun `GIVEN viewModel WHEN productsUseCase get error THEN send Error event`() {
        // GIVEN
        val category = ""
        every {
            getProductsCategoryUseCase.execute(category, TypeSort.Popular)
        }.returns(Single.error(mockk<Throwable>()))
        val viewModel = getViewModel()
        viewModel.categoryName = category
        val observer = viewModel.event.test()

        // WHEN
        viewModel.getProducts()

        // THEN
        observer.assertValue {
            it is ProductsListViewModel.Event.OnError
        }
    }

    @Test
    fun `GIVEN viewModel WHEN subscribe on getProductsCategoryUseCase THEN progressBar change value`() {
        // GIVEN
        val category = ""
        every {
            getProductsCategoryUseCase.execute(category, TypeSort.Popular)
        }.returns(Single.just(mockk()))
        val viewModel = getViewModel()
        viewModel.categoryName = category
        viewModel.progressBar.observeForever(progressBar)

        // WHEN
        viewModel.getProducts()

        // THEN
        verifyOrder {
            progressBar.onChanged(true)
            progressBar.onChanged(false)
        }
    }
}
