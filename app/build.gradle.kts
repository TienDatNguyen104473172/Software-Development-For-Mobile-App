plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp") version "2.0.21-1.0.25"
}

android {
    namespace = "com.example.mini_library"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.mini_library"
        minSdk = 24
        targetSdk = 36
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    val room_version = "2.6.1"
    implementation("androidx.core:core-ktx:1.13.1") // Hoặc phiên bản mới hơn
    implementation("androidx.appcompat:appcompat:1.7.0") // Rất quan trọng cho AppCompatActivity
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // Cho layout
    implementation("com.google.android.material:material:1.12.0") // Cho FAB
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version") // Để dùng Coroutines với Room
    ksp("androidx.room:room-compiler:$room_version") // Dùng KSP thay vì kapt
    // Thêm 2 dòng này để sửa lỗi viewModelScope và asLiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
}