import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
plugins {
    id("chaeda.android.library")
    kotlin("plugin.serialization") version libs.versions.kotlinVersion
    alias(libs.plugins.androidKotlin)
}

android {
    buildTypes {
        debug {
            buildConfigField(
                "String",
                "BASE_URL",
                gradleLocalProperties(rootDir).getProperty("base.url"),
            )
            buildConfigField(
                "String",
                "IMAGE_URL",
                gradleLocalProperties(rootDir).getProperty("image.url"),
            )
        }

        release {
            buildConfigField(
                "String",
                "BASE_URL",
                gradleLocalProperties(rootDir).getProperty("base.url"),
            )
            buildConfigField(
                "String",
                "IMAGE_URL",
                gradleLocalProperties(rootDir).getProperty("image.url"),
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }

    namespace = "com.chaeda.data"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.pagingRuntime)
    implementation(libs.androidx.security)
    implementation(libs.billing)

    implementation(libs.gson)
    implementation(libs.okhttp.bom)
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
    implementation(libs.timber)
    implementation(libs.androidx.appCompat)
    implementation(libs.materialDesign)
    implementation(libs.androidx.constraintLayout)
}
