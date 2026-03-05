buildscript {
  repositories { google() }
  dependencies { classpath(libs.androidx.navigation.safe.args.gradle.plugin) }
}

plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.room) apply false
  id("com.google.devtools.ksp") version "2.3.4" apply false
}
