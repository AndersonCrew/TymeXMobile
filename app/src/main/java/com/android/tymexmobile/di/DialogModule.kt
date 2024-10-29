package com.android.tymexmobile.di

import com.android.tymexmobile.dialog.ConfirmDialog
import com.android.tymexmobile.dialog.ConfirmDialogImpl
import com.android.tymexmobile.dialog.LoadingDialog
import com.android.tymexmobile.dialog.LoadingDialogImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent

/**
 * Created by BM Anderson on 29/10/2024.
 */
@Module
@InstallIn(ActivityComponent::class, FragmentComponent::class)
abstract class DialogModule {

    @Binds
    abstract fun providerConfirmDialog(context: ConfirmDialogImpl): ConfirmDialog

    @Binds
    abstract fun providerLoadingDialog(context: LoadingDialogImpl): LoadingDialog

}