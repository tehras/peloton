import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://jitpack.io") }
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha06")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}")
    }
}

subprojects {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
        maven { url = uri("https://jitpack.io") }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            // Allow warnings when running from IDE, makes it easier to experiment.
            allWarningsAsErrors = true

            jvmTarget = "1.8"
            freeCompilerArgs = freeCompilerArgs + listOf("-Xallow-jvm-ir-dependencies")
        }
    }
}
