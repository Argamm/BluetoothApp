package com.zdravnica.app.screens.connecting_page.menuScreen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.CLOSE_ICON_DESCRIPTION

@Composable
fun MenuTopAppBar(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(
                onClick = {
                    onNavigateUp()
                }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = com.zdravnica.uikit.resources.R.drawable.ic_close),
                    contentDescription = CLOSE_ICON_DESCRIPTION,
                    tint = MaterialTheme.colors.onSurface
                )
            }
        },
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(com.zdravnica.uikit.resources.R.string.menu_screen_top_app_bar_title),
                    style = ZdravnicaAppTheme.typography.bodyNormalMedium,
                    color = MaterialTheme.colors.onSurface
                )
            }
        },
        actions = {
            Spacer(modifier = Modifier.width(ZdravnicaAppTheme.dimens.size60))
        }
    )
}

@Preview
@Composable
fun PreviewMenuTopAppBar() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        MenuTopAppBar {}
    }
}
