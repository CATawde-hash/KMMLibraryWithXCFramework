import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    kotlin("native.cocoapods")
    alias(libs.plugins.jetbrainsCompose)
    id("maven-publish")
}

group = "com.example"
version = "1.0"

publishing {
    repositories {
        maven {
            // Configure your Maven repository here
        }
    }
}

kotlin {
    android()
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "kmmLibraryshared"
            xcf.add(this)
            isStatic = true
        }
    }

    // Due to https://issuetracker.google.com/u/0/issues/342905180
    sourceSets.commonMain {
        kotlin.srcDir("build/generated/ksp/metadata")
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled) //for sqlite drivers related

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.material3)

            api(libs.mvvm.core) // only ViewModel, EventsDispatcher, Dispatchers.UI
            api(libs.mvvm.compose) // api mvvm-core, getViewModel for Compose Multiplatfrom
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.kamel.image)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.koin.core)

            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")
            implementation("io.coil-kt.coil3:coil-compose:3.0.0-alpha06")

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation("androidx.core:core-ktx:1.9.0")

            implementation(libs.ktor.client.android)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.androidx.core.ktx)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.koin.core)
        }

        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
    }
}

android {
    namespace = "com.example.kmmlibrarywithxcframework"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}
/*dependencies {
    implementation(libs.androidx.runtime.android)
}*/
