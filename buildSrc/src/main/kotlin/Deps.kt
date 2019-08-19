object Deps {

    val kotlinVersion = "1.3.31"

    //Version
    private val supportLibrary = "28.0.0"
    private val chipsSupportLibrary = "28.0.0-alpha1"
    private val okHttpVersion = "3.9.1"
    private val espressoVersion = "2.2.2"
    private val ankoVersion = "0.10.8"
    private val retrofitVersion = "2.4.0"
    private val robolectricVersion = "3.5.1"
    private val mockitoVersion = "2.8.9"
    private val constraintLayout = "1.1.3"
    private val rxJavaVersion = "2.2.0"
    private val rxAndroidVersion = "2.0.0"
    private val paperVersion = "2.6"
    private val daggerVersion = "2.14.1"
    private val mockitoKotlinVersion = "1.6.0"
    private val exoPlayerVersion = "2.9.0"
    private val stethoVersion = "1.5.0"
    private val glideVersion = "4.7.1"
    private val hyperionVersion = "0.9.23"
    private val archVersion = "2.0.0"
    private val playServicesVersion = "15.0.0"
    private val junitVersion = "4.12"
    private val jbox2dVersion = "2.2.1.1"
    private val processingVersion = "3.3.7"
    private val komaVersion = "0.12"
    private val deeplearning4jVersion = "0.9.1"
    private val koin_version = "1.0.2"
    private val picassoVersion = "2.71828"
    private val tensorflowVersion = "1.12.0"
    private val jacksonVersion = "2.9.8"
    private val ktorVersion = "1.1.3"
    private val roomVersion = "1.1.0"
    private val coroutineVersion = "1.1.1"
    private val enamelVersion = "0.9.0"

    object Enamel {
        val core = "com.benoitthore.enamel:core:$enamelVersion"
        val geometry = "com.benoitthore.enamel:geometry:$enamelVersion"
        val layout = "com.benoitthore.enamel:layout:$enamelVersion"
        val android = "com.benoitthore.enamel:android:$enamelVersion"
    }

    object Arch {
        val runtime = "androidx.lifecycle:lifecycle-runtime:2.0.0:$archVersion"
        val extensions = "androidx.lifecycle:lifecycle-extensions:$archVersion"
    }

    //play services dependencies
    object PlayServices {
        val adsidentifier = "com.google.android.gms:play-services-ads-identifier:$playServicesVersion"
        val vision = "com.google.android.gms:play-services-vision:$playServicesVersion"
        val firebase = "com.google.firebase:firebase-messaging:$playServicesVersion"
        val messaging = "com.google.android.gms:play-services-gcm:$playServicesVersion"
        val material = "com.google.android.material:material:1.1.0-alpha01"
    }

    object XDependencies {
        val core = "androidx.core:core-ktx:1.0.1"
        val cardview = "androidx.cardview:cardview:1.0.0"
        val appcompat = "androidx.appcompat:appcompat:1.0.2"
        val legacy = "androidx.legacy:legacy-support-v4:1.0.0"
        val annotation = "androidx.annotation:annotation:1.0.0"
        val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0-alpha2"
        val dynamicanimations = "androidx.dynamicanimation:dynamicanimation:1.0.0"
    }

    object KotlinDependencies {
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
        val reflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
        val kluent = "org.amshove.kluent:kluent:1.4"
    }


    object Squareup {
        val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        val retrofitRxAdapter = "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"
        val jacksonConvertor = "com.squareup.retrofit2:converter-jackson:$retrofitVersion"
        val gsonConvertor = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
        val okhttpUrlConnection = "com.squareup.okhttp3:okhttp-urlconnection:$okHttpVersion"
        val retrofitMock = "com.squareup.retrofit2:retrofit-mock:$retrofitVersion"
    }

    object Jackson {
        val core = "com.fasterxml.jackson.core:jackson-core:$jacksonVersion"
        val kotlin = "com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion"
    }

    object Coroutines {
        val retrofit = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
        val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"
        val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion"
    }

    object Mockito {
        val core = "org.mockito:mockito-core:$mockitoVersion"
        val android = "org.mockito:mockito-android:$mockitoVersion"
        val kotlin = "com.nhaarman:mockito-kotlin:$mockitoKotlinVersion"

    }

    object RxJava {
        val rxJava = "io.reactivex.rxjava2:rxjava:$rxJavaVersion"
        val rxAndroid = "io.reactivex.rxjava2:rxandroid:$rxAndroidVersion"
    }

    object RxBasedLibs {
        val permissions = "com.github.tbruyelle:rxpermissions:0.10.2"
        val bindings = "com.jakewharton.rxbinding2:rxbinding:2.1.1"
        val bindingsCompat = "com.jakewharton.rxbinding2:rxbinding-appcompat-v7:2.1.1"
    }

    object Paper {
        val paperDb = "io.paperdb:paperdb:$paperVersion"
    }

    object Exoplayer {
        val core = "com.google.android.exoplayer:exoplayer-core:$exoPlayerVersion"
        val ui = "com.google.android.exoplayer:exoplayer-ui:$exoPlayerVersion"
    }

    object stetho {
        val stetho = "com.facebook.stetho:stetho:$stethoVersion"
        val stetho_okhttp = "com.facebook.stetho:stetho-okhttp3:$stethoVersion"
    }

//    bumptech {
//            glide        = "com.github.bumptech.glide:glide:$glideVersion"
//            glideCompiler= "com.github.bumptech.glide:compiler:$glideVersion"
//    }

    object Picasso {
        val picasso = "com.squareup.picasso:picasso:$picassoVersion"
    }

    object Koin {
        val core = "org.koin:koin-core:$koin_version"
        val android = "org.koin:koin-android:$koin_version"
        val coreExt = "org.koin:koin-core-ext:$koin_version"
        val test = "org.koin:koin-test:$koin_version"
    }

    object DiscreteScrollView {
        val scrollview = "com.yarolegovich:discrete-scrollview:1.4.9"
    }

    object Overscroller {
        val overscroller = "me.everything:overscroll-decor-android:1.0.4"
    }

    object RmsSwitch {
        val rmswitch = "com.rm:rmswitch:1.2.2"
    }

    object Junit {
        val junit = "junit:junit:$junitVersion"
    }

    object Ktlint {
        val ktlint = "com.github.shyiko:ktlint:0.20.0"
    }

    object Jbox2d {
        val jbox2d = "org.jbox2d:jbox2d-library:$jbox2dVersion"
    }

    object Processing {
        val core = "org.processing:core:$processingVersion"
    }

    object Koma {
        val koma = "com.kyonifer:koma-core-ejml:$komaVersion"
    }

    object Deeplearning4j {
        val core = "org.deeplearning4j:deeplearning4j-core:$deeplearning4jVersion"
        val nd4j = "org.nd4j:nd4j-native-platform:$deeplearning4jVersion"
    }

    object Tensorflow {
        val core = "org.tensorflow:tensorflow:$tensorflowVersion"
        val gpu = "org.tensorflow:libtensorflow:$tensorflowVersion"
        val jniGpu = "org.tensorflow:libtensorflow_jni_gpu:$tensorflowVersion"
    }

    object Ktor {
        val core = "io.ktor:ktor-server-core:$ktorVersion"
        val netty = "io.ktor:ktor-server-netty:$ktorVersion"
        val html = "io.ktor:ktor-html-builder:$ktorVersion"
        val cio = "io.ktor:ktor-server-cio:$ktorVersion"

    }

    object Room {
        val runtime = "android.arch.persistence.room:runtime:$roomVersion"
        val coroutines = "androidx.room:room-coroutines:2.1.0-alpha04" //https://medium.com/androiddevelopers/room-coroutines-422b786dc4c5

    }
}