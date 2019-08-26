plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.github.ben-manes.versions")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id("io.fabric") //TODO add after register on firebase
}

android {
    dataBinding {
        isEnabled = true
    }
    compileSdkVersion(Android.compileSdk)
    defaultConfig {
        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)

        applicationId = Android.applicationId
        versionCode = Android.versionCode
        versionName = Android.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"https://bebyrong.pallakastudio.com/api/\"")
        buildConfigField("String", "ClientID", "\"932570400235-rslk1h4g703m77b1pqn68nc9m2unhpqn.apps.googleusercontent.com\"")
        buildConfigField("String", "ClientSecret", "\"TULSBX80M7vtnuKkj0CTtjFn\"")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

//TODO disable for unused library
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Androidx.appcompat)
    implementation(Androidx.material)
    implementation(Androidx.recyclerview)
    implementation(Androidx.lifecycle)
    implementation(Androidx.support)

    implementation(Ktx.core)

//    implementation(Ktx.worker)
    implementation(Ktx.navFragment)
    implementation(Ktx.navUI)

    implementation(Kotlin.stdlib)
    implementation(Kotlin.koin)

    implementation(Firebase.core)
    implementation(Firebase.auth)
    implementation(Firebase.analytics)
    implementation(Firebase.crashlytics)

    implementation(PlayService.auth)

/*    implementation(Firebase.messaging)

    implementation(PlayService.map)
    implementation(PlayService.location)
    implementation(PlayService.place)*/

    implementation(Rx.kotlin)
    implementation(Rx.android)
    implementation(Rx.permission)

    implementation(Retrofit.core)
    implementation(Retrofit.adapter)
    implementation(Retrofit.converter)

    /*implementation(Room.rx)
    kapt(Room.compiler)
    debugImplementation(Room.debug)*/

    implementation(Glide.core)
    implementation(Glide.okhttp)
    kapt(Glide.compiler)

    implementation(Androidx.multidex)

    implementation(Log.okhttp)
    implementation(Log.timberkt)

    implementation(Extra.shimmer)
}

//override limit error log to 1000
gradle.projectsEvaluated {
    tasks.withType(JavaCompile::class) {
        options.compilerArgs.addAll(arrayOf("-Xmaxerrs", "1000"))
    }
}