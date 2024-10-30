package com.android.tymexmobile.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.android.data.ResourceException
import com.android.data.UseCaseResult
import com.android.data.database.DataSharedPreferences
import com.android.tymexmobile.MainActivity
import com.android.tymexmobile.R
import com.android.tymexmobile.dialog.ConfirmDialog
import com.android.tymexmobile.dialog.LoadingDialog
import com.android.tymexmobile.utils.InflateFragmentAlias
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Created by BM Anderson on 28/10/2024.
 */
abstract class BaseFragment<VB : ViewBinding, VM : HostViewModel>(private val inflate: InflateFragmentAlias<VB>) :
    Fragment() {

    protected abstract val viewModel: VM
    private var _binding: VB? = null
    val binding get() = _binding!!

    @Inject
    lateinit var mPref: DataSharedPreferences

    @Inject
    lateinit var commonDialog: ConfirmDialog

    @Inject
    lateinit var loadingDialog: LoadingDialog

    open var enableBackPressedDispatcher: Boolean = false
    open var hasFullScreen: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            _binding = inflate.invoke(inflater, container, false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        setupView()
        bindViewEvents()

        checkFullScreen()
    }

    private fun checkFullScreen() {
        if(!hasFullScreen) {
            var mainActivity = requireActivity() as MainActivity
            ViewCompat.setOnApplyWindowInsetsListener(mainActivity.findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }

    private fun setupBackPressedDispatcher() {
        val callback = object : OnBackPressedCallback(enableBackPressedDispatcher) {
            override fun handleOnBackPressed() {
                if (enableBackPressedDispatcher) {
                    // do stuff
                } else {
                    isEnabled = false
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(this, callback)
    }

    open fun setupView() {}

    open fun bindViewEvents() {}

    open fun bindViewModel() {
        with(viewModel) {
            uiState bindTo ::initObservation
        }
    }

    private fun initObservation(state: UseCaseResult<*>?) {
        when (state) {
            is UseCaseResult.Loading -> {
                loadingDialog.show()
            }

            is UseCaseResult.UnLoading -> {
                loadingDialog.hide()
            }

            is UseCaseResult.Error -> {
                loadingDialog.hide()
                commonDialog.showDialog(
                    message = "Server Error!"
                )
            }

            is UseCaseResult.Exception -> {
                loadingDialog.hide()
                when (state.ex) {
                    is ResourceException.UnAuthorized -> {
                        showPopupError(getString(R.string.session_expired), {
                            mPref.logout()
                            //TODO Navigate to Login
                            val navController = findNavController()
                            val navOptions = NavOptions.Builder()
                                .setPopUpTo(navController.graph.startDestinationId, true)
                                .build()
                            navController.navigate(R.id.nav_graph_splash, null, navOptions)
                        })
                    }

                    is ResourceException.NetworkConnection -> {
                        showPopupError(getString(R.string.lost_internet), icon = R.drawable.ic_lost_internet)
                    }

                    else -> {
                        showPopupError(getString(R.string.unknow_error))
                    }
                }
            }

            else -> {

            }
        }
    }

    open fun showPopupError(message: CharSequence?, callback: () -> Unit = {}, icon: Int?= null) {
        commonDialog.showDialog(
            icon = icon,
            message = message.toString(),
            rightText = context?.getString(R.string.yes),
            onRightButtonClick = callback
        )
    }

    abstract fun bindShareFlow(viewEvent: BaseViewEvent)

    abstract fun bindStateFlow(viewState: BaseViewState)

    protected inline infix fun <T> Flow<T>.bindTo(crossinline action: (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect {
                    action(it)
                }
            }
        }
    }

    protected inline fun launch(
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
        crossinline job: suspend CoroutineScope.() -> Unit
    ) = viewLifecycleOwner.lifecycleScope.launch(coroutineContext) {
        job.invoke(this)
    }

    protected inline fun launchUI(crossinline job: suspend CoroutineScope.() -> Unit) =
        launch(Dispatchers.Main, job)

    protected inline fun launchIO(crossinline job: suspend CoroutineScope.() -> Unit) =
        launch(Dispatchers.IO, job)
}