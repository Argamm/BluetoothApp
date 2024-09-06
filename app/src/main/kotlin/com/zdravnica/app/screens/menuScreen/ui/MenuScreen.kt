package com.zdravnica.app.screens.menuScreen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zdravnica.app.screens.menuScreen.viewModels.MenuScreenSideEffect
import com.zdravnica.app.screens.menuScreen.viewModels.MenuScreenViewModel
import com.zdravnica.app.utils.isTablet
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.components.topAppBar.SimpleTopAppBar
import com.zdravnica.uikit.extensions.compose.callPhoneActivity
import com.zdravnica.uikit.extensions.compose.sendEmailActivity
import com.zdravnica.uikit.resources.R
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    menuScreenViewModel: MenuScreenViewModel = koinViewModel(),
    onNavigateUp: (() -> Unit)? = null,
    navigateGToConnectionScreen: (() -> Unit)? = null,
    navigateToCancelDialogPage: (Boolean, String) -> Unit,
) {
    val context = LocalContext.current
    val menuScreenViewState by menuScreenViewModel.container.stateFlow.collectAsStateWithLifecycle()
    val cancelDialog = stringResource(id = R.string.menu_screen_cancel_title)
    val temperature = menuScreenViewModel.temperature
    val supportEmailAddress =
        stringResource(id = R.string.menu_screen_zdravnica_support_email_address)
    val supportPhoneNumber =
        stringResource(id = R.string.menu_screen_zdravnica_support_phone_number)
    val localUriHandler = LocalUriHandler.current
    val faqInfoUriPath = stringResource(id = R.string.menu_screen_zdravnica_uri_path)

    menuScreenViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is MenuScreenSideEffect.OnNavigateToConnectionScreen -> navigateGToConnectionScreen?.invoke()
            is MenuScreenSideEffect.OnNavigateUp -> onNavigateUp?.invoke()
            is MenuScreenSideEffect.OnSiteClick -> {
                localUriHandler.openUri(faqInfoUriPath)
            }

            is MenuScreenSideEffect.OnEmailClick -> {
                context.sendEmailActivity(supportEmailAddress)
            }

            is MenuScreenSideEffect.OnCallClick -> {
                context.callPhoneActivity(supportPhoneNumber)
            }

            is MenuScreenSideEffect.OnNavigateToCancelDialogPage ->
                navigateToCancelDialogPage.invoke(false, cancelDialog)
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                menuScreenViewModel.onChangeCancelDialogPageVisibility(false)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .then(
                if (menuScreenViewState.uiModel.idDialogVisible) {
                    Modifier.blur(ZdravnicaAppTheme.dimens.size15)
                } else Modifier
            ),
        topBar = {
            SimpleTopAppBar(
                title = stringResource(R.string.menu_screen_top_app_bar_title),
                onNavigateUp = menuScreenViewModel::onNavigateUp
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(
                        top = ZdravnicaAppTheme.dimens.size12,
                        start = ZdravnicaAppTheme.dimens.size12,
                        end = ZdravnicaAppTheme.dimens.size12
                    )
            ) {
                item {
                    MenuTemperatureInfo(
                        //this data must get from bluetooth
                        temperature = temperature.value,
                    )
                }
                item {
                    MenuIndicators()
                }
                item {
                    //this data must get from bluetooth
                    MenuBalms(
                        firstBalmCount = 100,
                        secondBalmCount = 100,
                        thirdBalmCount = 0
                    )
                }
                item {
                    MenuConnectToUs(
                        onSiteClick = menuScreenViewModel::onSiteClick,
                        onEmailClick = menuScreenViewModel::onEmailClick,
                        onCallClick = menuScreenViewModel::onCallClick,
                    )
                }
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = ZdravnicaAppTheme.dimens.size24)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                menuScreenViewModel.onChangeCancelDialogPageVisibility(true)
                                menuScreenViewModel.navigateToCancelDialogPage()
                            },
                        text = stringResource(R.string.menu_screen_disconnect),
                        style = ZdravnicaAppTheme.typography.bodyMediumSemi,
                        color = ZdravnicaAppTheme.colors.baseAppColor.gray200,
                        textAlign = TextAlign.Center
                    )
                }
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = ZdravnicaAppTheme.dimens.size12,
                                bottom = ZdravnicaAppTheme.dimens.size6
                            ),
                        text = stringResource(R.string.menu_screen_last_subtitle),
                        style = ZdravnicaAppTheme.typography.bodyNormalMedium,
                        color = ZdravnicaAppTheme.colors.baseAppColor.gray500,
                        textAlign = TextAlign.Center
                    )

                    Spacer(
                        modifier = Modifier.height(
                            if (isTablet())
                                ZdravnicaAppTheme.dimens.size100
                            else
                                ZdravnicaAppTheme.dimens.size50
                        )
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewMenuScreen() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        MenuScreen(navigateToCancelDialogPage = { a, b -> })
    }
}
