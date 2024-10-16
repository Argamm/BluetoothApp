package com.zdravnica.app.screens.menuScreen.ui

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.ERROR_ICON_DESCRIPTION
import com.zdravnica.uikit.ORDER_DESCRIPTION
import com.zdravnica.uikit.components.buttons.ui.OrderBalmButton
import com.zdravnica.uikit.resources.R

@Composable
fun MenuBalms(
    modifier: Modifier = Modifier,
    firstBalmCount: Int,
    secondBalmCount: Int,
    thirdBalmCount: Int,
    onOrderClick: () -> Unit,
    onBalmFilledClick: () -> Unit,
) {
    val zeroTextVisible = remember { mutableStateOf(false) }
    zeroTextVisible.value = firstBalmCount == 0 || secondBalmCount == 0 || thirdBalmCount == 0

    Card(
        elevation = ZdravnicaAppTheme.dimens.size4,
        shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size24),
        backgroundColor = Color.White,
        modifier = modifier
            .padding(top = ZdravnicaAppTheme.dimens.size16)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(ZdravnicaAppTheme.dimens.size16)
        ) {
            Text(
                text = stringResource(R.string.menu_screen_balm_title),
                style = ZdravnicaAppTheme.typography.bodyNormalMedium,
                color = ZdravnicaAppTheme.colors.baseAppColor.gray500
            )

            Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size16))
            BalmRow(stringResource(R.string.menu_screen_burdock), secondBalmCount)
            BalmRow(stringResource(R.string.menu_screen_nut), firstBalmCount)
            BalmRow(stringResource(R.string.menu_screen_mint), thirdBalmCount)

            if (zeroTextVisible.value) {
                Text(
                    modifier = Modifier.padding(top = ZdravnicaAppTheme.dimens.size12),
                    text = stringResource(R.string.menu_screen_add_some_balm),
                    style = ZdravnicaAppTheme.typography.bodyNormalMedium,
                    color = ZdravnicaAppTheme.colors.baseAppColor.error500
                )
            }

            OrderBalmButton(
                modifier = Modifier.padding(top = ZdravnicaAppTheme.dimens.size12),
                isDisabled = false,
                contentDescription = ORDER_DESCRIPTION,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus),
                text = if (zeroTextVisible.value)
                    stringResource(R.string.menu_screen_balm_filled)
                else
                    stringResource(R.string.menu_screen_order_balm),
                onClick = {
                    if (zeroTextVisible.value) {
                        onBalmFilledClick()
                    } else {
                        onOrderClick()
                    }
                }
            )

            if (zeroTextVisible.value) {
                Text(
                    modifier = Modifier
                        .padding(top = ZdravnicaAppTheme.dimens.size12),
                    text = stringResource(R.string.menu_screen_add_balm_instruction),
                    style = ZdravnicaAppTheme.typography.bodyNormalMedium,
                    color = ZdravnicaAppTheme.colors.baseAppColor.gray500,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun BalmRow(label: String, balmValue: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = ZdravnicaAppTheme.typography.bodyNormalMedium,
            color = ZdravnicaAppTheme.colors.baseAppColor.gray200
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.menu_screen_balm_count, balmValue),
                style = ZdravnicaAppTheme.typography.bodyNormalBold,
                color = if (balmValue != 0) ZdravnicaAppTheme.colors.baseAppColor.gray200
                else ZdravnicaAppTheme.colors.baseAppColor.gray500
            )
            if (balmValue == 0) {
                Spacer(modifier = Modifier.width(ZdravnicaAppTheme.dimens.size8))
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_error),
                    contentDescription = ERROR_ICON_DESCRIPTION,
                    tint = ZdravnicaAppTheme.colors.baseAppColor.gray500,
                    modifier = Modifier.size(ZdravnicaAppTheme.dimens.size18)
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size8))
}

@Preview
@Composable
fun PreviewMenuBalms() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        MenuBalms(
            firstBalmCount = 6,
            secondBalmCount = 6,
            thirdBalmCount = 6,
            onOrderClick = {},
            onBalmFilledClick = {},
        )
    }
}
