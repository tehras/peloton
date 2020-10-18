import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("java-library")
    id("kotlin")
}

//apply(from = rootProject.file("gradle/configure-android.gradle"))

dependencies {
    implementation(Kotlin.stdLib)

    implementation(Retrofit.core)
    implementation(Retrofit.moshi)
    implementation(Moshi.core)
}