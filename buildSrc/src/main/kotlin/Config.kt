import org.gradle.api.JavaVersion

object Config {
    const val COMPILE_SDK_VERSION = 34
    const val APPLICATION_ID = "com.zdravnica.app"
    const val MIN_SDK_VERSION = 26
    const val TARGET_SDK_VERSION = 34
    const val VERSION_CODE = 2
    const val VERSION_NAME = "1.0.3"
    val JVM_TARGET = JavaVersion.VERSION_17.toString()
    const val KOTLIN_COMPILER_EXTENSION_VERSION = "1.5.13"
    const val EXCLUDES = "/META-INF/{AL2.0,LGPL2.1}"
}
