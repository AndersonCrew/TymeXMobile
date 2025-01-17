package com.android.tymexmobile.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Created by BM Anderson on 29/10/2024.
 */

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

fun View.viewable(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun TextView.textVisible(text: String?) {
    this.text = text
    this.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
}


fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

fun View.hideKeyboard(activity: Activity?) {
    hideKeyboard()
    activity?.currentFocus?.let {
        if (it is EditText) it.clearFocus()
    }
}

fun EditText.requestShowKeyboard() {
    requestFocus()
    setSelection(length())
    showKeyboard()
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

@SuppressLint("ClickableViewAccessibility")
fun View.setupHideSoftKeyboard(activity: Activity?) {
    // Set up touch listener for non-text box views to hide keyboard.
    if (this !is EditText) {
        this.setOnTouchListener { _/*v*/, event ->
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> hideKeyboard(activity)
            }
            false
        }
    }
    //If a layout container, iterate over children and seed recursion.
    if (this is ViewGroup) {
        for (i in 0 until this.childCount) {
            this.getChildAt(i).setupHideSoftKeyboard(activity)
        }
    }
}


inline fun View.safeClick(crossinline listener: (v: View) -> Unit) =
    singleClick(500, listener)

inline fun View.singleClick(thresholdMs: Long = 300, crossinline listener: (v: View) -> Unit) {
    var lastClickTime: Long = 0
    setOnClickListener {
        val realTime = SystemClock.elapsedRealtime()
        if (realTime - lastClickTime > thresholdMs) {
            lastClickTime = realTime
            listener(it)
        }
    }
}

fun ImageView.loadImage(url: String?, context: Context, errorImage: Int, isCenterCrop: Boolean = true) {
    if(isCenterCrop) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).error(errorImage)
            .centerCrop().into(this)
    } else {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).error(errorImage).into(this)
    }
}


