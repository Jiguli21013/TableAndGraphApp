package com.yanchelenko.tableandgraphapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.yanchelenko.tableandgraphapp.BR
import com.yanchelenko.tableandgraphapp.ui.activity.MainActivity
import com.yanchelenko.tableandgraphapp.utils.navigation.SCREENS
import com.yanchelenko.tableandgraphapp.utils.ui.observeWithLifecycle

abstract class BaseFragment<BINDING : ViewDataBinding, VIEW_MODEL : BaseViewModel>(@LayoutRes val layout: Int) : Fragment() {

    abstract val viewModel: VIEW_MODEL

    private var _binding: BINDING? = null
    val binding: BINDING
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layout, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.vm, viewModel)

        viewModel.screenFlow.observeWithLifecycle(this) { screen ->
            navigateToScreen(screen = screen)
        }

        return binding.root
    }

    private fun navigateToScreen(screen: SCREENS) {
        if (activity is MainActivity) {
            (activity as MainActivity).navigateToScreen(screen)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
