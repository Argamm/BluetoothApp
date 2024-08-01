object Dependencies {
    object Android {
        private const val KTX_VERSION = "1.7.0"
        private const val LIFECYCLE_VERSION = "2.4.1"
        private const val J_UNIT_VERSION = "4.13.2"
        private const val J_UNIT_TEST_VERSION = "1.1.3"
        private const val ESPRESSO_VERSION = "3.4.0"
        private const val GSON_VERSION = "2.9.0"
        private const val APP_COMPACT_VERSION = "1.6.1"
        private const val SPLASH_SCREEN_VERSION = "1.0.1"

        const val KTX_CORE = "androidx.core:core-ktx:$KTX_VERSION"
        const val RUNTIME_LIFECYCLE = "androidx.lifecycle:lifecycle-runtime-ktx:$LIFECYCLE_VERSION"
        const val J_UNIT = "junit:junit:$J_UNIT_VERSION"
        const val J_UNIT_TEST = "androidx.test.ext:junit:$J_UNIT_TEST_VERSION"
        const val ESPRESSO = "androidx.test.espresso:espresso-core:$ESPRESSO_VERSION"
        const val APP_COMPACT_ACTIVITY_RESOURCES =
            "androidx.appcompat:appcompat-resources:$APP_COMPACT_VERSION"
        const val APP_COMPACT = "androidx.appcompat:appcompat:$APP_COMPACT_VERSION"
        const val SPLASH_SCREEN = "androidx.core:core-splashscreen:$SPLASH_SCREEN_VERSION"
    }


    object Compose {
        private const val ACCOMPANIST_VERSION = "0.31.2-alpha"
        private const val LIFECYCLE_RUNTIME_COMPOSE_VERSION = "2.6.0"
        private const val ACTIVITY_COMPOSE_VERSION = "1.9.0"
        private const val NAVIGATION_VERSION = "2.7.5"
        private const val COMPOS_VIEW_MODEL_VERSION = "2.6.1"
        private const val CONSTRAINT_LAYOUT_VERSION = "1.0.0"
        private const val COMPOSE_BOOM_VERSION = "2024.05.00"
        private const val COMPOSE_FOUNDATION_VERSION = "1.6.2"
        private const val SLIDER_COMPOSE_VERSION = "1.2.0"
        private const val PAGING_3_VERSION = "3.2.0"
        private const val CALENDAR_VERSION = "2.5.0"


        const val BOOM = "androidx.compose:compose-bom:$COMPOSE_BOOM_VERSION"
        const val UI = "androidx.compose.ui:ui"
        const val MATERIAL_2 = "androidx.compose.material:material"
        const val PREVIEW_TOOLING = "androidx.compose.ui:ui-tooling-preview"
        const val UI_TOOLING = "androidx.compose.ui:ui-tooling"
        const val J_UNIT = "androidx.compose.ui:ui-test-junit4"
        const val FOUNDATION = "androidx.compose.foundation:foundation:$COMPOSE_FOUNDATION_VERSION"
        const val MATERIAL_ICONS_CORE =
            "androidx.compose.material:material-icons-core"
        const val MATERIAL_ICONS_EXTENDED =
            "androidx.compose.material:material-icons-extended"
        const val MATERIAL_3 = "androidx.compose.material3:material3"
        const val CONSTRAINT_LAYOUT =
            "androidx.constraintlayout:constraintlayout-compose:$CONSTRAINT_LAYOUT_VERSION"
        const val VIEW_MODEL =
            "androidx.lifecycle:lifecycle-viewmodel-compose:$COMPOS_VIEW_MODEL_VERSION"
        const val ACCOMPANIST_SYSTEM_UI_CONTROLLER =
            "com.google.accompanist:accompanist-systemuicontroller:$ACCOMPANIST_VERSION"
        const val NAVIGATION = "androidx.navigation:navigation-compose:$NAVIGATION_VERSION"
        const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:$ACTIVITY_COMPOSE_VERSION"
        const val LIFECYCLE_RUNTIME_COMPOSE =
            "androidx.lifecycle:lifecycle-runtime-compose:$LIFECYCLE_RUNTIME_COMPOSE_VERSION"
    }

    object Koin {
        private const val KOIN_VERSION = "3.3.2"
        private const val KOIN_LOGGER_VERSION = "3.3.1"
        private const val KOIN_ANDROID_X_COMPOSE_VERSION = "3.4.2"
        private const val KOIN_TEST_VERSION = "3.3.3"

        const val KOIN_ANDROID = "io.insert-koin:koin-android:$KOIN_VERSION"
        const val KOIN_CORE = "io.insert-koin:koin-core:$KOIN_VERSION"
        const val KOIN_COMPOSE =
            "io.insert-koin:koin-androidx-compose:$KOIN_ANDROID_X_COMPOSE_VERSION"
        const val KOIN_LOGGER = "io.insert-koin:koin-logger-slf4j:$KOIN_LOGGER_VERSION"
        const val KOIN_TEST = "io.insert-koin:koin-test-junit4:$KOIN_TEST_VERSION"
    }

    object Coroutines {
        private const val COROUTINE_VERSION = "1.6.4"
        private const val COROUTINE_TEST_VERSION = "1.6.1"

        const val COROUTINES_CORE =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINE_VERSION"
        const val COROUTINES_ANDROID =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINE_VERSION"
        const val TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$COROUTINE_TEST_VERSION"
    }

    object DataStore {
        private const val DATASTORE_VERSION = "1.0.0"

        const val DATASTORE_PREFERENCES =
            "androidx.datastore:datastore-preferences:$DATASTORE_VERSION"
        const val DATASTORE_PREFERENCES_CORE =
            "androidx.datastore:datastore-preferences-core:$DATASTORE_VERSION"
    }

    object Room {
        private const val ROOM_VERSION = "2.6.0"

        const val RUNTIME = "androidx.room:room-runtime:$ROOM_VERSION"
        const val COMPILER_KAPT = "androidx.room:room-compiler:$ROOM_VERSION"
        const val KTX = "androidx.room:room-ktx:$ROOM_VERSION"
    }

    object Orbit {
        private const val ORBIT_VERSION = "4.3.2"

        const val CORE = "org.orbit-mvi:orbit-core:$ORBIT_VERSION"
        const val VIEW_MODEL = "org.orbit-mvi:orbit-viewmodel:$ORBIT_VERSION"
        const val TEST_IMPLEMENTATION = "org.orbit-mvi:orbit-test:$ORBIT_VERSION"
        const val COMPOSE = "org.orbit-mvi:orbit-compose:$ORBIT_VERSION"
    }

    object Coil {
        private const val COIL_VERSION = "2.5.0"

        const val COIL_COMPOSE = "io.coil-kt:coil-compose:$COIL_VERSION"
    }


    object Timber {
        private const val TIMBER_VERSION = "5.0.1"

        const val TIMBER = "com.jakewharton.timber:timber:$TIMBER_VERSION"
    }


    object JodaTime {
        val JODA_TIME = "joda-time:joda-time" version "2.12.1"
    }


    object KotlinXCollections {
        private const val VERSION = "0.3.5"
        const val IMMUTABLE = "org.jetbrains.kotlinx:kotlinx-collections-immutable:$VERSION"
    }

    object MaterialDialogCompose {
        private const val VERSION = "0.8.1-rc"
        const val DATE_TIME =
            "io.github.vanpra.compose-material-dialogs:datetime:$VERSION"
    }

    object Firebase {
        private const val BOOM_VERSION = "28.4.1"
        const val FIREBASE_BOOM = "com.google.firebase:firebase-bom:$BOOM_VERSION"
        const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics"
        const val FIREBASE_CRASHLYTICS = "com.google.firebase:firebase-crashlytics"
        const val FIREBASE_MASSAGING = "com.google.firebase:firebase-messaging"
        const val FIREBASE_REMOTE_CONFIG = "com.google.firebase:firebase-config-ktx"
    }

    object LottieAnimation {
        private const val VERSION = "6.3.0"
        const val Lottie = "com.airbnb.android:lottie-compose:$VERSION"
    }


    private infix fun String.version(version: String) = "$this:$version"
}
