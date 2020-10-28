plugins {
    id("java-library")
    id("kotlin")
    kotlin("plugin.serialization")
}

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