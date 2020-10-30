import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0-alpha15")
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
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            // Allow warnings when running from IDE, makes it easier to experiment.
            allWarningsAsErrors = true

            jvmTarget = "1.8"
            freeCompilerArgs =
                freeCompilerArgs + listOf("-Xallow-jvm-ir-dependencies", "-Xskip-prerelease-check")
        }
    }
}