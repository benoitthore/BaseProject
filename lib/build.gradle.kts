import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(SdkSupportVersion.compileSdkVersion)
    defaultConfig {
        multiDexEnabled = true

//        applicationId = "com.benoitthore.base.lib"
        minSdkVersion(SdkSupportVersion.minSdkVersion)
        targetSdkVersion(SdkSupportVersion.targetSdkVersion)
//        versionCode = 1
//        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))

    implementation(Deps.Enamel.core)
    implementation(Deps.Enamel.geometryAndroid)
    implementation(Deps.Enamel.geometry)
    implementation(Deps.Enamel.layout)

    implementation(Deps.Coroutines.core)
    implementation(Deps.Coroutines.android)

    implementation(Deps.Lifecycle.lifecycleViewModel)
    implementation(Deps.Lifecycle.archLifecycle)

    implementation(Deps.Squareup.retrofit)
    implementation(Deps.Squareup.jacksonConvertor)

    implementation(Deps.Jackson.core)
    implementation(Deps.Jackson.kotlin)

    implementation(Deps.Koin.core)
    implementation(Deps.Koin.android)

//    implementation(Deps.Room.runtime)
//    kapt(Deps.Room.kapt)

    implementation(Deps.XDependencies.core)
    implementation(Deps.XDependencies.appcompat)
    implementation(Deps.XDependencies.constraintLayout)

    testImplementation("junit:junit:4.12")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
}

