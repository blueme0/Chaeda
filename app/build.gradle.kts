plugins {
    id("chaeda.android.application")
    id("chaeda.android.androidHilt")
    id("chaeda.android.kotlin")
    alias(libs.plugins.androidKotlin)
}
dependencies {
    implementation(project(":core:base"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    implementation(libs.androidx.ui.text.android)
    implementation(libs.androidx.appCompat)
    implementation(libs.materialDesign)
    implementation(libs.androidx.constraintLayout)
    implementation(libs.google.oss.plugin)
    implementation(libs.play.services.location)
    implementation(libs.flexbox)
    implementation(libs.anychart)
}
