package com.maxshop.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.maxshop.RxAndroidSchedulerRule
import com.jraska.livedata.test
import com.maxshop.mapper.CategoryMapper
import com.maxshop.usecase.GetCategoriesUseCase
import com.maxshop.viewState.CategoryViewState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verifyOrder
import io.reactivex.Single
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

internal class CategoriesViewModelTest {
    @RelaxedMockK
    lateinit var getCategoriesUseCase: GetCategoriesUseCase

    @RelaxedMockK
    lateinit var categoryMapper: CategoryMapper

    @Rule
    @JvmField
    val androidTestRule = RxAndroidSchedulerRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var progressBar: Observer<Boolean>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    private fun getViewModel() = CategoriesViewModel(getCategoriesUseCase, categoryMapper)

    @Test
    fun `GIVEN viewModel WHEN categoryUseCase get products THEN categories added to list`() {
        // GIVEN
        every {
            getCategoriesUseCase.execute()
        }.returns(Single.just(listOf(mockk())))
        val viewModel = getViewModel()

        // WHEN
        viewModel.getCategories()

        // THEN
        assertNotNull(viewModel.categoriesList.value)
    }

    @Test
    fun `GIVEN viewModel WHEN categoryUseCase get error THEN receiver throwable`() {
        // GIVEN
        val throwable = mockk<Throwable>()
        every {
            getCategoriesUseCase.execute()
        }.returns(Single.error(throwable))
        val viewModel = getViewModel()
        val observer = viewModel.events.test()

        // WHEN
        viewModel.getCategories()

        // THEN
        observer.assertValue {
            it is CategoriesViewModel.Event.OnError
        }
    }

    @Test
    fun `GIVEN viewModel WHEN subscribe on getCategoryUseCase THEN progressBar change value`() {
        // GIVEN
        every {
            getCategoriesUseCase.execute()
        }.returns(Single.just(mockk()))
        val viewModel = getViewModel()
        viewModel.progressBar.observeForever(progressBar)

        // WHEN
        viewModel.getCategories()

        // THEN
        verifyOrder {
            progressBar.onChanged(true)
            progressBar.onChanged(false)
        }
    }

    @Test
    fun `GIVEN viewModel WHEN click on category THEN send OpenCategory event`() {
        val viewState = CategoryViewState("", "")
        every {
            getCategoriesUseCase.execute()
        }.returns(Single.just(listOf(mockk())))
        every {
            categoryMapper.toViewState(any())
        }.returns(viewState)
        val viewModel = getViewModel()
        val testObserver = viewModel.events.test()

        // WHEN
        viewModel.getCategories()
        viewState.onProductClick("")

        // THEN
        testObserver.assertValue {
            it is CategoriesViewModel.Event.OpenCategory
        }
    }
}
