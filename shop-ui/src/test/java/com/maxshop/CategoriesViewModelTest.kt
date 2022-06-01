package com.maxshop

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.jraska.livedata.test
import com.maxshop.mapper.CategoryMapper
import com.maxshop.model.category.Category
import com.maxshop.usecase.GetCategoriesUseCase
import com.maxshop.viewModel.CategoriesViewModel
import com.maxshop.viewState.CategoryViewState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock


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

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    private fun getViewModel() = CategoriesViewModel(getCategoriesUseCase, categoryMapper)

    @Test
    fun `GIVEN viewModel WHEN getCategory THEN categories added to list`() {
        // GIVEN
        every {
            getCategoriesUseCase.execute()
        }.returns(Single.just(mockk()))
        val viewModel = getViewModel()

        // WHEN
        viewModel.getCategories()

        // THEN
        assert(viewModel.categoriesList.value?.size != 0)
    }

    @Test
    fun `GIVEN viewModel WHEN getCategory THEN receiver throwable`() {
        // GIVEN
        val throwable = Throwable()
        every {
            getCategoriesUseCase.execute()
        }.returns(Single.error(throwable))
        val viewModel = getViewModel()

        // WHEN
        viewModel.getCategories()

        // THEN
        assert(
            viewModel.events.value == CategoriesViewModel.Event.OnError(
                throwable
            )
        )
    }

    @Test
    fun `GIVEN category view state WHEN clicked on category THEN open category event`() {
        // GIVEN
        val categoryName = "Name"
        val url = "URL"
        every {
            getCategoriesUseCase.execute()
        }.returns(Single.just(listOf(Category(categoryName, url), Category(categoryName, url))))
        val viewModel = getViewModel()

        // WHEN
        viewModel.getCategories()
        (viewModel.categoriesList.value?.get(0)?.data as? CategoryViewState)?.onProductClick(
            categoryName
        )

        // THEN
        assert(viewModel.events.value == CategoriesViewModel.Event.OpenCategory(categoryName))
    }

    @Test
    fun `GIVEN viewModel WHEN subscribe on getCategoryUseCase THEN progressBar == true`() {
        // GIVEN
        val single = Single.just<List<Category>>(mockk()).delay(3, TimeUnit.SECONDS)
        every {
            getCategoriesUseCase.execute()
        }.returns(single)
        val viewModel = getViewModel()

        val lifecycle = LifecycleRegistry(mock(LifecycleOwner::class.java))
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        // WHEN

        viewModel.progressBar.test()

        viewModel.progressBar.observe({ lifecycle }) {
            assert(true)
        }
        viewModel.getCategories()
    }

    @Test
    fun `GIVEN viewModel WHEN do finally on getCategoryUseCase THEN progressBar == false`() {
        // GIVEN
        every {
            getCategoriesUseCase.execute()
        }.returns(Single.just(mockk()))
        val viewModel = getViewModel()

        // WHEN
        viewModel.getCategories()

        // THEN
        assert(viewModel.progressBar.value == false)
    }
}