package com.yanchelenko.tableandgraphapp.ui.home

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.yanchelenko.tableandgraphapp.ui.activity.MainActivity
import com.yanchelenko.tableandgraphapp.R
import com.yanchelenko.tableandgraphapp.databinding.FragmentHomeBinding
import com.yanchelenko.tableandgraphapp.ui.UIAction
import com.yanchelenko.tableandgraphapp.ui.UIState
import com.yanchelenko.tableandgraphapp.ui.base.BaseFragment
import com.yanchelenko.tableandgraphapp.utils.ui.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
    }

    private fun observeState() {
        println("---observeState()")
        viewModel.uiState.observeWithLifecycle(viewLifecycleOwner) { uiState ->
            println("---newEvent")
                println("---uistate---${uiState}")
                when (uiState) {
                    is UIState.Success -> {
                        println("---UIState---Success---observeState()")
                        println("---uiState---points---${uiState.points}")
                        (activity as MainActivity).setVisibilityProgressBar(isVisible = false)
                        viewModel.onAction(
                            action = UIAction.NAVIGATE(
                                route = R.id.action_homeFragment_to_tableFragment,
                                points = uiState.points
                            )
                        )
                    }
                    is UIState.Loading -> {
                        (activity as MainActivity).setVisibilityProgressBar(isVisible = true)
                        hideKeyboard()
                        println("---UIState---Loading")
                    }
                    is UIState.Error -> {
                        (activity as MainActivity).setVisibilityProgressBar(isVisible = false)
                        hideKeyboard()
                        println("---UIState---Error")
                    }
                    is UIState.None -> {
                        (activity as MainActivity).setVisibilityProgressBar(isVisible = false)
                        println("---UIState---None")
                    }
                }


        }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}
