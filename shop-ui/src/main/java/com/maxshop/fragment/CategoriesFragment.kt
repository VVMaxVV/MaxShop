package com.maxshop.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.maxshop.shop_ui.databinding.FragmentCategoriesBinding
import com.maxshop.viewModel.CategoriesViewModel

internal class CategoriesFragment : BaseFragment() {
    private val viewModel: CategoriesViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return FragmentCategoriesBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            viewModel = this@CategoriesFragment.viewModel
            lifecycleOwner = this@CategoriesFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts()

        viewModel.events.observe(viewLifecycleOwner) {
            when (it) {
                is CategoriesViewModel.Event.OpenCategory
                -> openProducts(it.categoryName)
                is CategoriesViewModel.Event.ShowToast
                -> showToastMessage(it.text)
                is CategoriesViewModel.Event.OnError
                -> Log.e(CategoriesFragment::class.java.name, "${it.throwable.message}")
            }
        }
    }

    private fun openProducts(categoryName: String) {
        findNavController().navigate(
            CategoriesFragmentDirections.actionCategoriesFragmentToProductsListFragment(
                categoryName
            )
        )
    }
}
