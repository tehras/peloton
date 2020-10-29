plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("plugin.serialization")
}

apply(from = rootProject.file("gradle/configure-android.gradle"))

dependencies {
    implementation(Koin.core)
    implementation(Kotlin.stdLib)
    implementation(Kotlin.coroutinesCore)
    implementation(Kotlin.coroutinesAndroid)
    implementation(Kotlin.serialization)
    implementation(Kotlin.serializationJson)

    implementation(Retrofit.core)
    implementation(Retrofit.serialization)
}