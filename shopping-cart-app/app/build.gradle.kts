plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")

}

android {
    namespace = "pt.ipca.shopping_cart_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "pt.ipca.shopping_cart_app"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        //freeCompilerArgs = ["-Xjvm-default=compatibility"]
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.material3:material3")


    //-----------------------------------------------------------novas implementacoes UI----------------------------------------------------------------------------
    //implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    //implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    //implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    //implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")

    //implementation("androidx.activity:activity-ktx:1.9.3")
    //---------------------------------------------------------------------------------------------------------------------------------------------------

    //----------------------------------------------------------para usar o room compose(sqlLite)--------------------------------------------------------------

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.navigation:navigation-compose:2.8.4")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    testImplementation("androidx.room:room-testing:2.6.1")
    androidTestImplementation("androidx.room:room-testing:2.6.1")

    //_--------------------------------------------------------------------------------------------------------------------

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-auth-ktx:23.1.0")

    //------------------------------------------------------------------implementaion material

    //implementation("com.github.bumptech.glide:glide:4.12.0")
    //implementation("com.google.code.gson:gson:2.9.1")
    //implementation("com.tbuonomo:dotsindicator:5.0")

    //-----------------------------------------------------------------------------------------------------------

}