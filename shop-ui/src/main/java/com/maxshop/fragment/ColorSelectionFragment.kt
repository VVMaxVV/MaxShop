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

internal class ColorSelectionFragment : BaseBottomSheetFragment(), HasLifeCycleObserver {
    private val args: ColorSelectionFragmentArgs by navArgs()

    val viewModel: ColorSelectionViewModel by viewModels { factory }

    override fun addLifecycleObserver() {
        lifecycle.addObserver(viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.colorsList = args.colors.toList()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentColorSelectionBinding.inflate(inflater, container, false).apply {
            viewModel = this@ColorSelectionFragment.viewModel
            lifecycleOwner = this@ColorSelectionFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                is ColorSelectionViewModel.Event.Close -> findNavController().popBackStack()
            }
        }
    }
}
