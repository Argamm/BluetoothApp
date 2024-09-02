package com.zdravnica.app.di

import android.content.Context
import android.content.SharedPreferences
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.data.SharedPreferencesDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences("SelectProcedurePreferences", Context.MODE_PRIVATE)
    }
    single<LocalDataStore> {
        SharedPreferencesDataStore(get())
    }
}
