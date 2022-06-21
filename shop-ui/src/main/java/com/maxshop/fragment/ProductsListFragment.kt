package com.maxshop.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxshop.adapter.Decorations
import com.maxshop.model.TypeSort
import com.maxshop.shop_ui.R
import com.maxshop.shop_ui.databinding.FragmentProductsListBinding
import com.maxshop.viewModel.ProductsListViewModel

internal class ProductsListFragment : BaseFragment() {
    private val args: ProductsListFragmentArgs by navArgs()

    private var binding: FragmentProductsListBinding? = null

    private val viewModel: ProductsListViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsListBinding.inflate(
            inflater, container, false
        ).apply {
            viewModel = this@ProductsListFragment.viewModel
            lifecycleOwner = this@ProductsListFragment
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.categoryName = args.category

        viewModel.getActiveSort()

        viewModel.getProducts()

        viewModel.event.observe(viewLifecycleOwner) {
            handleEvent(it)
        }
    }

    override fun onResume() {
        super.onResume()
        setLayoutManager()
    }

    private fun setLayoutManager() {
        binding?.recyclerView?.let { recyclerView ->
            binding?.changeLayoutManagerButton?.let {
                when (it.isChecked) {
                    false -> {
                        if (recyclerView.itemDecorationCount > 0) recyclerView.removeItemDecorationAt(
                            0
                        )
                        recyclerView.layoutManager =
                            LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                        recyclerView.addItemDecoration(
                            Decorations.spaces(
                                resources.getDimension(R.dimen.normal_100),
                                true
                            )
                        )
                    }
                    true -> {
                        if (recyclerView.itemDecorationCount > 0) recyclerView.removeItemDecorationAt(
                            0
                        )
                        recyclerView.layoutManager = GridLayoutManager(context, 2)
                        recyclerView.addItemDecoration(
                            Decorations.gridSpaces(
                                resources.getDimension(R.dimen.normal_100),
                                resources.getDimension(R.dimen.normal_50)
                            )
                        )
                    }
                }
            }
        }
    }

    private fun handleEvent(event: ProductsListViewModel.Event) {
        when (event) {
            is ProductsListViewModel.Event.ShowSorts -> findNavController().navigate(
                ProductsListFragmentDirections
                    .actionProductsListFragmentToBottomSheetSortFragment(
                        viewModel.sort.value ?: TypeSort.Error
                    )
            )
            is ProductsListViewModel.Event.OpenProduct -> findNavController().navigate(
                ProductsListFragmentDirections
                    .actionProductsListFragmentToProductDetailsFragment(event.id)
            )
            is ProductsListViewModel.Event.ChangeLayoutManager -> {
                setLayoutManager()
            }
        }
    }
}
