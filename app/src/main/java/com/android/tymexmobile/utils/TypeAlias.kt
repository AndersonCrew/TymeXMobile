package com.android.tymexmobile.utils

import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by BM Anderson on 29/10/2024.
 */

typealias InflateFragmentAlias<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

typealias InflateActivityAlias<T> = (LayoutInflater) -> T