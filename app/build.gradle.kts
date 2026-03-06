plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.navigation.safe.args)
  id("com.google.devtools.ksp")
  alias(libs.plugins.google.services)
}

android {
  namespace = "com.hire_wire_application"
  compileSdk { version = release(36) { minorApiLevel = 1 } }

  defaultConfig {
    applicationId = "com.hire_wire_application"
    minSdk = 24
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  buildFeatures { viewBinding = true }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
}

dependencies {
  implementation(libs.picasso)
  implementation(libs.cloudinary.android)
  implementation(libs.cloudinary.android.download)
  implementation(libs.cloudinary.android.preprocess)
  implementation(libs.firebase.firestore)
  implementation(libs.firebase.auth)
  implementation(platform(libs.firebase.bom))
  implementation(libs.androidx.room.runtime)
  implementation(libs.androidx.recyclerview)
  ksp(libs.androidx.room.compiler)
  implementation(libs.androidx.room.ktx)
  implementation(libs.androidx.navigation.fragment.ktx)
  implementation(libs.androidx.navigation.ui.ktx)
  implementation(libs.androidx.fragment)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.constraintlayout)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}
