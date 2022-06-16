package com.maxshop.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.maxshop.shop_ui.databinding.FragmentProductDetailsBinding
import com.maxshop.viewModel.ProductDetailsViewModel

internal class ProductDetailsFragment : BaseFragment() {
    private val args: ProductDetailsFragmentArgs by navArgs()

    private var binding: FragmentProductDetailsBinding? = null

    private val viewModel: ProductDetailsViewModel by viewModels { factory }

    override fun getBottomNavVisibility(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false).apply {
            viewModel = this@ProductDetailsFragment.viewModel
            lifecycleOwner = this@ProductDetailsFragment
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                is ProductDetailsViewModel.Event.ShowSizes -> {
                    findNavController().navigate(
                        ProductDetailsFragmentDirections
                            .actionProductDetailsFragmentToSizeSelectionFragment(it.sizes.toTypedArray())
                    )
                }
                is ProductDetailsViewModel.Event.ShowColors -> {
                    findNavController().navigate(
                        ProductDetailsFragmentDirections
                            .actionPDPToColorSelectionFragment(it.colors.toTypedArray())
                    )
                }
                is ProductDetailsViewModel.Event.ShowToast -> {
                    Toast.makeText(context, it.messageId, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.id = args.id
        viewModel.getProduct()
    }
}
