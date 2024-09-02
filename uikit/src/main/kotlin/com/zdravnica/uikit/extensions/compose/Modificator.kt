package com.zdravnica.uikit.extensions.compose

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import com.zdravnica.uikit.CHOOSE_EMAIL_APP
import com.zdravnica.uikit.COUNT_TWO
import com.zdravnica.uikit.EMAIL_DATA
import com.zdravnica.uikit.NO_EMAIL_APP
import com.zdravnica.uikit.ONE_MINUTE_IN_SEC
import com.zdravnica.uikit.PHONE_CALL_DATA
import com.zdravnica.uikit.resources.R

fun Modifier.clickableSingle(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    isEnabledIndication: Boolean = true,
    onClick: () -> Unit
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
        properties["isEnabledIndication"] = isEnabledIndication
    }
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }

    this.then(
        Modifier.clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            onClick = { multipleEventsCutter.processEvent(onClick) },
            role = role,
            indication = if (isEnabledIndication) LocalIndication.current else null,
            interactionSource = remember {
                MutableInteractionSource()
            }
        )
    )
}

fun Int.formatAsValue(isMinutes: Boolean): String {
    return if (isMinutes) {
        val minutes = (this / ONE_MINUTE_IN_SEC).toString().padStart(COUNT_TWO, '0')
        val seconds = (this % ONE_MINUTE_IN_SEC).toString().padStart(COUNT_TWO, '0')
        "$minutes:$seconds"
    } else {
        "$this°"
    }
}

@Composable
fun calculateTimeText(totalSeconds: Int): String {
    val minutes = totalSeconds / ONE_MINUTE_IN_SEC
    val seconds = totalSeconds % ONE_MINUTE_IN_SEC
    return stringResource(R.string.procedure_process_time_format, minutes, seconds)
}

fun Context.sendEmailActivity(email: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = EMAIL_DATA
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    }
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(Intent.createChooser(intent, CHOOSE_EMAIL_APP))
    } else {
        Toast.makeText(this, NO_EMAIL_APP, Toast.LENGTH_SHORT).show()
    }
}

fun Context.callPhoneActivity(phone: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("$PHONE_CALL_DATA$phone")
    }
    startActivity(intent)
}
