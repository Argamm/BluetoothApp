package com.zdravnica.uikit.components.buttons.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme

@Composable
fun ActionDialogButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    textColor: Color = Color.Unspecified,
    shape: RoundedCornerShape = RoundedCornerShape(0.dp),
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        shape = shape,
        elevation = null,
        modifier = modifier
    ) {
        Text(
            text = text,
            color = textColor,
            style = ZdravnicaAppTheme.typography.bodyMediumSemi,
            modifier = Modifier.padding(paddingValues)
        )
    }
}