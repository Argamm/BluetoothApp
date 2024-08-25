package com.zdravnica.uikit.components.statusDetails

import com.zdravnica.uikit.resources.R

enum class StatusInfoState {
    SENSOR_ERROR,
    THERMOSTAT_ACTIVATION,
    TEMPERATURE_EXCEEDED,
    CONNECTION_LOST
}

val stateDataMap = mapOf(
    StatusInfoState.SENSOR_ERROR to StatusInfoData(
        icon = R.drawable.ic_error_with_borders,
        stateInfo = R.string.status_info_sensor_error,
        instruction = R.string.status_info_sensor_error_instruction
    ),
        StatusInfoState.THERMOSTAT_ACTIVATION to StatusInfoData(
        icon = R.drawable.ic_error_with_borders,
        stateInfo = R.string.status_info_thermostat_activation,
        instruction = R.string.status_info_thermostat_activation_instruction
    ),
    StatusInfoState.TEMPERATURE_EXCEEDED to StatusInfoData(
        icon = R.drawable.ic_error_with_borders,
        stateInfo = R.string.status_info_temperature_exceeded,
        instruction = R.string.status_info_temperature_exceeded_instruction
    ),
    StatusInfoState.CONNECTION_LOST to StatusInfoData(
        icon = R.drawable.ic_bluetooth,
        stateInfo = R.string.status_info_connection_lost,
        instruction = R.string.status_info_connection_lost_instruction
    )
)