import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {

    // Stdlib
    private const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"

    // Android UI
    private const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat_version}"
    private const val coreKtx = "androidx.core:core-ktx:${Versions.core_ktx_version}"
    private const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout_version}"
    private const val material = "com.google.android.material:material:${Versions.material_version}"
    private const val swipeToRefresh =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipetorefresh_version}"

    // Glide
    private const val glide = "com.github.bumptech.glide:glide:${Versions.glide_version}"
    private const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide_version}"

    // Room
    private const val room = "androidx.room:room-runtime:${Versions.roomVersion}"
    private const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    private const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"

    // Coroutines
    private const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_version}"

    // Coroutine Lifecycle Scopes
    private const val livedata =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}"
    private const val viewmodel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"
    private const val lifecycleJava8 =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle_version}"

    // Fragment KTX
    private const val fragmentKtx =
        "androidx.fragment:fragment-ktx:${Versions.fragment_ktx_version}"

    // LeakCanary
    private const val leakcanary =
        "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary_version}"

    // Serialization
    private const val serialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization_version}"

    // Timber
    private const val timber = "com.jakewharton.timber:timber:${Versions.timber_version}"

    // Retrofit
    private const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
    private const val converterSerialization =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.serialization_converter_version}"

    // OkHttp3
    private const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
    private const val okhttp3_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"

    // Hilt
    private const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt_version}"
    private const val hiltAndroidCompiler =
        "com.google.dagger:hilt-android-compiler:${Versions.hilt_version}"
    private const val hiltLifecycleViewModel =
        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt_compiler_version}"
    private const val hiltCompiler =
        "androidx.hilt:hilt-compiler:${Versions.hilt_compiler_version}"

    // Test libs
    private const val junit = "junit:junit:${Versions.junit}"
    private const val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    val appLibraries = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(coreKtx)
        add(appcompat)
        add(constraintLayout)
        add(material)
        add(swipeToRefresh)
        add(glide)
        add(room)
        add(roomKtx)
        add(coroutinesAndroid)
        add(livedata)
        add(viewmodel)
        add(lifecycleJava8)
        add(fragmentKtx)
        add(leakcanary)
        add(serialization)
        add(timber)
        add(retrofit2)
        add(converterSerialization)
        add(okhttp3)
        add(okhttp3_logging_interceptor)
        add(hiltAndroid)
        add(hiltLifecycleViewModel)
    }

    val kaptLibraries = arrayListOf<String>().apply {
        add(roomCompiler)
        add(glideCompiler)
        add(hiltAndroidCompiler)
        add(hiltCompiler)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
    }
}

// util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}
