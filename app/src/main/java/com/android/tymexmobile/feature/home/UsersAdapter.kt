package com.android.tymexmobile.feature.home

import android.annotation.SuppressLint
import android.text.Html
import com.android.model.User
import com.android.tymexmobile.R
import com.android.tymexmobile.base.BaseListAdapter
import com.android.tymexmobile.databinding.ItemUserBinding
import com.android.tymexmobile.utils.loadImage

/**
 * Created by BM Anderson on 29/10/2024.
 */
class UsersAdapter : BaseListAdapter<User, ItemUserBinding>(
    mInflate = ItemUserBinding::inflate,
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old.id == new.id }
) {
    @SuppressLint("SetTextI18n")
    override fun bindViewHolder(
        binding: ItemUserBinding,
        model: User,
        position: Int,
        viewType: Int
    ) {
        binding.apply {
            tvUserName.text = model.login
            tvLink.text = Html.fromHtml("<u>${model.htmlUrl}</u>", Html.FROM_HTML_MODE_LEGACY)

            imgAvatar.loadImage(
                url = model.avatarUrl,
                context = binding.root.context,
                errorImage = R.drawable.user_default
            )
        }
    }
}