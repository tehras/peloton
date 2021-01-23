@file:JvmName("Deps")

import Versions.chartsVersion
import Versions.koin
import Versions.kotlin
import Versions.kotlinCoroutine
import Versions.lifecycleVersion

object Versions {
  const val composeVersion = "1.4.21"
  const val compose = "1.0.0-alpha10"
  const val kotlin = "1.4.21"
  const val kotlinCoroutine = "1.4.0"
  const val targetSdk = 29
  const val buildVersion = "30.0.2"
  const val lifecycleVersion = "2.2.0"
  const val koin = "2.2.1"
  const val chartsVersion = "d34308d718"
}

object Compose {
  const val animation = "androidx.compose.animation:animation:${Versions.compose}"
  const val core = "androidx.compose.ui:ui:${Versions.compose}"
  const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
  const val layout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
  const val material = "androidx.compose.material:material:${Versions.compose}"
  const val materialIconsExt =
    "androidx.compose.material:material-icons-extended:${Versions.compose}"
  const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
  const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
}

object Android {
  const val appcompat = "androidx.appcompat:appcompat:1.2.0"
  const val activityKtx = "androidx.activity:activity-ktx:1.1.0"
  const val coil = "io.coil-kt:coil:1.0.0-rc3"
  const val coreKtx = "androidx.core:core-ktx:1.3.2"
  const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion"
  const val lifecycleExt = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
}

object Charts {
  const val line = "com.github.tehras.charts:line:$chartsVersion"
  const val common = "com.github.tehras.charts:common:$chartsVersion"
}

object Koin {
  const val android = "org.koin:koin-android:$koin"
  const val compose = "org.koin:koin-androidx-compose:$koin"
  const val core = "org.koin:koin-core:$koin"
  const val scope = "org.koin:koin-androidx-scope:$koin"
  const val viewModel = "org.koin:koin-androidx-viewmodel:$koin"
}

object Kotlin {
  const val serialization = "org.jetbrains.kotlin:kotlin-serialization:$kotlin"
  const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0"
  const val serializationRuntime = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:$kotlin"
  const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
  const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutine"
  const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinCoroutine"
}

object Retrofit {
  const val core = "com.squareup.retrofit2:retrofit:2.9.0"
  const val moshi = "com.squareup.retrofit2:converter-moshi:2.9.0"
  const val serialization =
    "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
}

object Moshi {
  const val core = "com.squareup.moshi:moshi:1.9.3"
  const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:1.9.3"
}