@file:Suppress("UnstableApiUsage")

plugins {
    id(Plugins.LIBRARY_PLUGIN)
    id(Plugins.KOTLIN_PLUGIN)
    id(Plugins.KOTLIN_KAPT)
}

android {
    compileSdk = Config.COMPILE_SDK_VERSION
    namespace = "com.zdravnica.bluetooth"

    defaultConfig {
        minSdk = Config.MIN_SDK_VERSION
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            consumerProguardFiles(
                "${rootProject.rootDir.absolutePath}/app/proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
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
    packaging {
        resources {
            excludes += Config.EXCLUDES
        }
    }
}

dependencies {
    // Koin main features for Android
    implementation(Dependencies.Koin.KOIN_ANDROID)
    implementation(Dependencies.Koin.KOIN_COMPOSE)
    implementation(Dependencies.Koin.KOIN_CORE)

}