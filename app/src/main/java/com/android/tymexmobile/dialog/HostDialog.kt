package com.android.tymexmobile.dialog

import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.android.tymexmobile.R
import com.android.tymexmobile.base.BaseDialog
import com.android.tymexmobile.utils.InflateFragmentAlias

/**
 * Created by BM Steve on 08/04/2024.
 */

abstract class HostDialog<T : ViewBinding>(
    context: Fragment,
    bindingInflater: InflateFragmentAlias<T>,
    @StyleRes themeStyle: Int = R.style.customDialogTheme
) : BaseDialog<T>(context, bindingInflater, themeStyle), HostDialogDelegate {

    override fun show() {
        showDialog()
    }

    override fun hide() {
        dismissDialog()
    }

    override fun analyze(resId:Int) {
        showDialog()
    }

    override fun success(resId:Int) {
        showDialog()
    }
}

interface HostDialogDelegate {
    fun show()
    fun hide()
    fun analyze(resId:Int)
    fun success(resId:Int)
}