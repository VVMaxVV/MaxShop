package com.maxshop.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.maxshop.model.TypeSort
import com.maxshop.shop_ui.databinding.FragmentProductsListBinding
import com.maxshop.viewModel.ProductsListViewModel

internal class ProductsListFragment : BaseFragment(), HasLifeCycleObserver {
    private val args: ProductsListFragmentArgs by navArgs()

    private var binding: FragmentProductsListBinding? = null

    private val viewModel: ProductsListViewModel by viewModels { factory }

    override fun addLifecycleObserver() {
        lifecycle.addObserver(viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.category = args.category
        super.onCreate(savedInstanceState)
    }

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

        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                is ProductsListViewModel.Event.ShowSorts -> findNavController().navigate(
                    ProductsListFragmentDirections
                        .actionProductsListFragmentToBottomSheetSortFragment(
                            viewModel.sort.value ?: TypeSort.Error
                        )
                )
                is ProductsListViewModel.Event.OpenProduct -> findNavController().navigate(
                    ProductsListFragmentDirections
                        .actionProductsListFragmentToProductDetailsFragment(it.id)
                )
            }
        }
    }
}
