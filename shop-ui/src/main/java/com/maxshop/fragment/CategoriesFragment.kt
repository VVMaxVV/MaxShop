package com.maxshop.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxshop.adapter.DataBindingRecyclerAdapter
import com.maxshop.adapter.VerticalSpaceItemDecoration
import com.maxshop.mapper.CategoryMapper
import com.maxshop.shop_ui.R
import com.maxshop.shop_ui.databinding.FragmentCategoriesBinding
import com.maxshop.viewModel.CategoriesViewModel
import javax.inject.Inject

class CategoriesFragment : BaseFragment() {
    val viewModel: CategoriesViewModel by viewModels { factory }

    @Inject
    lateinit var categoryMapper: CategoryMapper

    private var binding: FragmentCategoriesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCategoriesBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            viewModel = this@CategoriesFragment.viewModel
            lifecycleOwner = this@CategoriesFragment
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding?.recyclerViewCategory
        recyclerView?.let {
            it.addItemDecoration(
                VerticalSpaceItemDecoration(
                    requireContext()
                        .resources
                        .getDimension(R.dimen.normal_100)
                        .toInt()
                )
            )
            it.adapter = DataBindingRecyclerAdapter()
            it.layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.getProducts()

        viewModel.events.observe(viewLifecycleOwner) {
            when (it) {
                is CategoriesViewModel.Event.OpenEvent
                -> openProducts(it.categoryName)
                is CategoriesViewModel.Event.ToastEvent
                -> showToastMessage(it.text)
                is CategoriesViewModel.Event.ReceivedThrowableEvent
                -> Log.e(CategoriesFragment::class.java.name, "${it.throwable.message}")
            }
        }
    }

    private fun openProducts(categoryName: String) {
        showToastMessage("Open category $categoryName")
    }
}
