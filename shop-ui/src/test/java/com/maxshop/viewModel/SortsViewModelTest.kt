package com.maxshop.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.maxshop.RxAndroidSchedulerRule
import com.jraska.livedata.test
import com.maxshop.SortStream
import com.maxshop.mapper.SortMapper
import com.maxshop.model.TypeSort
import com.maxshop.usecase.GetSortsUseCase
import com.maxshop.viewState.SortViewState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Single
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

internal class SortsViewModelTest {
    @RelaxedMockK
    lateinit var getSortsUseCase: GetSortsUseCase

    @RelaxedMockK
    lateinit var sortMapper: SortMapper

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

    private fun getViewModel() = SortsViewModel(getSortsUseCase, sortMapper, sortStream)

    @Test
    fun `GIVEN viewModel WHEN getSorts THEN sorts will be added to the sortsList`() {
        // GIVEN
        every {
            getSortsUseCase.execute()
        }.returns(Single.just(listOf(TypeSort.Popular)))
        val viewModel = getViewModel()

        // WHEN
        viewModel.getSortsList(TypeSort.Popular)

        // THEN
        assertNotNull(viewModel.listSorts.value)
    }

    @Test
    fun `GIVEN viewModel WHEN click on sorting THEN get active sort on sortStream`() {
        val viewState = SortViewState(10, TypeSort.Popular, MutableLiveData(true))
        every {
            getSortsUseCase.execute()
        }.returns(Single.just(listOf(TypeSort.Popular)))
        every {
            sortMapper.toViewState(any(), any())
        }.returns(viewState)
        val viewModel = getViewModel()
        val observer = viewModel.event.test()

        // WHEN
        viewModel.getSortsList(TypeSort.Popular)
        viewState.onClick(viewState)

        // THEN
        observer.assertValue {
            it is SortsViewModel.Event.SortTypeSelected
        }
        assertTrue(viewModel.activePosition == 10)
    }
}
