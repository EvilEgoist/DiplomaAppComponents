plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ru.diploma.appcomponents"
    compileSdk = SdkVersions.COMPILE_SDK

    defaultConfig {
        applicationId = "ru.diploma.appcomponents"
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
            isMinifyEnabled = true
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
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.bundles.android.core)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.tests)
    implementation(libs.coil)
    implementation(libs.media3)
    implementation(libs.media3ui)
    implementation(libs.splash.screen)
    implementation(libs.glance)
    implementation(libs.glance.widget)
    implementation(project(":core:base"))
    implementation(project(":features:imageGallery"))
    //implementation project(path: ':core:ui')
}