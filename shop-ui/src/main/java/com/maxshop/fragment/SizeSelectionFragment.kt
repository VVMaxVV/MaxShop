package com.maxshop.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.maxshop.shop_ui.databinding.FragmentSizeSelectionBinding
import com.maxshop.viewModel.SizeSelectionViewModel

internal class SizeSelectionFragment : BaseBottomSheetFragment(), HasLifeCycleObserver {
    private val args: SizeSelectionFragmentArgs by navArgs()

    val viewModel: SizeSelectionViewModel by viewModels { factory }

    override fun addLifecycleObserver() {
        lifecycle.addObserver(viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.sizesList = args.sizes.toList()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSizeSelectionBinding.inflate(inflater, container, false).apply {
            viewModel = this@SizeSelectionFragment.viewModel
            lifecycleOwner = this@SizeSelectionFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                is SizeSelectionViewModel.Event.Close -> findNavController().popBackStack()
            }
        }
    }
}
