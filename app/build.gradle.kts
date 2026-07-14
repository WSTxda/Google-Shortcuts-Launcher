plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.aboutLibraries)
}

android {
    namespace = "com.wstxda.gsl"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.wstxda.gsl"
        minSdk = 26
        targetSdk = 37
        versionCode = 590
        versionName = "5.9.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        viewBinding = true
    }

    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
}

dependencies {
    implementation(libs.androidx.preference)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation)
    implementation(libs.google.material)
    implementation(libs.aboutlibraries.view)
    implementation(libs.markdown.core)
    implementation(libs.markdown.linkify)
}