dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")
