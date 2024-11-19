package com.zdravnica.uikit.components.push

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme

@Composable
fun ProcedureStateInfo(
    modifier: Modifier = Modifier,
    firstText: String,
    secondText: String? = null,
    procedureEnded: Boolean = false
) {
    val backgroundColor = if (!procedureEnded) {
        ZdravnicaAppTheme.colors.baseAppColor.info500
    } else {
        ZdravnicaAppTheme.colors.baseAppColor.success500
    }

    val verticalPadding = if (!procedureEnded)
        ZdravnicaAppTheme.dimens.size20
    else
        ZdravnicaAppTheme.dimens.size20

    Box(
        modifier = modifier
            .padding(horizontal = ZdravnicaAppTheme.dimens.size12)
            .shadow(
                elevation = ZdravnicaAppTheme.dimens.size8,
                shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size24)
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size24)
            )
            .padding(
                start = ZdravnicaAppTheme.dimens.size16,
                end = ZdravnicaAppTheme.dimens.size16,
                top = verticalPadding,
                bottom = verticalPadding
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = if (secondText == null)
                Alignment.CenterHorizontally
            else
                Alignment.Start
        ) {
            Text(
                text = firstText,
                style = ZdravnicaAppTheme.typography.bodyMediumBold.copy(color = Color.White),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            secondText?.let {
                Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size4))
                Text(
                    text = it,
                    style = ZdravnicaAppTheme.typography.bodyNormalRegular.copy(color = Color.White),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProcedureStateInfo() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        Column {
            ProcedureStateInfo(
                firstText = "Only First Text",
                procedureEnded = false
            )
            ProcedureStateInfo(
                firstText = "First Text",
                secondText = "Second Text",
                procedureEnded = true
            )
        }
    }
}
