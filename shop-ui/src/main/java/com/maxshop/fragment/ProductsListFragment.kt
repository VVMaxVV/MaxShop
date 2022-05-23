package com.maxshop.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.maxshop.shop_ui.R
import com.maxshop.shop_ui.databinding.FragmentProductsListBinding
import com.maxshop.viewModel.ProductsListViewModel
import javax.inject.Inject

class ProductsListFragment : BaseFragment() {
    companion object {
        const val EXTRA_CATEGORY_NAME = "category"
    }

    private var binding: FragmentProductsListBinding? = null

    private val viewModel: ProductsListViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsListBinding.inflate(
            inflater, container, false
        )
            .apply {
                viewModel = this@ProductsListFragment.viewModel
                lifecycleOwner = this@ProductsListFragment
            }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryName = this.requireArguments().getString(EXTRA_CATEGORY_NAME)

        viewModel.getProducts(categoryName ?: "Unknown error")
    }
}