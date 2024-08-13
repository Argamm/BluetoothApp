package com.zdravnica.app.screens.connecting_page.menuScreen.viewModels

sealed interface MenuScreenSideEffect {

    data object OnNavigateToConnectionScreen : MenuScreenSideEffect

    data object OnNavigateUp : MenuScreenSideEffect

    data object OnSiteClick : MenuScreenSideEffect

    data object OnEmailClick : MenuScreenSideEffect

    data object OnCallClick : MenuScreenSideEffect
}