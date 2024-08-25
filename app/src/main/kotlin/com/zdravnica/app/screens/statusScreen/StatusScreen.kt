package com.zdravnica.app.screens.statusScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.components.statusDetails.StatusInfoState
import com.zdravnica.uikit.components.statusDetails.stateDataMap

@Composable
fun StatusScreen(
    modifier: Modifier = Modifier,
    state: StatusInfoState = StatusInfoState.THERMOSTAT_ACTIVATION,
    onCloseClick: () -> Unit,
    onSupportClick: () -> Unit,
    onYesClick: () -> Unit,
) {
    val data = stateDataMap[state]!!

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopAppBar(
            elevation = 0.dp,
            backgroundColor = Color.White,
            title = {},
            navigationIcon = {
                IconButton(onClick = { onCloseClick() }) {
                    Image(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.size(ZdravnicaAppTheme.dimens.size24)
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size126))

        Image(
            painter = painterResource(id = data.icon),
            contentDescription = null,
            modifier = Modifier
                .size(ZdravnicaAppTheme.dimens.size200)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size18))

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        brush = Brush.linearGradient(
                            colors = ZdravnicaAppTheme.colors.timeAndTemperatureColor
                        )
                    )
                ) {
                    append(stringResource(id = data.stateInfo))
                }
            },
            style = ZdravnicaAppTheme.typography.headH4,
            modifier = Modifier.padding(horizontal = ZdravnicaAppTheme.dimens.size36)
        )

        Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size8))

        Text(
            text = stringResource(id = data.instruction),
            style = ZdravnicaAppTheme.typography.bodyNormalRegular,
            color = ZdravnicaAppTheme.colors.baseAppColor.gray300,
            modifier = Modifier.padding(horizontal = ZdravnicaAppTheme.dimens.size36)
        )

        Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size81))

        BottomButtons(
            onSupportClick = onSupportClick,
            onActionClick = onYesClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStatusScreen() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        StatusScreen(
            state = StatusInfoState.THERMOSTAT_ACTIVATION,
            onCloseClick = {},
            onSupportClick = {},
            onYesClick = {},
        )
    }
}
