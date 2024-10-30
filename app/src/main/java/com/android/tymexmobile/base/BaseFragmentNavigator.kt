package com.android.tymexmobile.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.android.tymexmobile.R
import timber.log.Timber

/**
 * Created by BM Anderson on 29/10/2024.
 */
interface BaseFragmentNavigator {
    val navHostFragmentId: Int

    fun findNavController(): NavController?

    fun navigate(directions: NavDirections, animation: NavigateAnimation = NavigateAnimation.FADE, bundle: Bundle?= null)

    fun navigateUp()

    fun pressBack()

    enum class NavigateAnimation {
        NOTHING,
        FADE,
        SLIDE,
    }

}

abstract class BaseFragmentNavigatorImpl(
    protected val fragment: Fragment
) : BaseFragmentNavigator {

    protected var navController: NavController? = null

    override fun findNavController(): NavController? {
        return navController ?: try {
            fragment.findNavController().apply {
                navController = this
            }
        } catch (e: IllegalStateException) {
            // Log Crashlytics as non-fatal for monitoring
            Timber.e(e)
            null
        }
    }

    override fun navigateUp() {
        findNavController()?.navigateUp()
    }

    override fun navigate(
        directions: NavDirections,
        animation: BaseFragmentNavigator.NavigateAnimation,
        bundle: Bundle?
    ) {

        findNavController()?.navigate(
            directions.actionId, bundle,
            when (animation) {
                BaseFragmentNavigator.NavigateAnimation.FADE -> {
                    NavOptions
                        .Builder()
                        .setEnterAnim(R.anim.anim_fade_in)
                        .setExitAnim(R.anim.anim_nothing)
                        .setPopEnterAnim(R.anim.anim_nothing)
                        .setPopExitAnim(R.anim.anim_fade_out)
                        .build()
                }

                BaseFragmentNavigator.NavigateAnimation.SLIDE -> {
                    NavOptions
                        .Builder()
                        .setEnterAnim(R.anim.anim_slide_right_to_current)
                        .setExitAnim(R.anim.anim_nothing)
                        .setPopEnterAnim(R.anim.anim_nothing)
                        .setPopExitAnim(R.anim.anim_slide_current_to_right)
                        .build()
                }

                else -> {
                    NavOptions
                        .Builder().build()
                }
            }
        )
    }

    override fun pressBack() {
        findNavController()?.navigateUp()
    }

    protected fun popBackTo(@IdRes destinationId: Int, inclusive: Boolean = false) {
        findNavController()?.popBackStack(destinationId, inclusive)
    }
}
