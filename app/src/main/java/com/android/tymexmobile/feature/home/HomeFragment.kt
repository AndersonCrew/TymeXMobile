package com.android.tymexmobile.feature.home

import androidx.fragment.app.viewModels
import com.android.model.User
import com.android.tymexmobile.R
import com.android.tymexmobile.base.BaseFragment
import com.android.tymexmobile.base.BaseViewEvent
import com.android.tymexmobile.base.BaseViewState
import com.android.tymexmobile.databinding.FragmentHomeBinding
import com.android.tymexmobile.feature.splash.SplashViewModel
import com.android.tymexmobile.utils.gone
import com.android.tymexmobile.utils.safeClick
import com.android.tymexmobile.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by BM Anderson on 28/10/2024.
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()

    override var enableBackPressedDispatcher = true

    private var currentPage = 1

    @Inject lateinit var navigator: HomeNavigator

    private lateinit var adapter: UsersAdapter

    override fun setupView() {
        super.setupView()
        binding.toolBar.tvTitle.text = getText(R.string.home_title)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        if(!::adapter.isInitialized) {
            adapter = UsersAdapter()
            binding.rvUser.adapter = adapter
        } else {
            adapter.submitList(adapter.currentList)
        }
    }
    override fun bindViewEvents() {
        super.bindViewEvents()

        viewModel.getUser(currentPage)

        binding.toolBar.frBack.safeClick {
            navigator.pressBack()
        }
    }

    override fun bindViewModel(): Unit = with(viewModel) {
        super.bindViewModel()
        stateFlow bindTo ::bindStateFlow
    }



    override fun bindShareFlow(viewEvent: BaseViewEvent) {

    }

    override fun bindStateFlow(viewState: BaseViewState) {
        when(viewState) {
            is HomeViewModel.ViewState.GetListUserSuccess -> {
                bindDataUser(viewState.list)
            }
        }
    }

    private fun bindDataUser(list: List<User>?) {
        //First Load Data
        if(currentPage == 1) {
            if(list.isNullOrEmpty()) {
                binding.apply {
                    tvNoData.visible()
                    rvUser.gone()
                }
            } else {
                binding.apply {
                    tvNoData.gone()
                    rvUser.visible()
                    adapter.submitList(list)
                }
            }
        }
    }
}