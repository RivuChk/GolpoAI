import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type
import org.gradle.declarative.dsl.schema.FqName.Empty.packageName
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.konan.target.KonanTarget


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("com.codingfeline.buildkonfig")
}


buildkonfig {
    packageName = "dev.rivu.genai.kmpllminterferencelib.config"

    defaultConfigs {
        buildConfigField(
            Type.STRING,
            "modelUrl",
            gradleLocalProperties(rootDir, providers).getProperty("modelUrl") ?: "MISSING_API_KEY"
        )
    }
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions.jvmTarget = JvmTarget.JVM_17.target
        }

    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core) // use latest stable
            implementation(libs.touchlab.kermit)

            implementation(libs.kotlinx.coroutines.core)

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

        androidMain.dependencies {
            implementation(libs.solutions.genai)
            implementation(libs.koin.android) // use latest stable

        }

        iosMain.dependencies {

        }

    }


}

android {
    namespace = "dev.rivu.genai.kmpllminterferencelib"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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


