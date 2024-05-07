plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.nghiencuukhoahoc"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nghiencuukhoahoc"
        minSdk = 28 //30
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
    buildFeatures {
        viewBinding = true
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
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")

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
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth:22.3.1")

    //networkuing


    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))

    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    //connect aws
    implementation ("com.amazonaws:aws-android-sdk-pinpoint:2.73.0")
    implementation ("androidx.localbroadcastmanager:localbroadcastmanager:1.1.0")
    implementation ("com.amazonaws:aws-android-sdk-iot:2.8.+")
    // Add the dependency for the Realtime Database library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-database")
}
