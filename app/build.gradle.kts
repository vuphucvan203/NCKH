plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.nghiencuukhoahoc"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nghiencuukhoahoc"
        minSdk = 30
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
}

dependencies {
    //Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    implementation ("com.google.firebase:firebase-messaging")
    //import CardView
    implementation ("androidx.cardview:cardview:1.0.0")

    //volley
    implementation("com.android.volley:volley:1.2.1")

    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:1.1.5")
    implementation ("com.amazonaws:aws-android-sdk-pinpoint:2.73.0")
    implementation ("com.amazonaws:aws-android-sdk-mobile-client:2.73.0")
    //json
    implementation ("com.google.code.gson:gson:2.8.9")
    
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}