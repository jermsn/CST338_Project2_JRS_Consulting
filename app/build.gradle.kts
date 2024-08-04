plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.cst338_tracktournament"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cst338_tracktournament"
        minSdk = 34
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


    // This was manually added to enable viewBindings
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.databinding.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


    // These were added based on the suggestions at this URL
    // https://developer.android.com/jetpack/androidx/releases/room#kts
    val room_version = "2.6.1"

    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

}