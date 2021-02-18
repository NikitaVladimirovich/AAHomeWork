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
    private const val navFragment = "androidx.navigation:navigation-fragment:${Versions.nav_version}"
    private const val navUI = "androidx.navigation:navigation-ui:${Versions.nav_version}"
    private const val navFuture = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.nav_version}"

    // RatingBar
    private const val ratingBar =
        "me.zhanghai.android.materialratingbar:library:${Versions.rating_bar_version}"

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
    private const val workManager = "androidx.work:work-runtime-ktx:${Versions.work_manager_version}"

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

    // Preference KTX
    private const val preferenceKtx =
        "androidx.preference:preference-ktx:${Versions.preference_ktx_version}"

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
        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt_extensions_version}"
    private const val hiltWorker = "androidx.hilt:hilt-work:${Versions.hilt_extensions_version}"
    private const val hiltCompiler =
        "androidx.hilt:hilt-compiler:${Versions.hilt_extensions_version}"

    // Test libs
    private const val junit = "junit:junit:${Versions.junit_version}"
    private const val extJUnit = "androidx.test.ext:junit:${Versions.extJunit_version}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso_version}"
    private const val coroutines_test =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines_test_version}"
    private const val core_testing = "androidx.arch.core:core-testing:${Versions.core_testing_version}"
    private const val mockito_core = "org.mockito:mockito-core:${Versions.mockito_core_version}"
    private const val mockk = "io.mockk:mockk:${Versions.mockk_version}"
    private const val hilt_test = "com.google.dagger:hilt-android-testing:${Versions.hilt_version}"
    private const val workTest = "androidx.work:work-testing:${Versions.work_manager_version}"

    val appLibraries = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(coreKtx)
        add(appcompat)
        add(constraintLayout)
        add(material)
        add(swipeToRefresh)
        add(ratingBar)
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
        add(preferenceKtx)
        add(retrofit2)
        add(converterSerialization)
        add(okhttp3)
        add(okhttp3_logging_interceptor)
        add(hiltAndroid)
        add(hiltLifecycleViewModel)
        add(hiltWorker)
        add(workManager)
        add(navFragment)
        add(navUI)
        add(navFuture)
    }

    val kaptLibraries = arrayListOf<String>().apply {
        add(roomCompiler)
        add(glideCompiler)
        add(hiltAndroidCompiler)
        add(hiltCompiler)
    }

    val kaptTestLibraries = arrayListOf<String>().apply {
        add(hiltAndroidCompiler)
        add(hiltCompiler)
    }

    val kaptAndroidTestLibraries = arrayListOf<String>().apply {
        add(hiltAndroidCompiler)
        add(hiltCompiler)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
        add(hilt_test)
        add(coroutines_test)
        add(workTest)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
        add(coroutines_test)
        add(core_testing)
        add(mockito_core)
        add(mockk)
        add(hilt_test)
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

fun DependencyHandler.kaptAndroidTest(list: List<String>) {
    list.forEach { dependency ->
        add("kaptAndroidTest", dependency)
    }
}

fun DependencyHandler.kaptTest(list: List<String>) {
    list.forEach { dependency ->
        add("kaptTest", dependency)
    }
}
