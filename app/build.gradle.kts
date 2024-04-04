plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "com.gedeputu.subsmissionakhir"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gedeputu.subsmissionakhir"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "API_KEY", "\"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\"")
        buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation (libs.androidx.viewpager2)
    implementation (libs.circleimageview)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.androidx.lifecycle.extensions)
    implementation (libs.glide)

    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.compiler)
    androidTestImplementation(libs.androidx.room.testing)
    implementation(libs.androidx.room.ktx)

    implementation(libs.androidx.datastore.preferences)
    implementation (libs.androidx.datastore.preferences.core)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}