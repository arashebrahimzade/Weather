plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.weather.core.design"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles = "consumer-rules.pro"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra.get("compose_compiler_version") as String
    }
}

dependencies {

    //Compose
    implementation(platform("androidx.compose:compose-bom:${rootProject.extra.get("compose_bom_version")}"))
//    implementation "androidx.compose.ui:ui:"
    implementation("androidx.compose.material:material:")
    implementation("androidx.compose.material:material-icons-extended:")
    implementation("androidx.compose.ui:ui-tooling-preview:")
    debugImplementation ("androidx.compose.ui:ui-tooling:")
    implementation("androidx.compose.runtime:runtime:")
//    implementation("androidx.compose.runtime:runtime-livedata:")
    implementation("androidx.compose.foundation:foundation:")
//    implementation("androidx.compose.animation:animation:")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}