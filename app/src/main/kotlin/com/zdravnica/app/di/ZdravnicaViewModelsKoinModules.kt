package com.zdravnica.app.di

import com.zdravnica.app.screens.connecting_page.procedure.viewModels.ProcedureScreenViewModel
import com.zdravnica.app.screens.connecting_page.viewmodels.ConnectingPageViewModel
import com.zdravnica.app.screens.menuScreen.viewModels.MenuScreenViewModel
import com.zdravnica.app.screens.selectProcedure.viewModels.SelectProcedureViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val zdravnicaViewModelsKoinModules = module {
    viewModelOf(::ConnectingPageViewModel)
    viewModelOf(::SelectProcedureViewModel)
    viewModelOf(::MenuScreenViewModel)
    viewModelOf(::ProcedureScreenViewModel)
}