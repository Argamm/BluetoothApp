
plugins {
    id(Plugins.LIBRARY_PLUGIN)
    id(Plugins.KOTLIN_PLUGIN)
    id(Plugins.KOTLIN_KAPT)
}


android {
    compileSdk = Config.COMPILE_SDK_VERSION
    namespace = "com.zdravnica.uikit.resources"

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
    // Compose
    val composeBoomPlatform = platform(Dependencies.Compose.BOOM)
    implementation(composeBoomPlatform)
    implementation(Dependencies.Compose.UI)
    implementation(Dependencies.Compose.FOUNDATION)
}