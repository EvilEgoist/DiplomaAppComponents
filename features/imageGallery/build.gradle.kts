plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "ru.diploma.appcomponents.features.imageGallery"
    compileSdk = SdkVersions.COMPILE_SDK

    with (defaultConfig) {
        minSdk = SdkVersions.MIN_SDK
        targetSdk = SdkVersions.TARGET_SDK
        versionCode = Releases.VERSION_CODE
        versionName = Releases.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "API_KEY", properties["API_KEY"].toString())
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                // List additional ProGuard rules for the given build type here. By default,
                // Android Studio creates and includes an empty rules file for you (located
                // at the root directory of each module).
                "proguard-rules.pro"
            )
        }

    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":core:network"))

    implementation(libs.kotlinx.serialization.converter)
    implementation(libs.kotlinx.serialization)
    implementation(libs.bundles.android.core)
    implementation(libs.room.paging.compose)
    implementation(libs.bundles.compose)
    implementation(libs.navigation.hilt)
    implementation(libs.systemuicontr)
    implementation(libs.bundles.tests)
    implementation(libs.room.paging)
    implementation(libs.navigation)
    implementation(libs.media3ui)
    implementation(libs.timber)
    implementation(libs.media3)
    implementation(libs.hilt)
    implementation(libs.coil)
    implementation(libs.room)
    kapt(libs.hilt.compiler)
    kapt(libs.room.compiler)

    //implementation project(path: ':core:ui')
}