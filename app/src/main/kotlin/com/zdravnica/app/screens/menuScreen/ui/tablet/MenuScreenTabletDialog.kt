package com.zdravnica.app.screens.menuScreen.ui.tablet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.zdravnica.app.screens.menuScreen.ui.MenuScreen
import com.zdravnica.app.screens.menuScreen.viewModels.MenuScreenViewModel
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MenuScreenTabletDialog(
    modifier: Modifier = Modifier,
    menuScreenViewModel: MenuScreenViewModel = koinViewModel(),
    onNavigateUp: (() -> Unit)? = null,
    navigateGToConnectionScreen: (() -> Unit)? = null,
    navigateToCancelDialogPage: (Boolean, String) -> Unit,
    navigateToTheConnectionScreen: () -> Unit,
) {
    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ), onDismissRequest = { }) {
        Box(
            modifier = Modifier
                .fillMaxSize(), contentAlignment = Alignment.CenterEnd
        ) {
            Surface(
                modifier = Modifier
                    .width(ZdravnicaAppTheme.dimens.size360),
                color = ZdravnicaAppTheme.colors.baseAppColor.success500
            ) {
                MenuScreen(
                    modifier = modifier,
                    menuScreenViewModel = menuScreenViewModel,
                    onNavigateUp = onNavigateUp,
                    navigateGToConnectionScreen = navigateGToConnectionScreen,
                    navigateToCancelDialogPage = navigateToCancelDialogPage,
                    navigateToTheConnectionScreen = navigateToTheConnectionScreen
                )
            }
        }
    }
}