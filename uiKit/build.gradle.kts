plugins {
    id(Plugins.LIBRARY_PLUGIN)
    id(Plugins.KOTLIN_PLUGIN)
    id(Plugins.KOTLIN_KAPT)
}

android {
    namespace = "com.zdravnica.uikit"
    compileSdk = Config.COMPILE_SDK_VERSION
    version = Config.VERSION_NAME

    defaultConfig {
        minSdk = Config.MIN_SDK_VERSION
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    @Suppress("UnstableApiUsage")
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
    implementation(project(":uiKit:resources"))

    // Compose
    val composeBoomPlatform = platform(Dependencies.Compose.BOOM)
    implementation(composeBoomPlatform)
    implementation(Dependencies.Compose.UI)
    implementation(Dependencies.Compose.MATERIAL_2)
    implementation(Dependencies.Compose.MATERIAL_3)
    implementation(Dependencies.Compose.PREVIEW_TOOLING)
    implementation(Dependencies.Compose.FOUNDATION)
    implementation(Dependencies.Compose.MATERIAL_ICONS_CORE)
    implementation(Dependencies.Compose.MATERIAL_ICONS_EXTENDED)
    implementation(Dependencies.Compose.CONSTRAINT_LAYOUT)
    implementation(Dependencies.Compose.ACCOMPANIST_SYSTEM_UI_CONTROLLER)
    implementation(Dependencies.MaterialDialogCompose.DATE_TIME)
    implementation(Dependencies.Compose.NAVIGATION)
    debugImplementation(Dependencies.Compose.UI_TOOLING)
    // image loader coil
    implementation(Dependencies.Coil.COIL_COMPOSE)
    // Test
    androidTestImplementation(composeBoomPlatform)
    androidTestImplementation(Dependencies.Compose.J_UNIT)
}