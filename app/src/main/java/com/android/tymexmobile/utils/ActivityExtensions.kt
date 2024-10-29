package com.android.tymexmobile.utils

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets

/**
 * Created by BM Anderson on 29/10/2024.
 */

val Activity.windowHeight: Int
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = windowManager.currentWindowMetrics
            val insets = metrics.windowInsets.getInsets(WindowInsets.Type.systemBars())
            metrics.bounds.height() - insets.bottom - insets.top
        } else {
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            var screenHeight = displayMetrics.heightPixels
            // find out if status bar has already been subtracted from screenHeight
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getRealMetrics(displayMetrics)
            val physicalHeight = displayMetrics.heightPixels
            val heightDelta = physicalHeight - screenHeight
            val statusBarHeight = statusBarHeightPx()
            val navigationBarHeight = navigationBarHeightPx()
            if (heightDelta == 0 || heightDelta == navigationBarHeight) {
                screenHeight -= statusBarHeight
            }
            screenHeight
        }
    }

val Activity.windowWidth: Int
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = windowManager.currentWindowMetrics
            val insets = metrics.windowInsets.getInsets(WindowInsets.Type.systemBars())
            metrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

val Activity.screenWidth: Int
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = windowManager.currentWindowMetrics
            metrics.bounds.width()
        } else {
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

val Activity.screenHeight: Int
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = windowManager.currentWindowMetrics
            metrics.bounds.height()
        } else {
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }


@SuppressLint("DiscouragedApi", "InternalInsetResource")
fun Context.statusBarHeightPx(): Int {
    var height = 0
    try {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            height = resources.getDimensionPixelSize(resourceId)
        }
    } catch (ignore: Exception) {
    }
    return height
}

fun Context.actionBarHeightPx(): Int {
    var height = 0
    try {
        theme.obtainStyledAttributes(
            intArrayOf(R.attr.actionBarSize)
        ).use {
            height = it.getDimension(0, 0f).toInt()
        }
    } catch (ignore: Exception) {
    }
    return height
}

@SuppressLint("InternalInsetResource", "DiscouragedApi")
fun Context.navigationBarHeightPx(): Int {
    var height = 0
    try {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            height = resources.getDimensionPixelSize(resourceId)
        }
    } catch (ignore: Exception) {
    }
    return height
}