package com.android.tymexmobile.dialog

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.android.tymexmobile.R
import com.android.tymexmobile.databinding.DialogLoadingBinding
import javax.inject.Inject

/**
 * Created by BM Steve on 08/04/2024.
 */

interface LoadingDialog : HostDialogDelegate

class LoadingDialogImpl @Inject constructor(context: Fragment) :
    HostDialog<DialogLoadingBinding>(
        context,
        DialogLoadingBinding::inflate,
        R.style.loadingDialogTheme
    ),
    LoadingDialog {

    override fun resizeWindowSize(alertDialog: AlertDialog) {

    }

    override fun show() {
        setCancelable(false)
        binding.animationView.playAnimation()
        showDialog()
    }

    override fun hide() {
        dismissDialog()
    }
}