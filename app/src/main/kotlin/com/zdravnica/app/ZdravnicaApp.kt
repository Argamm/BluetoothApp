package com.zdravnica.app

import android.app.Application
import com.zdravnica.app.di.appKoinModules
import com.zdravnica.resources.ui.theme.models.LocalLanguageEnum
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.Locale

class ZdravnicaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val conf = resources.configuration
        conf.setLocale(Locale(LocalLanguageEnum.RUSSIAN.languageISO))

        startKoin {
            androidLogger()
            androidContext(createConfigurationContext(conf))
            modules(appKoinModules)
        }
    }
}