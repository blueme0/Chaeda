plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.build)
    compileOnly(libs.kotlin.gradle)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "chaeda.android.application"
            implementationClass = "plugins.AndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "chaeda.android.library"
            implementationClass = "plugins.AndroidLibraryPlugin"
        }
        register("androidHilt") {
            id = "chaeda.android.androidHilt"
            implementationClass = "plugins.AndroidHiltPlugin"
        }
        register("androidKotlin") {
            id = "chaeda.android.kotlin"
            implementationClass = "plugins.AndroidKotlinPlugin"
        }
    }
}
