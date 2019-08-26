//define Version library
object Version {
    const val kotlin = "1.3.31"
    const val androidPlugin = "3.4.0"
    const val playService = "4.2.0"
    const val fabric = "1.29.0"
    const val updateLib = "0.21.0"
    const val navigation = "2.1.0-alpha03"

    const val androidx = "1.1.0-alpha05"
    const val lifecycle = "2.2.0-alpha01"
    const val multidex = "2.0.1"


    const val retrofit = "2.5.0"
    const val koin = "2.0.0-rc-3"
    const val glide = "4.9.0"
    const val room = "2.1.0-alpha06"
    const val work = "1.0.0"
    const val ktx = "1.2.0-alpha01"

    const val rxkotlin = "2.3.0"
    const val rxandroid = "2.1.1"
    const val rxpermission = "2.x.v0.9.3"

    const val firebaseCore = "16.0.9"
    const val firebaseAnalytics = "16.5.0"
    const val firebaseCrashlytics = "2.10.0"
    const val firebaseMessaging = "17.6.0"

    const val ps = "16.0.0"
    const val psAuth = "16.0.1"
    const val psMap = "16.1.0"

    const val okhttpLog = "3.14.1"
    const val timberkt = "1.5.1"
    const val debugDb = "1.0.6"
}

//Build gradle on root
object Build {
    const val android = "com.android.tools.build:gradle:${Version.androidPlugin}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
    const val playService = "com.google.gms:google-services:${Version.playService}"
    const val fabric = "io.fabric.tools:gradle:${Version.fabric}"
    const val updateLib = "com.github.ben-manes:gradle-versions-plugin:${Version.updateLib}"
    const val navigation = "androidx.navigation:navigation-safe-args-gradle-plugin:${Version.navigation}"
}

//Build gradle app
//TODO : change application id here
object Android {
    const val applicationId = "ps.bebyrong"
    const val compileSdk = 28
    const val minSdk = 21
    const val targetSdk = 28
    const val versionCode = 1
    const val versionName = "1.0"
}

object Androidx {
    const val appcompat = "androidx.appcompat:appcompat:${Version.androidx}"
    const val material = "com.google.android.material:material:1.1.0-alpha06"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Version.androidx}"
    const val cardview = "androidx.cardview:cardview:${Version.androidx}"
    const val support = "androidx.legacy:legacy-support-v4:1.0.0"
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Version.lifecycle}"
    const val multidex = "androidx.multidex:multidex:${Version.multidex}"
}

object Ktx {
    const val worker = "androidx.work:work-rxjava2::${Version.work}"
    const val core = "androidx.core:core-ktx:${Version.ktx}"
    const val fragment = "androidx.fragment:fragment-ktx:${Version.ktx}"
    const val navFragment = "androidx.navigation:navigation-fragment-ktx:${Version.navigation}"
    const val navUI = "androidx.navigation:navigation-ui-ktx:${Version.navigation}"
}

object Kotlin {
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Version.kotlin}"
    const val koin = "org.koin:koin-androidx-viewmodel:${Version.koin}"
}

object Firebase {
    const val core = "com.google.firebase:firebase-core:${Version.firebaseCore}"
    const val analytics = "com.google.firebase:firebase-analytics:${Version.firebaseAnalytics}"
    const val crashlytics = "com.crashlytics.sdk.android:crashlytics:${Version.firebaseCrashlytics}"
    const val messaging = "com.google.firebase:firebase-messaging:${Version.firebaseMessaging}"
    const val auth = "com.google.firebase:firebase-auth:17.0.0"
}

object PlayService {
    const val map = "com.google.android.gms:play-services-maps:${Version.psMap}"
    const val location = "com.google.android.gms:play-services-location:${Version.ps}"
    const val place = "com.google.android.gms:play-services-places:${Version.ps}"
    const val auth = "com.google.android.gms:play-services-auth:${Version.psAuth}"
}

object Rx {
    const val kotlin = "io.reactivex.rxjava2:rxkotlin:${Version.rxkotlin}"
    const val android = "io.reactivex.rxjava2:rxandroid:${Version.rxandroid}"
    const val permission = "com.github.tbruyelle:rxpermissions:${Version.rxpermission}"
}

object Retrofit {
    const val core = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    const val adapter = "com.squareup.retrofit2:adapter-rxjava2:${Version.retrofit}"
    const val converter = "com.squareup.retrofit2:converter-moshi:${Version.retrofit}"
}

object Room {
    const val rx = "androidx.room:room-rxjava2:${Version.room}"
    const val compiler = "androidx.room:room-compiler:${Version.room}"
    const val debug = "com.amitshekhar.android:debug-db:${Version.debugDb}"
}

object Glide {
    const val core = "com.github.bumptech.glide:glide:${Version.glide}"
    const val okhttp = "com.github.bumptech.glide:okhttp3-integration:${Version.glide}"
    const val compiler = "com.github.bumptech.glide:compiler:${Version.glide}"
}

object Log {
    const val okhttp = "com.squareup.okhttp3:logging-interceptor:${Version.okhttpLog}"
    const val timberkt = "com.github.ajalt:timberkt:${Version.timberkt}"
}

object Extra {
    const val shimmer = "com.facebook.shimmer:shimmer:0.4.0"
}
