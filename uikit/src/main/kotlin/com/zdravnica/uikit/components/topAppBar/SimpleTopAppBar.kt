package com.zdravnica.uikit.components.topAppBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.CLOSE_ICON_DESCRIPTION
import com.zdravnica.uikit.resources.R

@Composable
fun SimpleTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onNavigateUp: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(
                onClick = {
                    onNavigateUp()
                }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_close),
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
                    text = title,
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
fun PreviewSimpleTopAppBar() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        SimpleTopAppBar {}
    }
}