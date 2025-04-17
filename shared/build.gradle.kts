import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.declarative.dsl.schema.FqName.Empty.packageName
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("com.codingfeline.buildkonfig")
}

buildkonfig {
    packageName = "dev.rivu.golpoai.config"

    defaultConfigs {
        buildConfigField(
            Type.STRING,
            "GEMINI_API_KEY",
            gradleLocalProperties(rootDir, providers).getProperty("GEMINI_API_KEY") ?: "MISSING_API_KEY"
        )
    }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.shreyasp.generativeai.gemini)
            implementation(libs.koin.core) // use latest stable
            implementation(libs.touchlab.kermit)
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotest.framework.engine)
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.framework.api)
                implementation(libs.kotest.framework.datatest)
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(libs.mockk)
                implementation(libs.kotest.runner.junit5)
                implementation(libs.kotest.framework.engine)
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.framework.api)
                implementation(libs.kotest.framework.datatest)
            }
        }
    }
}

android {
    namespace = "dev.rivu.golpoai.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

