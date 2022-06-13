package com.maxshop.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import com.example.maxshop.CoroutinesTestRule
import com.example.maxshop.RxAndroidSchedulerRule
import com.maxshop.mapper.ColorMapper
import com.maxshop.stream.ColorStream
import com.maxshop.viewState.ColorViewState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

internal class ColorSelectionViewModelTest {
    @RelaxedMockK
    lateinit var colorMapper: ColorMapper

    @RelaxedMockK
    lateinit var colorStream: ColorStream

    @Rule
    @JvmField
    val androidTestRule = RxAndroidSchedulerRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var lifecycle: LifecycleRegistry

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        lifecycle = LifecycleRegistry(mockk(relaxed = true))
    }

    private fun getViewModel() = ColorSelectionViewModel(colorMapper, colorStream).also {
        it.colorsList = listOf("Black")
    }

    private val colorViewState = ColorViewState("", false)

    @Test
    fun `GIVEN viewModel WHEN fragment created THEN colors added to itemList`() {
        // GIVEN
        val viewModel = getViewModel()
        lifecycle.addObserver(viewModel)

        // WHEN
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        // THEN
        assertNotNull(viewModel.itemsList.value)
    }

    @Test
    fun `GIVEN viewModel WHEN clicked on item THEN received event CloseFragment`() {
        // GIVEN
        every {
            colorMapper.toSizeViewState(any())
        }.returns(colorViewState)
        val viewModel = getViewModel()
        lifecycle.addObserver(viewModel)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        // WHEN
        colorViewState.onClick(colorViewState)

        // THEN
        assertTrue(viewModel.event.value is ColorSelectionViewModel.Event.Close)
    }
}
