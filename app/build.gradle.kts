
plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "step.learning.allbuysell"
    compileSdk = 34

    defaultConfig {
        applicationId = "step.learning.allbuysell"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isRenderscriptDebuggable = true
            renderscriptOptimLevel = 3
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = true
        }
    }
    dependenciesInfo {
        includeInApk = true
        includeInBundle = true
    }
    buildToolsVersion = "34.0.0"
}


dependencies {
    implementation("com.google.android.flexbox:flexbox:3.0.0")
    //implementation("com.azure:azure-cosmos:4.0.1")
    //com.azure:azure-cosmos:4.3.0
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
/*

https://learn.microsoft.com/en-us/answers/questions/297216/connecting-android-studio-to-cosmos-database

*/