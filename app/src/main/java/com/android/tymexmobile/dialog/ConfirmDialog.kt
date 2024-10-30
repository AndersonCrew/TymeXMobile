package com.android.tymexmobile.dialog

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.android.tymexmobile.R
import com.android.tymexmobile.databinding.DialogConfirmBinding
import com.android.tymexmobile.utils.gone
import com.android.tymexmobile.utils.safeClick
import com.android.tymexmobile.utils.textVisible
import com.android.tymexmobile.utils.visible
import javax.inject.Inject

/**
 * Created by BM Anderson on 29/10/2024.
 */

interface ConfirmDialog : HostDialogDelegate {
    fun showDialog(
        icon: Int? = null,
        title: String? = null,
        message: String,
        leftText: String? = null,
        rightText: String? = null,
        onLeftButtonClick: (() -> Unit) = {},
        onRightButtonClick: () -> Unit = {},
        cancelable: Boolean = true
    )
}

class ConfirmDialogImpl @Inject constructor(context: Fragment) :
    HostDialog<DialogConfirmBinding>(context, DialogConfirmBinding::inflate, R.style.loadingDialogTheme),
    ConfirmDialog {

    override fun setupDialogConfig(alertDialog: AlertDialog) {
        binding.tvTitle.text = null
        binding.tvContent.text = null
        binding.tvCancel.text = null
        binding.tvYes.text = null
    }

    override fun bindDialogView() {

    }


    private var onCancelled: (() -> Unit)? = null

    override fun onDialogCancelled() {
        super.onDialogCancelled()
        onCancelled?.invoke()
    }

    override fun resizeWindowSize(alertDialog: AlertDialog) {

    }

    override fun showDialog(
        icon: Int?,
        title: String?,
        message: String,
        leftText: String?,
        rightText: String?,
        onLeftButtonClick: () -> Unit,
        onRightButtonClick: () -> Unit,
        cancelable: Boolean
    ) {
        showDialog()
        setCancelable(cancelable)

        binding.apply {
            tvTitle.textVisible(title)
            tvContent.text = message
            tvCancel.textVisible(leftText)
            tvYes.textVisible(rightText)

            if(icon != null) {
                imgIcon.setImageResource(icon)
                imgIcon.visible()
            } else {
                imgIcon.gone()
            }

            tvCancel.safeClick {
                dismissDialog()
                onLeftButtonClick.invoke()
            }

            tvYes.safeClick {
                dismissDialog()
                onRightButtonClick.invoke()
            }

        }
    }
}
