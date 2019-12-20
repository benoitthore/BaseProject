plugins {
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
    maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
}

val kotlinVersion = "1.3.41" // Also in Deps.kt

dependencies {
    compileOnly(gradleApi())
    implementation("com.android.tools.build:gradle:3.5.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
}