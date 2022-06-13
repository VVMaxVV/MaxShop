package com.maxshop.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.example.maxshop.CoroutinesTestRule
import com.example.maxshop.RxAndroidSchedulerRule
import com.maxshop.model.product.DetailedProduct
import com.maxshop.stream.ColorStream
import com.maxshop.stream.SizeStream
import com.maxshop.usecase.GetProductUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verifyOrder
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
internal class ProductDetailsViewModelTest {
    @RelaxedMockK
    lateinit var getProductUseCase: GetProductUseCase

    @RelaxedMockK
    lateinit var colorStream: ColorStream

    @RelaxedMockK
    lateinit var sizeStream: SizeStream

    @Rule
    @JvmField
    val androidTestRule = RxAndroidSchedulerRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var lifecycle: LifecycleRegistry

    @RelaxedMockK
    private lateinit var visibilityError: Observer<Boolean>

    @RelaxedMockK
    private lateinit var progressBar: Observer<Boolean>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        lifecycle = LifecycleRegistry(mockk(relaxed = true))
    }

    private fun getViewModel() =
        ProductDetailsViewModel(getProductUseCase, sizeStream, colorStream).also {
            it.progressBar.observeForever(progressBar)
            it.visibilityError.observeForever(visibilityError)
        }

    private val product = DetailedProduct(
        0,
        "",
        "",
        "",
        "",
        "",
        0f,
        0,
        listOf("XL"),
        listOf("Black")
    )

    @Test
    fun `GIVEN viewModel WHEN fragment created THEN get product`() {
        // GIVEN
        val id = 1
        every {
            runBlocking {
                getProductUseCase.execute(any())
            }
        }.returns(mockk())
        val viewModel = getViewModel()
        viewModel.progressBar.observeForever(progressBar)
        viewModel.id = id
        lifecycle.addObserver(viewModel)

        // WHEN
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        // THEN
        assertNotNull(viewModel.detailedProduct.value)
        verifyOrder {
            progressBar.onChanged(true)
            progressBar.onChanged(false)
        }
    }

    @Test
    fun `GIVEN viewModel WHEN colorStream emit data THEN color set value`() = runTest {
        // GIVEN
        val id = 1
        val color = "Black"
        every {
            runBlocking {
                getProductUseCase.execute(any())
            }
        }.returns(mockk())
        every {
            runBlocking {
                colorStream.stream()
            }
        }.returns(
            flow {
                emit(color)
            }
        )
        val viewModel = getViewModel()
        viewModel.id = id
        lifecycle.addObserver(viewModel)

        // WHEN
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        // THEN
        assertTrue(viewModel.color.value == color)
    }

    @Test
    fun `GIVEN viewModel WHEN sizeStream emit data THEN size set value`() = runTest {
        // GIVEN
        val id = 1
        val size = "XL"
        every {
            runBlocking {
                getProductUseCase.execute(any())
            }
        }.returns(mockk())
        every {
            runBlocking {
                sizeStream.stream()
            }
        }.returns(
            flow {
                emit(size)
            }
        )
        val viewModel = getViewModel()
        viewModel.id = id
        lifecycle.addObserver(viewModel)

        // WHEN
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        // THEN
        assertTrue(viewModel.size.value == size)
    }

    @Test
    fun `GIVEN viewModel WHEN select size view clicked THEN received event ShowSizes`() {
        // GIVEN
        val id = 1
        every {
            runBlocking {
                getProductUseCase.execute(any())
            }
        }.returns(product)
        val viewModel = getViewModel()
        viewModel.id = id
        lifecycle.addObserver(viewModel)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        // WHEN
        viewModel.showSizes()

        // THEN
        assertTrue(viewModel.event.value is ProductDetailsViewModel.Event.ShowSizes)
    }

    @Test
    fun `GIVEN viewModel WHEN select color view clicked THEN received event ShowColors`() {
        // GIVEN
        val id = 1
        every {
            runBlocking {
                getProductUseCase.execute(any())
            }
        }.returns(product)
        val viewModel = getViewModel()
        viewModel.id = id
        lifecycle.addObserver(viewModel)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        // WHEN
        viewModel.showColors()

        // THEN
        assertTrue(viewModel.event.value is ProductDetailsViewModel.Event.ShowColors)
    }

    @Test
    fun `GIVEN viewModel WHEN get product called throwable THEN received event ReceivedThrowable`() {
        // GIVEN
        val id = 1
        val throwable = mockk<Throwable>()
        every {
            runBlocking {
                getProductUseCase.execute(any())
            }
        }.throws(throwable)
        val viewModel = getViewModel()
        viewModel.id = id
        lifecycle.addObserver(viewModel)

        // WHEN
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        // THEN
        assertTrue(viewModel.event.value == ProductDetailsViewModel.Event.OnError(throwable))
        assertTrue(viewModel.visibilityError.value == true)
        verifyOrder {
            progressBar.onChanged(true)
            progressBar.onChanged(false)
        }
    }

    @Test
    fun `GIVEN viewModel WHEN get product after error THEN product not null & visibilityError == false`() {
        // GIVEN
        val id = 1
        val throwable = mockk<Throwable>()
        every {
            runBlocking {
                getProductUseCase.execute(any())
            }
        }.throws(throwable)
        val viewModel = getViewModel()
        viewModel.id = id
        lifecycle.addObserver(viewModel)

        // WHEN
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        every {
            runBlocking {
                getProductUseCase.execute(any())
            }
        }.returns(product)
        viewModel.listenerButtonRefresh.onClick(mockk())

        // THEN
        verifyOrder {
            visibilityError.onChanged(true)
            visibilityError.onChanged(false)
        }
        assertTrue(viewModel.detailedProduct.value == product)
    }

    @Test
    fun `GIVEN viewModel WHEN id == null THEN emit OnError event & visibilityError == true`() {
        // GIVEN
        val viewModel = getViewModel()
        lifecycle.addObserver(viewModel)

        // WHEN
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)

        // THEN
        assertTrue((viewModel.event.value as ProductDetailsViewModel.Event.OnError).throwable is NullPointerException)
        assertTrue(viewModel.visibilityError.value == true)
    }
}
