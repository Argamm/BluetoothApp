plugins {
    id(Plugins.APPLICATION_PLUGIN)
    id(Plugins.KOTLIN_PLUGIN)
    id(Plugins.KOTLIN_KAPT)
}

android {
    namespace = "com.zdravnica.app"
    compileSdk = Config.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = Config.MIN_SDK_VERSION
        targetSdk = Config.TARGET_SDK_VERSION
        versionCode = Config.VERSION_CODE
        versionName = Config.VERSION_NAME
        setProperty("archivesBaseName", "Zdravnica-$versionName")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = Config.JVM_TARGET
    }
    sourceSets.all {
        java.srcDir("src/$name/kotlin")
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Config.KOTLIN_COMPILER_EXTENSION_VERSION
    }
    packaging  {
        resources {
            excludes += Config.EXCLUDES
        }
    }
}

dependencies {

    implementation(project(":uikit"))
    implementation(project(":uikit:resources"))
    implementation(project(":bluetooth"))

    implementation(Dependencies.Android.APP_COMPACT)
    implementation(Dependencies.Android.APP_COMPACT_ACTIVITY_RESOURCES)
    implementation(Dependencies.Android.SPLASH_SCREEN)

    // Firebase
    implementation(platform(Dependencies.Firebase.FIREBASE_BOOM))
    implementation(Dependencies.Firebase.FIREBASE_ANALYTICS)
    implementation(Dependencies.Firebase.FIREBASE_CRASHLYTICS)
    implementation(Dependencies.Firebase.FIREBASE_REMOTE_CONFIG)
    // Compose
    val composeBoomPlatform = platform(Dependencies.Compose.BOOM)
    implementation(composeBoomPlatform)
    implementation(Dependencies.Compose.UI)
    implementation(Dependencies.Compose.MATERIAL_2)
    implementation(Dependencies.Compose.PREVIEW_TOOLING)
    implementation(Dependencies.Compose.FOUNDATION)
    implementation(Dependencies.Compose.MATERIAL_ICONS_CORE)
    implementation(Dependencies.Compose.MATERIAL_ICONS_EXTENDED)
    implementation(Dependencies.Compose.CONSTRAINT_LAYOUT)
    implementation(Dependencies.Compose.VIEW_MODEL)
    implementation(Dependencies.Compose.ACCOMPANIST_SYSTEM_UI_CONTROLLER)
    implementation(Dependencies.Compose.ACTIVITY_COMPOSE)
    implementation(Dependencies.Compose.LIFECYCLE_RUNTIME_COMPOSE)
    implementation(Dependencies.Compose.NAVIGATION)
    implementation(Dependencies.Android.KTX_CORE)
    implementation(Dependencies.Android.RUNTIME_LIFECYCLE)
    // Koin main features for Android
    implementation(Dependencies.Koin.KOIN_ANDROID)
    implementation(Dependencies.Koin.KOIN_COMPOSE)
    implementation(Dependencies.Koin.KOIN_CORE)
    implementation(Dependencies.Koin.KOIN_LOGGER)
    // Timber
    implementation(Dependencies.Timber.TIMBER)
    // Orbit
    implementation(Dependencies.Orbit.CORE)
    implementation(Dependencies.Orbit.VIEW_MODEL)
    implementation(Dependencies.Orbit.COMPOSE)
    // Test
    testImplementation(Dependencies.Koin.KOIN_TEST)
}