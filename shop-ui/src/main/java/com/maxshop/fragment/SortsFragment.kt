package com.maxshop.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.maxshop.shop_ui.databinding.FragmentBottomSheetSortBinding
import com.maxshop.viewModel.SortsViewModel

internal class SortsFragment : BaseBottomSheetFragment(), HasLifeCycleObserver {
    private val args: SortsFragmentArgs by navArgs()

    var binding: FragmentBottomSheetSortBinding? = null

    private val viewModel: SortsViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.activeSort = args.activeSort
        super.onCreate(savedInstanceState)
    }

    override fun addLifecycleObserver() {
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetSortBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            viewModel = this@SortsFragment.viewModel
            lifecycleOwner = this@SortsFragment
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                is SortsViewModel.Event.SortTypeSelected -> findNavController().popBackStack()
            }
        }
    }
}
