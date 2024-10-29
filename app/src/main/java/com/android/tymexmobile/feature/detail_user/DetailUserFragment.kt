package com.android.tymexmobile.feature.detail_user

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.model.User
import com.android.tymexmobile.R
import com.android.tymexmobile.base.BaseFragment
import com.android.tymexmobile.base.BaseViewEvent
import com.android.tymexmobile.base.BaseViewState
import com.android.tymexmobile.databinding.FragmentUserDetailBinding
import com.android.tymexmobile.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.android.tymexmobile.utils.safeClick

/**
 * Created by BM Anderson on 28/10/2024.
 */

@AndroidEntryPoint
class DetailUserFragment : BaseFragment<FragmentUserDetailBinding, DetailUserViewModel>(FragmentUserDetailBinding::inflate) {

    override val viewModel: DetailUserViewModel by viewModels()

    override var enableBackPressedDispatcher = true

    private val args: DetailUserFragmentArgs by navArgs()

    @Inject lateinit var navigator: DetailUserNavigator
    override fun setupView() {
        super.setupView()
        args.user?.let {
            binding.imgAvatar.loadImage(
                url = it.avatarUrl,
                context = binding.root.context,
                errorImage = R.drawable.user_default
            )
        }
    }

    override fun bindViewEvents() {
        super.bindViewEvents()
        args.user?.login?.let {
            viewModel.getUser(it)
        }

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
            is DetailUserViewModel.ViewState.GetUserSuccess -> {
                bindDataUser(viewState.list)
            }
        }
    }

    private fun bindDataUser(user: User?) {
        binding.apply {
            tvUserName.text = user?.name
            tvBlog.text = user?.blog
            tvCountFollower.text = checkCount(user?.followers?: 0)
            tvCountFollowing.text = checkCount(user?.following?: 0)
        }
    }

    private fun checkCount(total: Int): String {
        return when {
            total <= 10 -> total.toString()
            total in 11..100 -> "10+"
            else -> "100+"
        }
    }

}