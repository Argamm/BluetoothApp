package com.zdravnica.app.screens.menuScreen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.COUNT_ONE
import com.zdravnica.uikit.resources.R

@Composable
fun MenuConnectToUs(
    modifier: Modifier = Modifier,
    onSiteClick: () -> Unit,
    onEmailClick: () -> Unit,
    onCallClick: () -> Unit
) {
    BoxWithConstraints {
        val maxTextWidth = maxWidth / CARDS_TITLE_COUNT - (ZdravnicaAppTheme.dimens.size14 + ZdravnicaAppTheme.dimens.size16)

        Row(
            modifier = modifier
                .padding(ZdravnicaAppTheme.dimens.size16)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            ConnectColumn(
                icon = ImageVector.vectorResource(id = R.drawable.ic_network),
                text = stringResource(R.string.menu_screen_site),
                onClick = onSiteClick,
                width = maxTextWidth
            )
            Spacer(modifier = Modifier.width(ZdravnicaAppTheme.dimens.size8))

            ConnectColumn(
                icon = ImageVector.vectorResource(id = R.drawable.ic_email),
                text = stringResource(R.string.menu_screen_email),
                onClick = onEmailClick,
                width = maxTextWidth
            )
            Spacer(modifier = Modifier.width(ZdravnicaAppTheme.dimens.size8))

            ConnectColumn(
                icon = ImageVector.vectorResource(id = R.drawable.ic_phone),
                text = stringResource(R.string.menu_screen_call),
                onClick = onCallClick,
                width = maxTextWidth
            )
        }
    }
}

@Composable
fun ConnectColumn(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    width: Dp
) {
    Card(
        elevation = ZdravnicaAppTheme.dimens.size4,
        shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size24),
        backgroundColor = Color.White,
        modifier = modifier
            .padding(ZdravnicaAppTheme.dimens.size4)
            .width(width)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .height(IntrinsicSize.Min)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(
                    top = ZdravnicaAppTheme.dimens.size25,
                    bottom = ZdravnicaAppTheme.dimens.size25,
                    start = ZdravnicaAppTheme.dimens.size8,
                    end = ZdravnicaAppTheme.dimens.size8
                )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = ZdravnicaAppTheme.colors.baseAppColor.gray200,
                modifier = Modifier.size(ZdravnicaAppTheme.dimens.size24)
            )
            Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size4))
            Text(
                text = text,
                style = ZdravnicaAppTheme.typography.bodyNormalMedium,
                color = ZdravnicaAppTheme.colors.baseAppColor.gray200,
                textAlign = TextAlign.Center,
                maxLines = COUNT_ONE,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

const val CARDS_TITLE_COUNT = 3

@Preview
@Composable
fun PreviewMenuConnectToUs() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        MenuConnectToUs(
            onSiteClick = { },
            onEmailClick = { },
            onCallClick = { }
        )
    }
}
