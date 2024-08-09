package com.zdravnica.app.screens.connecting_page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zdravnica.app.screens.connecting_page.ui.ConnectingPageContentScreen
import com.zdravnica.app.screens.connecting_page.viewmodels.ConnectingPageSideEffect
import com.zdravnica.app.screens.connecting_page.viewmodels.ConnectingPageViewModel
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.components.loader.AppLoader
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ConnectingPageScreen(
    viewModel: ConnectingPageViewModel,
    modifier: Modifier = Modifier,
    onShowAllDevicesDialog: (() -> Unit)? = null
) {

    val viewState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ConnectingPageSideEffect.OnShowAllDevicesDialog -> onShowAllDevicesDialog?.invoke()
        }
    }

    Box(modifier = modifier) {

        ConnectingPageContentScreen(
            viewState = viewState,
            modifier = Modifier.fillMaxSize(),
            showAllBluetoothDevicesDialog = viewModel::showAllBluetoothDevicesDialog,
        )

        AnimatedVisibility(
            visible = viewState.isLoading,
            enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top),
            exit = slideOutVertically { it } + shrinkVertically()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ZdravnicaAppTheme.colors.primaryBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                AppLoader(
                    modifier = Modifier
                        .requiredSize(ZdravnicaAppTheme.dimens.size120)
                )
            }
        }
    }
}