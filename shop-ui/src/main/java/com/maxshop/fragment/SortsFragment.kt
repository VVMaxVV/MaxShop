package com.maxshop.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.maxshop.shop_ui.R
import com.maxshop.shop_ui.databinding.FragmentBottomSheetSortBinding
import com.maxshop.viewModel.SortsViewModel
import com.maxshop.viewModel.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SortsFragment : BottomSheetDialogFragment() {
    private val args: SortsFragmentArgs by navArgs()

    @Inject
    lateinit var factory: ViewModelFactory

    var binding: FragmentBottomSheetSortBinding? = null

    private val viewModel: SortsViewModel by viewModels { factory }

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
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

        viewModel.getSortsList(args.typeSort)

        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                is SortsViewModel.Event.SortTypeSelected -> this.dismiss()
            }
        }
    }
}
