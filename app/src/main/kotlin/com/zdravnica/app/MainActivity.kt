package com.zdravnica.app

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.os.LocaleListCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.zdravnica.app.navigation.app.navgraphs.AppNavGraph
import com.zdravnica.app.navigation.app.root.RootNavigationGraph
import com.zdravnica.app.utils.LocalBackPressedDispatcher
import com.zdravnica.resources.ui.theme.models.LocalLanguageEnum
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.DELAY_DURATION_1200
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        installSplashScreen().apply {
            setOnExitAnimationListener { splashScreen ->
                lifecycleScope.launch {
                    delay(DELAY_DURATION_1200)
                    splashScreen.remove()
                }
            }
        }

        setContent {
            val currentLanguage by remember { mutableStateOf(LocalLanguageEnum.RUSSIAN) }
            val isDarkModeValue = isSystemInDarkTheme()
            val isDarkMode by remember { mutableStateOf(isDarkModeValue) }
            val rootNavController = rememberNavController()
            setLocaleConfiguration(currentLanguage)

            CompositionLocalProvider(
                LocalBackPressedDispatcher provides this@MainActivity.onBackPressedDispatcher
            ) {
                ZdravnicaAppExerciseTheme(
                    darkThem = isDarkMode
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(ZdravnicaAppTheme.colors.primaryBackgroundColor),
                        color = ZdravnicaAppTheme.colors.primaryBackgroundColor
                    ) {
                        RootNavigationGraph(
                            navHostController = rootNavController,
                            startDestination = AppNavGraph.Connection.route
                        )
                    }
                }
            }
        }
    }

    private fun setLocaleConfiguration(currentLanguage: LocalLanguageEnum) {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(
                currentLanguage.languageISO
            )
        )

        Locale.setDefault(Locale(currentLanguage.languageISO))
    }
}
