package com.zdravnica.app.screens.preparingTheCabin.viewModels

sealed interface PreparingTheCabinScreenSideEffect {
    data object OnNavigateToSelectProcedureScreen : PreparingTheCabinScreenSideEffect
    data object OnNavigateToCancelDialogPage : PreparingTheCabinScreenSideEffect
    data object OnNavigateToFailedTenCommandScreen : PreparingTheCabinScreenSideEffect
    data object OnNavigateToFailedTemperatureCommandScreen : PreparingTheCabinScreenSideEffect
    data object OnBluetoothConnectionLost : PreparingTheCabinScreenSideEffect
    data object OnThermostatActivation: PreparingTheCabinScreenSideEffect
    data object OnTemperatureSensorWarning: PreparingTheCabinScreenSideEffect
}