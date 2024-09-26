package com.zdravnica.app.di

import com.zdravnica.app.screens.procedureProcess.viewModels.ProcedureProcessViewModel
import com.zdravnica.app.screens.connecting_page.viewmodels.ConnectingPageViewModel
import com.zdravnica.app.screens.menuScreen.viewModels.MenuScreenViewModel
import com.zdravnica.app.screens.preparingTheCabin.viewModels.PreparingTheCabinScreenViewModel
import com.zdravnica.app.screens.procedure.viewModels.ProcedureScreenViewModel
import com.zdravnica.app.screens.selectProcedure.viewModels.SelectProcedureViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val zdravnicaViewModelsKoinModules = module {
    viewModelOf(::ConnectingPageViewModel)
    viewModel { SelectProcedureViewModel(get(), get()) }
    viewModelOf(::MenuScreenViewModel)
    viewModel { ProcedureScreenViewModel(get(), get()) }
    viewModel { PreparingTheCabinScreenViewModel(get(), get(), get()) }
    viewModel { ProcedureProcessViewModel(get(), get(), get()) }
}