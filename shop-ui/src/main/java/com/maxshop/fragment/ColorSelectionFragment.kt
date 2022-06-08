package com.maxshop.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.maxshop.shop_ui.databinding.FragmentColorSelectionBinding
import com.maxshop.viewModel.ColorSelectionViewModel

internal class ColorSelectionFragment : BaseBottomSheetFragment() {
    private val args: ColorSelectionFragmentArgs by navArgs()

    private var binding: FragmentColorSelectionBinding? = null

    val viewModel: ColorSelectionViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentColorSelectionBinding.inflate(inflater, container, false).apply {
            viewModel = this@ColorSelectionFragment.viewModel
            lifecycleOwner = this@ColorSelectionFragment
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getColors(args.colors.toList())

        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                is ColorSelectionViewModel.Event.CloseDialog -> {
                    findNavController().popBackStack()
                }
            }
        }
    }
}
