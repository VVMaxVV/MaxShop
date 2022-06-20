package com.maxshop.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.bag_ui.databinding.FragmentBagBinding
import com.maxshop.viewModel.BagViewModel

class BagFragment : BaseFragment() {
    private val viewModel: BagViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentBagBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@BagFragment
            viewModel = this@BagFragment.viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts()

        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                is BagViewModel.Event.OpenProduct -> {
                    showToastMessage(
                        "Open product: ${it.id}"
                    )
                }
                is BagViewModel.Event.ShowToast -> {
                    Toast.makeText(context, it.idString, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
