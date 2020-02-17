class Deps {

    public static def kotlinVersion = "1.3.31"

    static class Versions {
        //Version
        public static def supportLibrary = "28.0.0"
        public static def chipsSupportLibrary = "28.0.0-alpha1"
        public static def okHttpVersion = "3.9.1"
        public static def espressoVersion = "2.2.2"
        public static def ankoVersion = "0.10.8"
        public static def retrofitVersion = "2.4.0"
        public static def robolectricVersion = "3.5.1"
        public static def mockitoVersion = "2.8.9"
        public static def constraintLayout = "1.1.3"
        public static def rxJavaVersion = "2.2.0"
        public static def rxAndroidVersion = "2.0.0"
        public static def paperVersion = "2.6"
        public static def daggerVersion = "2.14.1"
        public static def mockitoKotlinVersion = "1.6.0"
        public static def exoPlayerVersion = "2.9.0"
        public static def stethoVersion = "1.5.0"
        public static def glideVersion = "4.7.1"
        public static def hyperionVersion = "0.9.23"
        public static def archVersion = "2.0.0"
        public static def playServicesVersion = "15.0.0"
        public static def junitVersion = "4.12"
        public static def jbox2dVersion = "2.2.1.1"
        public static def processingVersion = "3.3.7"
        public static def komaVersion = "0.12"
        public static def deeplearning4jVersion = "0.9.1"
        public static def koin_version = "2.0.1"
        public static def picassoVersion = "2.71828"
        public static def tensorflowVersion = "1.12.0"
        public static def jacksonVersion = "2.9.8"
        public static def ktorVersion = "1.1.3"
        public static def roomVersion = "1.1.0"
//    public static def roomVersion = "2.2.0"
        public static def coroutineVersion = "1.2.2"
        public static def enamelVersion = "0.9.0-B"
        public static def splittiesVersion = "2.1.1"
        public static def constraintLayoutVersion = "2.0.0-beta3"
    }

    public static def Enamel = new _Enamel()
    static class _Enamel {
        public static def core = "com.benoitthore.enamel:core:$Versions.enamelVersion"
        public static def geometry = "com.benoitthore.enamel:geometry:$Versions.enamelVersion"
        public static def layout = "com.benoitthore.enamel:layout:$Versions.enamelVersion"
        public static def android = "com.benoitthore.enamel:layout-android:$Versions.enamelVersion"
        public static def geometryAndroid = "com.benoitthore.enamel:geometry-android:$Versions.enamelVersion"
    }

    public static def Arch = new _Arch()
    static class _Arch {
        public static def runtime = "androidx.lifecycle:lifecycle-runtime:2.0.0:$Versions.archVersion"
        public static def extensions = "androidx.lifecycle:lifecycle-extensions:$Versions.archVersion"
    }

    //play
    public static def PlayServices = new _PlayServices()
    static class _PlayServices {
        public static def adsidentifier = "com.google.android.gms:play-services-ads-identifier:$Versions.playServicesVersion"
        public static def vision = "com.google.android.gms:play-services-vision:$Versions.playServicesVersion"
        public static def firebase = "com.google.firebase:firebase-messaging:$Versions.playServicesVersion"
        public static def messaging = "com.google.android.gms:play-services-gcm:$Versions.playServicesVersion"
        public static def material = "com.google.android.material:material:1.1.0-alpha01"
    }

    public static def XDependencies = new _XDependencies()
    static class _XDependencies {
        public static def core = "androidx.core:core-ktx:1.1.0"
        public static def cardview = "androidx.cardview:cardview:1.0.0"
        public static def appcompat = "androidx.appcompat:appcompat:1.1.0"
        public static def recyclerView = "androidx.recyclerview:recyclerview:1.0.0"
        public static def legacy = "androidx.legacy:legacy-support-v4:1.0.0"
        public static def annotation = "androidx.annotation:annotation:1.0.0"
        public static def constraintLayout = "androidx.constraintlayout:constraintlayout:$Versions.constraintLayoutVersion"
        public static def dynamicanimations = "androidx.dynamicanimation:dynamicanimation:1.0.0"
    }

    public static def KotlinDependencies = new _KotlinDependencies()
    static class _KotlinDependencies {
        public static def stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$Versions.kotlinVersion"
        public static def reflect = "org.jetbrains.kotlin:kotlin-reflect:$Versions.kotlinVersion"
        public static def kluent = "org.amshove.kluent:kluent:1.4"
    }


    public static def Squareup = new _Squareup()
    static class _Squareup {
        public static def retrofit = "com.squareup.retrofit2:retrofit:$Versions.retrofitVersion"
        public static def retrofitRxAdapter = "com.squareup.retrofit2:adapter-rxjava2:$Versions.retrofitVersion"
        public static def jacksonConvertor = "com.squareup.retrofit2:converter-jackson:$Versions.retrofitVersion"
        public static def gsonConvertor = "com.squareup.retrofit2:converter-gson:$Versions.retrofitVersion"
        public static def loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$Versions.okHttpVersion"
        public static def okhttpUrlConnection = "com.squareup.okhttp3:okhttp-urlconnection:$Versions.okHttpVersion"
        public static def retrofitMock = "com.squareup.retrofit2:retrofit-mock:$Versions.retrofitVersion"
    }

    public static def Jackson = new _Jackson()
    static class _Jackson {
        public static def core = "com.fasterxml.jackson.core:jackson-core:$Versions.jacksonVersion"
        public static def kotlin = "com.fasterxml.jackson.module:jackson-module-kotlin:$Versions.jacksonVersion"
    }

    public static def Coroutines = new _Coroutines()
    static class _Coroutines {
        public static def retrofit = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
        public static def core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$Versions.coroutineVersion"
        public static def android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$Versions.coroutineVersion"
    }

    public static def Mockito = new _Mockito()
    static class _Mockito {
        public static def core = "org.mockito:mockito-core:$Versions.mockitoVersion"
        public static def android = "org.mockito:mockito-android:$Versions.mockitoVersion"
        public static def kotlin = "com.nhaarman:mockito-kotlin:$Versions.mockitoKotlinVersion"

    }

    public static def RxJava = new _RxJava()
    static class _RxJava {
        public static def rxJava = "io.reactivex.rxjava2:rxjava:$Versions.rxJavaVersion"
        public static def rxAndroid = "io.reactivex.rxjava2:rxandroid:$Versions.rxAndroidVersion"
    }

    public static def RxBasedLibs = new _RxBasedLibs()
    static class _RxBasedLibs {
        public static def permissions = "com.github.tbruyelle:rxpermissions:0.10.2"
        public static def bindings = "com.jakewharton.rxbinding2:rxbinding:2.1.1"
        public static def bindingsCompat = "com.jakewharton.rxbinding2:rxbinding-appcompat-v7:2.1.1"
    }

    public static def Paper = new _Paper()
    static class _Paper {
        public static def paperDb = "io.paperdb:paperdb:$Versions.paperVersion"
    }

    public static def Exoplayer = new _Exoplayer()
    static class _Exoplayer {
        public static def core = "com.google.android.exoplayer:exoplayer-core:$Versions.exoPlayerVersion"
        public static def ui = "com.google.android.exoplayer:exoplayer-ui:$Versions.exoPlayerVersion"
    }

    public static def stetho = new _stetho()
    static class _stetho {
        public static def stetho = "com.facebook.stetho:stetho:$Versions.stethoVersion"
        public static def stetho_okhttp = "com.facebook.stetho:stetho-okhttp3:$Versions.stethoVersion"
    }

//    bumptech {
//            glide        = "com.github.bumptech.glide:glide:$Versions.glideVersion"
//            glideCompiler= "com.github.bumptech.glide:compiler:$Versions.glideVersion"
//    }

    public static def Picasso = new _Picasso()
    static class _Picasso {
        public static def picasso = "com.squareup.picasso:picasso:$Versions.picassoVersion"
    }

    public static def Koin = new _Koin()
    static class _Koin {
        public static def core = "org.koin:koin-core:$Versions.koin_version"
        public static def android = "org.koin:koin-android:$Versions.koin_version"
        public static def viewModel = "org.koin:koin-android-viewmodel:$Versions.koin_version"
        public static def coreExt = "org.koin:koin-core-ext:$Versions.koin_version"
        public static def test = "org.koin:koin-test:$Versions.koin_version"
    }

    public static def DiscreteScrollView = new _DiscreteScrollView()
    static class _DiscreteScrollView {
        public static def scrollview = "com.yarolegovich:discrete-scrollview:1.4.9"
    }

    public static def Overscroller = new _Overscroller()
    static class _Overscroller {
        public static def overscroller = "me.everything:overscroll-decor-android:1.0.4"
    }

    public static def RmsSwitch = new _RmsSwitch()
    static class _RmsSwitch {
        public static def rmswitch = "com.rm:rmswitch:1.2.2"
    }

    public static def Junit = new _Junit()
    static class _Junit {
        public static def junit = "junit:junit:$Versions.junitVersion"
    }

    public static def Ktlint = new _Ktlint()
    static class _Ktlint {
        public static def ktlint = "com.github.shyiko:ktlint:0.20.0"
    }

    public static def Jbox2d = new _Jbox2d()
    static class _Jbox2d {
        public static def jbox2d = "org.jbox2d:jbox2d-library:$Versions.jbox2dVersion"
    }

    public static def Processing = new _Processing()
    static class _Processing {
        public static def core = "org.processing:core:$Versions.processingVersion"
    }

    public static def Koma = new _Koma()
    static class _Koma {
        public static def koma = "com.kyonifer:koma-core-ejml:$Versions.komaVersion"
    }

    public static def Deeplearning4j = new _Deeplearning4j()
    static class _Deeplearning4j {
        public static def core = "org.deeplearning4j:deeplearning4j-core:$Versions.deeplearning4jVersion"
        public static def nd4j = "org.nd4j:nd4j-native-platform:$Versions.deeplearning4jVersion"
    }

    public static def Tensorflow = new _Tensorflow()
    static class _Tensorflow {
        public static def core = "org.tensorflow:tensorflow:$Versions.tensorflowVersion"
        public static def gpu = "org.tensorflow:libtensorflow:$Versions.tensorflowVersion"
        public static def jniGpu = "org.tensorflow:libtensorflow_jni_gpu:$Versions.tensorflowVersion"
    }

    public static def Ktor = new _Ktor()
    static class _Ktor {
        public static def core = "io.ktor:ktor-server-core:$Versions.ktorVersion"
        public static def netty = "io.ktor:ktor-server-netty:$Versions.ktorVersion"
        public static def html = "io.ktor:ktor-html-builder:$Versions.ktorVersion"
        public static def cio = "io.ktor:ktor-server-cio:$Versions.ktorVersion"
    }

    public static def Korlibs = new _Korlibs()
    static class _Korlibs {
//        https://korlibs.soywiz.com/klock/
        public static def klock = "com.soywiz.korlibs.klock:klock-jvm:1.7.3"
    }

    public static def Room = new _Room()
    static class _Room {
        public static def runtime = "android.arch.persistence.room:runtime:$Versions.roomVersion"
        public static def kapt = "android.arch.persistence.room:compiler:$Versions.roomVersion"
        public static def coroutines = "androidx.room:room-coroutines:2.1.0-alpha04"
        //https://medium.com/androiddevelopers/room-coroutines-422b786dc4c5
    }

    public static def Splitties = new _Splitties()
    static class _Splitties {
        public static def viewDsl = "com.louiscad.splitties:splitties-views-dsl:$Versions.splittiesVersion"
    }

    public static def Lifecycle = new _Lifecycle()
    static class _Lifecycle {
        public static def lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.1.0"
        public static def archLifecycle = "android.arch.lifecycle:extensions:1.1.1"
    }
}