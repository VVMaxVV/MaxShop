package com.maxshop.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxshop.adapter.CategoryAdapter
import com.maxshop.adapter.VerticalSpaceItemDecoration
import com.maxshop.shop_ui.R
import com.maxshop.shop_ui.databinding.FragmentCategoriesBinding
import com.maxshop.viewModel.CategoriesViewModel

class CategoriesFragment : BaseFragment() {
    val viewModel: CategoriesViewModel by viewModels { factory }

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
        val adapter = CategoryAdapter()
        recyclerView?.let {
            it.addItemDecoration(
                VerticalSpaceItemDecoration(
                    requireContext()
                        .resources
                        .getDimension(R.dimen.normal_100)
                        .toInt()
                )
            )
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
            it.setHasFixedSize(true)
        }

        viewModel.categoriesLiveData.observe(viewLifecycleOwner) {
            adapter.addData(it)
        }
        viewModel.getProducts()

        viewModel.events.observe(viewLifecycleOwner) {
            when (it) {
                is CategoriesViewModel.CategoryEvents.OpenCategoryProductListEvents
                -> openProducts(it.categoryName)
                is CategoriesViewModel.CategoryEvents.ToastCategoryEvents
                -> toast(it.text)
                is CategoriesViewModel.CategoryEvents.ReceivedThrowable
                -> Log.e(CategoriesFragment::class.java.name,"${it.throwable.message}")
            }
        }
    }

    private fun openProducts(categoryName: String) {
        toast("Open category $categoryName")
    }
}
