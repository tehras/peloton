plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
}

apply(from = rootProject.file("gradle/configure-android.gradle"))
apply(from = rootProject.file("gradle/configure-compose.gradle"))

dependencies {
    implementation(Android.activityKtx)
    implementation(Android.appcompat)
    implementation(Android.coil)
    implementation(Android.coreKtx)
    implementation(Android.lifecycleExt)
    implementation(Android.viewModelKtx)
    implementation(Charts.common)
    implementation(Charts.line)
    implementation(Charts.pie)
    implementation(Compose.core)
    implementation(Compose.layout)
    implementation(Compose.material)
    implementation(Compose.foundation)
    implementation(Compose.runtime)
    implementation(Koin.android)
    implementation(Koin.core)
    implementation(Koin.compose)
    implementation(Koin.viewModel)
    implementation(Koin.scope)
    implementation(Kotlin.stdLib)
    implementation(Kotlin.serialization)

    implementation(project(":data"))

    debugImplementation(Compose.tooling)
}